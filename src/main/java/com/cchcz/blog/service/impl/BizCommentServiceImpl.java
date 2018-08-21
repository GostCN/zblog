
package com.cchcz.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cchcz.blog.aspect.annotation.RedisCache;
import com.cchcz.blog.dao.beans.BizComment;
import com.cchcz.blog.dao.mapper.BizCommentMapper;
import com.cchcz.blog.exception.BlogCommentException;
import com.cchcz.blog.model.dto.BizCommentDTO;
import com.cchcz.blog.model.entity.Comment;
import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.model.enums.CommentStatusEnum;
import com.cchcz.blog.model.enums.TemplateKeyEnum;
import com.cchcz.blog.model.vo.CommentConditionVO;
import com.cchcz.blog.service.BizCommentService;
import com.cchcz.blog.service.MailService;
import com.cchcz.blog.service.SysConfigService;
import com.cchcz.blog.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 评论
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
@Service
public class BizCommentServiceImpl implements BizCommentService {

    @Autowired
    private BizCommentMapper bizCommentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MailService mailService;

    @Autowired
    private SysConfigService configService;

    @Value("${static.resource.url}")
    private String staticResourceUrl;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public PageInfo<Comment> findPageBreakByCondition(CommentConditionVO vo) {
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizComment> list = bizCommentMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Comment> boList = new ArrayList<>();
        for (BizComment bizComment : list) {
            boList.add(new Comment(bizComment));
        }
        PageInfo bean = new PageInfo<BizComment>(list);
        bean.setList(boList);
        return bean;
    }

    /**
     * @param vo
     * @return
     */
    @Override
//    @RedisCache
    public Map<String, Object> list(CommentConditionVO vo) {
        PageInfo pageInfo = findPageBreakByCondition(vo);
        Map<String, Object> map = new HashMap<>();
        if (pageInfo != null) {
            map.put("commentList", convert2DTO(pageInfo.getList()));
            map.put("total", pageInfo.getTotal());
            map.put("hasNextPage", pageInfo.isHasNextPage());
            map.put("nextPage", pageInfo.getNextPage());
        }
        return map;
    }

