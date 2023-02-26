package com.site.blog.service.impl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.HexUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.context.ConfigContextHolder;
import com.site.blog.mapper.EmailMapper;
import com.site.blog.model.entity.EmailConfig;
import com.site.blog.service.MailService;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import jakarta.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class MailServiceImpl extends ServiceImpl<EmailMapper, EmailConfig> implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private EmailMapper emailMapper;
    @Value("${spring.mail.username}")
    private String from;


    @Override
    public EmailConfig getDefaultMail() {
        var query = new LambdaQueryWrapper<EmailConfig>().eq(EmailConfig::getId, "1");
        return emailMapper.selectOne(query);
    }

    @Override
    public void sendTemplateEmail(String to, String subject, Map<String, Object> hashMap) {

        try {
            PebbleEngine engine = new PebbleEngine.Builder().build();
            PebbleTemplate compiledTemplate = engine.getTemplate("emailTemplate/emailFindPass.html");
            Writer writer = new StringWriter();


            compiledTemplate.evaluate(writer, hashMap);

            String output = writer.toString();
            sendHtmlMail(to, subject, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送简单邮件
     *
     * @param to      来
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }

    public static MimeMessage mimeMessage(EmailConfig emailConfig) {
        Console.log(emailConfig.toString());
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");
        p.put("mail.smtp.timeout", "20000");
        p.setProperty("mail.smtp.host", emailConfig.getEmailUrl());
        p.setProperty("mail.smtp.port", emailConfig.getPort());
        p.setProperty("mail.smtp.socketFactory.port", emailConfig.getPort());
        p.setProperty("mail.smtp.socketFactory.class", "jakarta.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(p, new Authenticator() {
            // 设置认证账户信息
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getEmail(), emailConfig.getEmailKey());
            }
        });
        session.setDebug(true);
        return new MimeMessage(session);
    }

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();


        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }


    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);
            //helper.addAttachment("test"+fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

    @Override
    public void sendFindPassEmail(String toEmail, EmailConfig emailConfig) {
        try {
            Map<String, Object> hashMap = new HashMap<>(16);
            var pass="123456";
            var newPass = HexUtil.encodeHexStr(pass);
            hashMap.put("websiteName", ConfigContextHolder.websiteName());
            hashMap.put("newPass", pass);
            hashMap.put("url", ConfigContextHolder.domain() + "/v2/auth/findPass?email=" + toEmail + "&cip=" + newPass);


            PebbleEngine engine = new PebbleEngine.Builder().build();
            PebbleTemplate compiledTemplate = engine.getTemplate("emailTemplate/emailFindPass.html");
            Writer writer = new StringWriter();


            compiledTemplate.evaluate(writer, hashMap);

            String content = writer.toString();


            MimeMessage message = mimeMessage(emailConfig);
            message.setFrom(emailConfig.getEmail());
            message.setRecipients(Message.RecipientType.TO, toEmail);
            message.setSubject("找回密码");
            //不然会乱码
            message.setContent(content, "text/html;charset=gbk");
            message.setSentDate(new Date());
            message.saveChanges();
            Transport.send(message);
            logger.info("找回密码邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}