
package com.cchcz.blog.controller.admin;

import com.cchcz.blog.aspect.annotation.BussinessLog;
import com.cchcz.blog.model.entity.User;
import com.cchcz.blog.model.entity.UserPwd;
import com.cchcz.blog.model.object.ResponseVO;
import com.cchcz.blog.model.property.AppProperties;
import com.cchcz.blog.util.RequestUtil;
import com.cchcz.blog.util.ResultUtil;
import com.cchcz.blog.util.SessionUtil;
import com.cchcz.blog.constant.SessionConstant;
import com.cchcz.blog.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;

/**
 * 登录相关
 *
 * @author cchcz
 * @version 1.0
 * @date 2018/4/24 14:37
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/passport")
public class PassportController {

    @Autowired
    private AppProperties config;
    @Autowired
    private SysUserService userService;

    @BussinessLog("进入登录页面")
    @GetMapping("/login")
    public ModelAndView login(Model model) {
        model.addAttribute("enableKaptcha", config.getEnableKaptcha());
        return ResultUtil.viewForAdmin("login");
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/signin")
    @ResponseBody
    public ResponseVO submitLogin(String username, String password, boolean rememberMe, String kaptcha) {
        if (config.getEnableKaptcha()) {
            if (StringUtils.isEmpty(kaptcha) || !kaptcha.equals(SessionUtil.getKaptcha())) {
                return ResultUtil.error("验证码错误！");
            }
            SessionUtil.removeKaptcha();
        }
        try {
            User user = userService.login(username, password);
            SessionUtil.setUser(user);
            if (rememberMe) {
                SessionUtil.setCookie(user.getId(), true);
            } else {
                SessionUtil.setCookie(user.getId(), false);
            }
            return ResultUtil.success("登录成功！");
        } catch (Exception e) {
            log.error("登录失败，用户名[{}]", username, e);
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @return
     */
    @PostMapping("/updatePwd")
    @ResponseBody
    public ResponseVO updatePwd(@Validated UserPwd userPwd, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(bindingResult.getFieldError().getDefaultMessage());
        }
        boolean result = userService.updatePwd(userPwd);
        SessionUtil.removeAllSession();
        return ResultUtil.success(result ? "密码已修改成功，请重新登录" : "密码修改失败");
    }

    /**
     * 使用权限管理工具进行用户的退出，跳出登录，给出提示信息
     *
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/logout")
    public ModelAndView logout(RedirectAttributes redirectAttributes) {
        SessionUtil.removeUser();
        Cookie cookie = new Cookie(SessionConstant.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);// 立即销毁cookie
        cookie.setPath("/");
        RequestUtil.getResponse().addCookie(cookie);
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return ResultUtil.redirect("/admin/index");
    }
}
