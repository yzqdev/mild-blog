package com.site.blog.service


import com.site.blog.model.entity.EmailConfig

/**
 * @Author: Yangzhengqian
 * @Description:
 * @Date:Created time 2020/11/12 上午10:46
 * @Modified By:
 */
interface MailService  {
    val defaultMail: EmailConfig?
    fun sendTemplateEmail(to: String?, subject: String?, content: Map<String?, Any?>?)
    fun sendSimpleMail(to: String?, subject: String?, content: String?)
    fun sendHtmlMail(to: String?, subject: String?, content: String?)
    fun sendAttachmentsMail(to: String?, subject: String?, content: String?, filePath: String?)
    fun sendInlineResourceMail(to: String?, subject: String?, content: String?, rscPath: String?, rscId: String?)
    fun sendFindPassEmail(toEmail: String?, emailConfig: EmailConfig?)
}