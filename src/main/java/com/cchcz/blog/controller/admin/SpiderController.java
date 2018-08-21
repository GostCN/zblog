package com.cchcz.blog.controller.admin;


import com.cchcz.blog.model.entity.SpiderConfig;
import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.model.object.BaseController;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.spider.DefaultSpider;
import com.cchcz.blog.spider.util.SpiderConfigHolder;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.util.SessionUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysSpiderConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin/spider")
public class SpiderController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SpiderController.class);

    @Resource
    private DefaultSpider spider;

    @Autowired
    private SysSpiderConfigService sysSpiderConfigService;

    @GetMapping(value = "")
    public ModelAndView index(HttpServletRequest request) {
        List<SpiderConfig> spiderList = sysSpiderConfigService.listAll();
        request.setAttribute("spiders", new PageInfo<>(spiderList));
        return ResultUtil.viewForAdmin("spider/list");
    }

    @RequestMapping(value = "/publish")
    @ResponseBody
    public ResponseVO spide(String targetUrl, String netAddType) {
        User user = SessionUtil.getUser();
        try {
            boolean b = spider.doSpide(targetUrl, user.getId(), "020".equals(netAddType));
            if (b) {
                return ResultUtil.success("成功");
            }
            return ResultUtil.error("处理失败");
        } catch (Exception e) {
            logger.error("系统异常", e);
            return ResultUtil.error(e.getMessage());
        }
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public ResponseVO editSpider(SpiderConfig spider) {
        spider.setUpdateTime(new Date());
        if (spider.getId() == null) {
            sysSpiderConfigService.insert(spider);
        } else {
            sysSpiderConfigService.updateSelective(spider);
        }
        //修改成功加载一下爬虫配置
        loadSpiderConfig();
        return ResultUtil.success("成功");
    }

    @GetMapping(value = "/add")
    public ModelAndView addSpider() {
        return ResultUtil.viewForAdmin("spider/edit");
    }

    @GetMapping(value = "/{sid}")
    public ModelAndView editSpider(@PathVariable Long sid, HttpServletRequest request) {
        SpiderConfig spiderConfig = sysSpiderConfigService.getByPrimaryKey(sid);
        request.setAttribute("spider", spiderConfig);
        return ResultUtil.viewForAdmin("spider/edit");
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseVO deleteSpider(@RequestParam Long sid, HttpServletRequest request) {
        boolean delete = sysSpiderConfigService.removeByPrimaryKey(sid);
        if (delete) {
            return ResultUtil.success("成功");
        }
        return ResultUtil.success("成功");
    }

    @PostConstruct
    private void loadSpiderConfig() {
        SpiderConfigHolder.setSysSpiderConfigService(sysSpiderConfigService);
        SpiderConfigHolder.loadSpiderConfig();
    }
}
