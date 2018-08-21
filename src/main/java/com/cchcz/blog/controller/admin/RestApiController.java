
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.model.enums.QiniuUploadType;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.util.FileUtil;
import com.cchcz.blog.util.ResultUtil;
import com.google.common.collect.Maps;
import com.cchcz.blog.service.SysAttachService;
import com.cchcz.blog.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 其他api性质的接口
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/api")
public class RestApiController {

    @Autowired
    private SysAttachService sysAttachService;
    @Autowired
    private SysConfigService configService;

    /**
     * 上传文件到七牛云
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseVO upload(@RequestParam("file") MultipartFile file) {
        String filePath = FileUtil.uploadToQiniu(file, QiniuUploadType.SIMPLE, false);
        return ResultUtil.success("图片上传成功", filePath);
    }

    @PostMapping("/uploadForMd")
    public Object uploadForMd(@RequestParam("file") MultipartFile file) {
        String filePath = FileUtil.uploadToQiniu(file, QiniuUploadType.SIMPLE, false);
        Config config = configService.get();
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("success", 1);
        resultMap.put("message", "上传成功");
        resultMap.put("filename", config.getQiuniuBasePath() + filePath + "-pw");
        return resultMap;
    }

    /**
     * 发布文章选择图片时获取素材库
     *
     * @return
     */
    @PostMapping("/material")
    public ResponseVO material() {
        return ResultUtil.success("", sysAttachService.listMaterial());
    }
}
