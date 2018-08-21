
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.model.plugin.kaptcha.Captcha;
import com.cchcz.blog.model.plugin.kaptcha.GifCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 验证码
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/24 14:37
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class KaptchaController {

    @GetMapping("/getKaptcha")
    @ResponseBody
    public void getKaptcha(HttpServletResponse response) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/gif");
            /**
             * gif格式动画验证码
             * 宽，高，位数。
             */
            Captcha captcha = new GifCaptcha(146,33,4);
            //输出
            captcha.out(response.getOutputStream());
        } catch (Exception e) {
            log.error("获取验证码异常：{}", e.getMessage());
        }
    }

}
