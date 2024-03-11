package com.site.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.model.entity.EmailConfig;

import java.util.Map;

/**
 * @Author: Yangzhengqian
 * @Description:
 * @Date:Created time 2020/11/12 上午10:46
 * @Modified By:
 */

public interface MailService  extends IService<EmailConfig> {
EmailConfig getDefaultMail();
    void sendTemplateEmail(String to, String subject, Map<String, Object> content);

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
    void sendFindPassEmail(String toEmail, EmailConfig emailConfig);


}