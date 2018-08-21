package com.cchcz.blog.controller.web;

import com.cchcz.blog.model.entity.NetEasePlay;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.util.NetEaseApi;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.service.BizNetEasePlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/music")
@Controller
public class MusicWebController {

    @Autowired
    private BizNetEasePlayService bizNetEasePlayService;

    @RequestMapping("/")
    public ModelAndView player() {
        return ResultUtil.view("/music/index");
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return ResultUtil.view("/music/index");
    }

    @RequestMapping("/list")
    public ModelAndView music() {
        return ResultUtil.view("/music/music");
    }

    @RequestMapping("/playList")
    @ResponseBody
    public Object list() {
        NetEasePlay netEasePlay = bizNetEasePlayService.getDefault();
        return NetEaseApi.getPlaylist(netEasePlay.getPlayId());
    }

    @RequestMapping("/lrc/{musicId}")
    @ResponseBody
    public ResponseVO getLrc(@PathVariable("musicId") String musicId) {
        return NetEaseApi.getLyric(musicId);
    }

}