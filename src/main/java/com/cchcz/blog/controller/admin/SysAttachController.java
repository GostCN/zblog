package com.cchcz.blog.controller.admin;

import com.cchcz.blog.dao.beans.SysAttach;
import com.cchcz.blog.model.enums.QiniuUploadType;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.vo.SysAttachConditionVO;
import com.cchcz.blog.util.BlogUtils;
import com.cchcz.blog.util.FileUtil;
import com.cchcz.blog.util.RequestUtil;
import com.cchcz.blog.util.ResultUtil;
import com.github.pagehelper.PageInfo;
import com.cchcz.blog.service.SysAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 附件管理
 * <p>
 * Created by 13 on 2017/2/21.
 */
@Slf4j
@Controller
@RequestMapping("admin/attachs")
public class SysAttachController {
    public static final String CLASSPATH = BlogUtils.getUplodFilePath();
    private static final int MAX_FILE_SIZE = 1048576;
    @Resource
    private SysAttachService sysAttachService;


    /**
     * 附件页面
     * <p>
     * //     * @param pageNumber
     * //     * @param pageSize
     *
     * @return
     */
    @RequestMapping(value = "")
    public ModelAndView list(SysAttachConditionVO vo) {
        vo.setPageSize(12);
        vo.getSysAttach().setStatus("1");
        PageInfo<SysAttach> pageInfo = sysAttachService.findPageBreakByCondition(vo);
        HttpServletRequest request = RequestUtil.getRequest();
        request.setAttribute("attachs", pageInfo);
        request.setAttribute("max_file_size", MAX_FILE_SIZE / 1024);
        return ResultUtil.viewForAdmin("attach/list");
    }

    @RequestMapping("upload")
    @ResponseBody
    public ResponseVO upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) {
        for (MultipartFile file : multipartFiles) {
            FileUtil.uploadToQiniu(file, QiniuUploadType.SIMPLE, false);
        }
        return ResultUtil.success("图片上传成功", "");
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public ResponseVO delete(@RequestParam Integer id, HttpServletRequest request) {
        try {
            SysAttach attach = sysAttachService.selectById(id);
            if (null == attach) {
                return ResultUtil.error("不存在该附件");
            }
            attach.setStatus("0");
            sysAttachService.updateSelective(attach);
//            new File(CLASSPATH + attach.getFkey()).delete();
        } catch (Exception e) {
            String msg = "附件删除失败";
            log.error(msg, e);
            return ResultUtil.error(msg);
        }
        return ResultUtil.success("成功");
    }

}