    private List<BizCommentDTO> convert2DTO(List<Comment> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<BizCommentDTO> dtoList = new LinkedList<>();
        for (Comment comment : list) {
            BizCommentDTO dto = BeanConvertUtil.doConvert(comment, BizCommentDTO.class);
            dto.setParentDTO(BeanConvertUtil.doConvert(comment.getParent(), BizCommentDTO.class));
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * admin发表评论
     *
     * @param comment
     * @return
     */
    @Override
    @RedisCache(flush = true)
    public void commentForAdmin(Comment comment) throws BlogCommentException {
        Config config = configService.get();
        User user = SessionUtil.getUser();
        comment.setQq(user.getQq());
        comment.setEmail(user.getEmail());
        comment.setNickname(user.getNickname());
        comment.setAvatar(user.getAvatar());
        comment.setUrl(config.getSiteUrl());
        comment.setUserId(user.getId());
        comment.setStatus(CommentStatusEnum.APPROVED.toString());
        comment.setPid(comment.getId());
        comment.setId(null);
        this.comment(comment);
    }

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    @Override
    @RedisCache(flush = true)
    public Comment comment(Comment comment) throws BlogCommentException {
        if (StringUtils.isEmpty(comment.getNickname())) {
            throw new BlogCommentException("必须输入昵称哦~~");
        }
        String content = comment.getContent();
        if (StringUtils.isEmpty(content)) {
            throw new BlogCommentException("不说话可不行，必须说点什么哦~~");
        }
        content = content.trim();
        if (content.endsWith("<p><br></p>")) {
            comment.setContent(content.substring(0, content.length() - "<p><br></p>".length()));
        }
        comment.setNickname(HtmlUtil.html2Text(comment.getNickname()));
        comment.setQq(HtmlUtil.html2Text(comment.getQq()));
        comment.setAvatar(HtmlUtil.html2Text(comment.getAvatar()));
        comment.setEmail(HtmlUtil.html2Text(comment.getEmail()));
        comment.setUrl(HtmlUtil.html2Text(comment.getUrl()));
        HttpServletRequest request = RequestUtil.getRequest();
        String ua = request.getHeader("User-Agent");
        UserAgent agent = UserAgent.parseUserAgentString(ua);
        // 浏览器
        Browser browser = agent.getBrowser();
        String browserInfo = browser.getName();
//        comment.setBrowserShortName(browser.getShortName());// 此处需开发者自己处理
        // 浏览器版本
        Version version = agent.getBrowserVersion();
        if (version != null) {
            browserInfo += " " + version.getVersion();
        }
        comment.setBrowser(browserInfo);
        // 操作系统
        OperatingSystem os = agent.getOperatingSystem();
        comment.setOs(os.getName());
//        comment.setOsShortName(os.getShortName());// 此处需开发者自己处理
        comment.setIp(IpUtil.getRealIp(request));
        String address = "定位失败";
        Config config = configService.get();
        try {
            String locationJson = RestClientUtil.get(UrlBuildUtil.getLocationByIp(comment.getIp(), config.getBaiduApiAk()));
            JSONObject localtionContent = JSONObject.parseObject(locationJson).getJSONObject("content");
            // 地址详情
            JSONObject addressDetail = localtionContent.getJSONObject("address_detail");
            // 省
            String province = addressDetail.getString("province");
            // 市
            String city = addressDetail.getString("city");
            // 区
            String district = addressDetail.getString("district");
            // 街道
            String street = addressDetail.getString("street");
            // 街道编号
//            String street_number = addressDetail.getString("street_number");
            StringBuffer sb = new StringBuffer(province);
            if (!StringUtils.isEmpty(city)) {
                sb.append(city);
            }
            if (!StringUtils.isEmpty(district)) {
                sb.append(district);
            }
            if (!StringUtils.isEmpty(street)) {
                sb.append(street);
            }
            address = sb.toString();
            // 经纬度
            JSONObject point = localtionContent.getJSONObject("point");
            // 纬度
            String lat = point.getString("y");
            // 经度
            String lng = point.getString("x");
            comment.setLat(lat);
            comment.setLng(lng);
            comment.setAddress(address);
        } catch (Exception e) {
            comment.setAddress("未知");
            log.error("获取地址失败", e);
        }
        if (StringUtils.isEmpty(comment.getStatus())) {
            comment.setStatus(CommentStatusEnum.VERIFYING.toString());
        }
        String nickname = comment.getNickname();
        if ("匿名".equals(nickname)) {
            comment.setNickname("神秘人");
        }
        if ("神秘人".equals(nickname)) {
            if (StringUtils.isNotBlank(comment.getQq())) {
                comment.setNickname(comment.getQq());
            } else if (StringUtils.isNotBlank(comment.getEmail())) {
                comment.setNickname(comment.getEmail());
            }
        }
        this.insert(comment);
        this.sendEmail(comment);
        return comment;
    }


    private void sendEmail(Comment comment) {
        // 发送邮件通知，此处如发生异常不应阻塞当前的业务流程
        // 可以进行日志记录等操作
        try {
            if (null != comment.getPid() && 0 != comment.getPid()) {
                // 给被评论的用户发送通知
                Comment commentDB = this.getByPrimaryKey(comment.getPid());
                mailService.send(commentDB, TemplateKeyEnum.TM_COMMENT_REPLY, false);
            } else {
                mailService.sendToAdmin(comment);
            }
        } catch (Exception e) {
            log.error("发送评论通知邮件时发生异常", e);
        }
    }

    /**
     * 查询近期评论
     *
     * @param pageSize
     * @return
     */
    @Override
    @RedisCache
    public List<Comment> listRecentComment(int pageSize) {
        CommentConditionVO vo = new CommentConditionVO();
        vo.setPageSize(pageSize);
        vo.setStatus(CommentStatusEnum.APPROVED.toString());
        PageHelper.startPage(vo.getPageNumber(), vo.getPageSize());
        List<BizComment> list = bizCommentMapper.findPageBreakByCondition(vo);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Comment> boList = new ArrayList<>();
        for (BizComment bizComment : list) {
            if (StringUtils.isEmpty(bizComment.getAvatar())) {
                bizComment.setAvatar(staticResourceUrl + "/cuser/" + (Math.abs(Objects.hash(bizComment.getNickname() + "")) % 20) + ".jpg");
            }
            boList.add(new Comment(bizComment));
        }
        return boList;
    }

    /**
     * 查询未审核的评论
     *
     * @param pageSize
     * @return
     */
    @Override
    public List<Comment> listVerifying(int pageSize) {
        CommentConditionVO vo = new CommentConditionVO();
        vo.setPageSize(pageSize);
        vo.setStatus(CommentStatusEnum.VERIFYING.toString());
        PageInfo pageInfo = findPageBreakByCondition(vo);
        return null == pageInfo ? null : pageInfo.getList();
    }

    /**
     * 点赞
     *
     * @param id
     */
    @Override
    @RedisCache(flush = true)
    public void doSupport(Long id) {
        String key = IpUtil.getRealIp(RequestUtil.getRequest()) + "_doSupport_" + id;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            throw new BlogCommentException("一个小时只能点一次赞哈~");
        }
        bizCommentMapper.doSupport(id);
        operations.set(key, id, 1, TimeUnit.HOURS);
    }

    /**
     * 点踩
     *
     * @param id
     */
    @Override
    @RedisCache(flush = true)
    public void doOppose(Long id) {
        String key = IpUtil.getRealIp(RequestUtil.getRequest()) + "_doOppose_" + id;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            throw new BlogCommentException("一个小时只能踩一次哈~又没什么深仇大恨");
        }
        bizCommentMapper.doOppose(id);
        operations.set(key, id, 1, TimeUnit.HOURS);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public Comment insert(Comment entity) {
        Assert.notNull(entity, "Comment不可为空！");
        entity.setUpdateTime(new Date());
        entity.setCreateTime(new Date());
        bizCommentMapper.insertSelective(entity.getBizComment());
        return entity;
    }

    /**
     * 批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列
     *
     * @param entities
     */
    @Override
    @RedisCache(flush = true)
    public void insertList(List<Comment> entities) {
        Assert.notNull(entities, "Comments不可为空！");
        List<BizComment> list = new ArrayList<>();
        for (Comment entity : entities) {
            entity.setUpdateTime(new Date());
            entity.setCreateTime(new Date());
            list.add(entity.getBizComment());
        }
        bizCommentMapper.insertList(list);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     *
     * @param primaryKey
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean removeByPrimaryKey(Long primaryKey) {
        return bizCommentMapper.deleteByPrimaryKey(primaryKey) > 0;
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean update(Comment entity) {
        Assert.notNull(entity, "Comment不可为空！");
        entity.setUpdateTime(new Date());
        return bizCommentMapper.updateByPrimaryKey(entity.getBizComment()) > 0;
    }

    /**
     * 根据主键更新属性不为null的值
     *
     * @param entity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @RedisCache(flush = true)
    public boolean updateSelective(Comment entity) {
        Assert.notNull(entity, "Comment不可为空！");
        entity.setUpdateTime(new Date());
        return bizCommentMapper.updateByPrimaryKeySelective(entity.getBizComment()) > 0;
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     *
     * @param primaryKey
     * @return
     */
    @Override
    public Comment getByPrimaryKey(Long primaryKey) {
        Assert.notNull(primaryKey, "PrimaryKey不可为空！");
        BizComment entity = bizCommentMapper.getById(primaryKey);
        return null == entity ? null : new Comment(entity);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果时抛出异常，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public Comment getOneByEntity(Comment entity) {
        Assert.notNull(entity, "Comment不可为空！");
        BizComment bo = bizCommentMapper.selectOne(entity.getBizComment());
        return null == bo ? null : new Comment(bo);
    }

    /**
     * 查询全部结果，listByEntity(null)方法能达到同样的效果
     *
     * @return
     */
    @Override
    public List<Comment> listAll() {
        List<BizComment> entityList = bizCommentMapper.selectAll();

        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Comment> list = new ArrayList<>();
        for (BizComment entity : entityList) {
            list.add(new Comment(entity));
        }
        return list;
    }

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param entity
     * @return
     */
    @Override
    public List<Comment> listByEntity(Comment entity) {
        Assert.notNull(entity, "Comment不可为空！");
        List<BizComment> entityList = bizCommentMapper.select(entity.getBizComment());
        if (CollectionUtils.isEmpty(entityList)) {
            return null;
        }
        List<Comment> list = new ArrayList<>();
        for (BizComment po : entityList) {
            list.add(new Comment(po));
        }
        return list;
    }
}
