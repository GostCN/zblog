
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.entity.Config;
import com.cchcz.blog.model.enums.QiniuUploadType;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.util.FileUtil;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统配置
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/24 14:37
 * @since 1.0
 */
@RestController
@RequestMapping("/admin/config")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    @PostMapping("/get")
    public ResponseVO get() {
        return ResultUtil.success(null, sysConfigService.get());
    }

    @PostMapping("/edit")
    public ResponseVO edit(Config config,
                           @RequestParam(required = false) MultipartFile wxPraiseCodeFile,
                           @RequestParam(required = false) MultipartFile zfbPraiseCodeFile) {
        config.setWxPraiseCode(FileUtil.uploadToQiniu(wxPraiseCodeFile, QiniuUploadType.QRCODE, true));
        config.setZfbPraiseCode(FileUtil.uploadToQiniu(zfbPraiseCodeFile, QiniuUploadType.QRCODE, true));
        try {
            sysConfigService.update(config);
        } catch (Exception e) {
            return ResultUtil.error("系统配置修改失败");
        }
        return ResultUtil.success("系统配置修改成功");
    }

}
