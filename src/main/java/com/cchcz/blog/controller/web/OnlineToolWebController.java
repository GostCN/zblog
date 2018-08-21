package com.cchcz.blog.controller.web;

import com.cchcz.blog.service.BizKvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <ClassName>OnlineToolWebController</ClassName>
 * <Description></Description>
 *
 * @Author cchcz
 * @Date 2018年07月10日 22:49
 */
@RequestMapping("/onlineTool")
@Controller
public class OnlineToolWebController {
    private static final String TOOL_WEBSITE = "tool_website";
    @Autowired
    private BizKvService bizKvService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("url", "/onlineTool/");
        return "/onlineTool/index";
    }

    @RequestMapping("/json2Entity")
    public String json2Entity(Model model) {
        model.addAttribute("url", "/onlineTool/json2Entity");
        return "/onlineTool/json2Entity";
    }

    @RequestMapping("/highlighter")
    public String highlighter(Model model) {
        model.addAttribute("url", "/onlineTool/highlighter");
        return "/onlineTool/codeHighlighter";
    }

    @RequestMapping("/format/js")
    public String formatJs(Model model) {
        model.addAttribute("url", "/onlineTool/format/js");
        return "/onlineTool/jsFormat";
    }

    @RequestMapping("/format/json")
    public String formatJson(Model model) {
        model.addAttribute("url", "/onlineTool/format/json");
        return "/onlineTool/jsonFormat";
    }

    @RequestMapping("/format/css")
    public String formatCss(Model model) {
        model.addAttribute("url", "/onlineTool/format/css");
        return "/onlineTool/cssFormat";
    }

    @RequestMapping("/domain")
    public String domain(Model model) {
        model.addAttribute("url", "/onlineTool/domain");
        return "/onlineTool/domainQuery";
    }

    @RequestMapping("/ua")
    public String ua(Model model) {
        model.addAttribute("url", "/onlineTool/ua");
        return "/onlineTool/uaAnalyze";
    }

    @RequestMapping("/search")
    public String search(Model model) {
        model.addAttribute("url", "/onlineTool/search");
        return "/onlineTool/search";
    }

    @RequestMapping("/redirect")
    public String redirect(Model model) {
        String toolUrl = bizKvService.getValue(TOOL_WEBSITE);
        return "redirect:" + toolUrl;
    }
}