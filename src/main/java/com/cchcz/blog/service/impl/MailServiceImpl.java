
package com.cchcz.blog.service.impl;

import com.cchcz.blog.model.entity.*;
import com.cchcz.blog.model.enums.TemplateKeyEnum;
import com.cchcz.blog.util.FreeMarkerUtil;
import com.cchcz.blog.model.entity.*;
import com.cchcz.blog.service.MailService;
import com.cchcz.blog.service.SysConfigService;
import com.cchcz.blog.service.SysTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邮件发送
 *
 * @author cchcz
 * @version 1.0

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SysTemplateService templateService;
    @Autowired
    private SysConfigService configService;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 普通的发送
     *
     * @param mailDetail
     * @return
     */
    @Override
    @Async
    public void send(MailDetail mailDetail) {
        sendMessage(mailDetail, from);
    }

    /**
     * 发送友情链接邮件通知
     *
     * @param link
     * @param keyEnum
     * @return
     */
    @Override
    @Async
    public void send(Link link, TemplateKeyEnum keyEnum) {
        if (!StringUtils.isEmpty(link.getEmail())) {
            Config config = configService.get();
            Template template = templateService.getTemplate(keyEnum);
            String temXml = template.getRefValue();
            Map<String, Object> map = new HashMap<>(2);
            map.put("link", link);
            map.put("config", config);
            String mailContext = FreeMarkerUtil.template2String(temXml, map, true);

            MailDetail mailDetail = new MailDetail("友情链接操作通知", link.getEmail(), link.getName(), mailContext);
            send(mailDetail);
        }
        this.sendToAdmin(link);
    }

    /**
     * 发送评论邮件通知
     *
     * @param comment
     * @param keyEnum
     * @param audit
     * @return
     */
    @Override
    @Async
    public void send(Comment comment, TemplateKeyEnum keyEnum, boolean audit) {
        if (comment == null || StringUtils.isEmpty(comment.getEmail())) {
            this.sendToAdmin(comment);
            return;
        }
        Config config = configService.get();
        Template template = templateService.getTemplate(keyEnum);
        String temXml = template.getRefValue();
        Map<String, Object> map = new HashMap<>(2);
        map.put("comment", comment);
        map.put("config", config);
        String mailContext = FreeMarkerUtil.template2String(temXml, map, true);
        String subject = "评论回复通知";
        if (audit) {
            subject = "评论审核结果通知";
        }
        MailDetail mailDetail = new MailDetail(subject, comment.getEmail(), comment.getNickname(), mailContext);
        send(mailDetail);
        if (!audit) {
            this.sendToAdmin(comment);
        }
    }

    /**
     * 发送到管理员的友链操作通知
     *
     * @param link
     */
    @Override
    @Async
    public void sendToAdmin(Link link) {
        Config config = configService.get();
        Template template = templateService.getTemplate(TemplateKeyEnum.TM_LINKS_TO_ADMIN);
        String temXml = template.getRefValue();
        Map<String, Object> map = new HashMap<>(1);
        map.put("link", link);
        String mailContext = FreeMarkerUtil.template2String(temXml, map, true);
        String adminEmail = config.getAuthorEmail();
        adminEmail = StringUtils.isEmpty(adminEmail) ? "1098308189@qq.com" : (adminEmail.contains("#") ? adminEmail.replace("#", "@") : adminEmail);
        MailDetail mailDetail = new MailDetail("有新的友链消息", adminEmail, config.getAuthorName(), mailContext);
        send(mailDetail);
    }

    /**
     * 发送到管理员的评论通知
     *
     * @param comment
     */
    @Override
    @Async
    public void sendToAdmin(Comment comment) {
        Config config = configService.get();
        Template template = templateService.getTemplate(TemplateKeyEnum.TM_NEW_COMMENT);
        String temXml = template.getRefValue();
        Map<String, Object> map = new HashMap<>(2);
        map.put("comment", comment);
        map.put("config", config);
        String mailContext = FreeMarkerUtil.template2String(temXml, map, true);
        String subject = "有新的评论消息";
        String adminEmail = config.getAuthorEmail();
        adminEmail = StringUtils.isEmpty(adminEmail) ? "1098308189@qq.com" : (adminEmail.contains("#") ? adminEmail.replace("#", "@") : adminEmail);
        MailDetail mailDetail = new MailDetail(subject, adminEmail, config.getAuthorName(), mailContext);
        send(mailDetail);
    }


    private boolean sendMessage(MailDetail detail, String from) {
        log.info("Start to send html email for [{}({})]", detail.getToUsername(), detail.getToMailAddress());
        if (StringUtils.isEmpty(detail.getToMailAddress())) {
            log.warn("邮件接收者为空！");
            return false;
        }
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // 创建邮件发送者地址
            InternetAddress fromAddress = new InternetAddress(MimeUtility.encodeText("网站管理员") + "<" + from + ">");
            helper.setFrom(fromAddress);
            // 创建邮件接收者地址
            InternetAddress toAddress = new InternetAddress(MimeUtility.encodeText(detail.getToMailAddress()) + "<" + detail.getToMailAddress() + ">");
            helper.setTo(toAddress);
            helper.setSubject(detail.getSubject());
            // 第二个参数指定发送的是HTML格式
            helper.setText(detail.getContent(), detail.isHtml());
            if (detail.getCc() != null && detail.getCc().length > 0) {
                helper.setCc(detail.getCc());
            }
            if (detail.isExitFile()) {
                try {
                    List<String> filePaths = detail.getFilePaths();
                    for (String filePath : filePaths) {
                        // 附件 ：注意项目路径问题，自动补用项目路径
                        FileSystemResource file = new FileSystemResource(new File(filePath));
                        helper.addAttachment("图片.jpg", file);
                    }
                } catch (Exception e) {
                    log.error("添加附件发生异常", e);
                }
            }
            javaMailSender.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send E-mail. e [{}]", e.getMessage());
        }
        return false;
    }
}
