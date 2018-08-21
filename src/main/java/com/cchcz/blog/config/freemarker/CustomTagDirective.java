
package com.cchcz.blog.config.freemarker;

import com.cchcz.blog.model.entity.*;
import com.cchcz.blog.service.*;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.cchcz.blog.model.entity.*;
import com.cchcz.blog.service.*;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义的freemarker标签
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Component
public class CustomTagDirective implements TemplateDirectiveModel {
    private static final String METHOD_KEY = "method";
    private static final String SHOW_TYPE_LIST_IDS = "show_type_list_ids";
    @Autowired
    private BizTypeService bizTypeService;
    @Autowired
    private BizCommentService commentService;
    @Autowired
    private BizTagsService bizTagsService;
    @Autowired
    private SysResourcesService resourcesService;
    @Autowired
    private SysConfigService configService;
    @Autowired
    private BizToolTypeService bizToolTypeService;
    @Autowired
    private BizBookService bizBookService;
    @Autowired
    private BizToolService bizToolService;
    @Autowired
    private BizOSProjectService bizOSProjectService;
    @Autowired
    private BizKvService bizKvService;
    @Autowired
    private BizNavTypeService bizNavTypeService;
    @Autowired
    private BizNavService bizNavService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        if (map.containsKey(METHOD_KEY)) {
            String method = map.get(METHOD_KEY).toString();
            int pageSize = 10;
            if (map.containsKey("pageSize")) {
                String pageSizeStr = map.get("pageSize").toString();
                pageSize = Integer.parseInt(pageSizeStr);
            }
            long toolTypeId = -1;
            if (map.containsKey("toolTypeId")) {
                String toolTypeIdStr = map.get("toolTypeId").toString();
                toolTypeId = Long.parseLong(toolTypeIdStr);
            }
            long navTypeId = -1;
            if (map.containsKey("navTypeId")) {
                String navTypeIdStr = map.get("navTypeId").toString();
                navTypeId = Long.parseLong(navTypeIdStr);
            }

            String source = "GitHub";
            if (map.containsKey("source")) {
                source = map.get("source").toString();
            }
            switch (method) {
                case "types":
                    environment.setVariable("types", builder.build().wrap(getTypes()));
                    break;
                case "subTypes":
                    environment.setVariable("subTypes", builder.build().wrap(getSubTypes()));
                    break;
                case "tagsList":
                    // 所有标签
                    environment.setVariable("tagsList", builder.build().wrap(bizTagsService.listAll()));
                    break;
                case "availableMenus":
                    // 所有可用的菜单资源
                    environment.setVariable("availableMenus", builder.build().wrap(resourcesService.listAllAvailableMenu()));
                    break;
                case "recentComments":
                    // 近期评论
                    environment.setVariable("recentComments", builder.build().wrap(commentService.listRecentComment(pageSize)));
                    break;
                case "siteInfo":
                    // 站点属性
                    environment.setVariable("siteInfo", builder.build().wrap(configService.getSiteInfo()));
                    break;
                case "toolTypes":
                    // 工具类型
                    environment.setVariable("toolTypes", builder.build().wrap(bizToolTypeService.listAll()));
                    break;
                case "navTypes":
                    // 工具类型
                    environment.setVariable("navTypes", builder.build().wrap(bizNavTypeService.listAll()));
                    break;
                case "books":
                    // 工具类型
                    Book book = new Book();
                    book.setStatus("1");
                    environment.setVariable("books", builder.build().wrap(bizBookService.listByEntity(book)));
                    break;
                case "toolList":
                    Tool tool = new Tool();
                    tool.setTypeId(toolTypeId);
                    List<Tool> toolList = bizToolService.listByEntity(tool);
                    PageInfo<Tool> pageInfo = new PageInfo<>(toolList);
                    environment.setVariable("toolList", builder.build().wrap(null == pageInfo ? null : pageInfo.getList()));
                    break;
                case "navList":
                    Nav nav = new Nav();
                    nav.setTypeId(navTypeId);
                    List<Nav> navList = bizNavService.listByEntity(nav);
                    PageInfo<Nav> navpageInfo = new PageInfo<>(navList);
                    environment.setVariable("navList", builder.build().wrap(null == navpageInfo ? null : navpageInfo.getList()));
                    break;
                case "projectList":
                    OSProject project = new OSProject();
                    project.setStatus("1");
                    project.setSource(source);
                    List<OSProject> projectList = bizOSProjectService.listByEntity(project);
                    PageInfo<OSProject> pageInfo1 = new PageInfo<>(projectList);
                    environment.setVariable("projectList", builder.build().wrap(null == pageInfo1 ? null : pageInfo1.getList()));
                    break;
                case "menus":
                    Integer userId = null;
                    if (map.containsKey("userId")) {
                        String userIdStr = map.get("userId").toString();
                        if (StringUtils.isEmpty(userIdStr)) {
                            return;
                        }
                        userId = Integer.parseInt(userIdStr);
                    }
                    Map<String, Object> params = new HashMap<>(2);
                    params.put("type", "menu");
                    params.put("userId", userId);
                    environment.setVariable("menus", builder.build().wrap(resourcesService.listUserResources(params)));
                    break;
                default:
                    break;
            }
        }
        templateDirectiveBody.render(environment.getOut());
    }

    private List<Type> getTypes() {
        List<Type> types = bizTypeService.listAll();
        if (CollectionUtils.isEmpty(types)) {
            return null;
        }
        Set<Long> set = types.stream()
                .filter(x -> !(CollectionUtils.isEmpty(x.getNodes())))
                .flatMap(x -> x.getNodes().stream())
                .map(x -> x.getId())
                .collect(Collectors.toSet());
        List<Type> typeList = types.stream()
                .filter(x -> !set.contains(x.getId()))
                .collect(Collectors.toList());
        typeList.forEach(type -> {
            if (!CollectionUtils.isEmpty(type.getNodes())) {
                Collections.sort(type.getNodes(), (t1, t2) -> Ints.compare(t1.getSort(), t2.getSort()));
            }
        });
        return typeList;
    }

    private List<Type> getSubTypes() {
        List<Type> types = bizTypeService.listAll();
        if (CollectionUtils.isEmpty(types)) {
            return null;
        }
        String value = bizKvService.getValue(SHOW_TYPE_LIST_IDS);
        Set<Long> showTypes = null;
        if (StringUtils.isNotBlank(value)) {
            showTypes = Splitter.on(",").splitToList(value).stream().map(s -> Long.valueOf(s)).collect(Collectors.toSet());
        } else {
            showTypes = Sets.newHashSet();
        }
        Set<Long> set = types.stream()
                .filter(x -> !(CollectionUtils.isEmpty(x.getNodes())))
                .map(x -> x.getId())
                .collect(Collectors.toSet());
        Set<Long> finalShowTypes = showTypes;
        return types.stream()
                .filter(x -> !set.contains(x.getId()) && finalShowTypes.contains(x.getId()))
                .collect(Collectors.toList());
    }
}
