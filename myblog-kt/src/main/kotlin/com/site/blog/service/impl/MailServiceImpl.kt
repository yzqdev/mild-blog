package com.site.blog.service.impl

import cn.hutool.core.lang.Console
import cn.hutool.core.util.HexUtil
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.mitchellbosecke.pebble.PebbleEngine
import com.site.blog.context.ConfigContextHolder.domain
import com.site.blog.context.ConfigContextHolder.websiteName
import com.site.blog.mapper.EmailMapper
import com.site.blog.model.entity.EmailConfig
import com.site.blog.service.MailService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import java.io.StringWriter
import java.io.Writer
import java.util.*
import javax.annotation.Resource
import javax.mail.*
import javax.mail.internet.MimeMessage

@Service
class MailServiceImpl : ServiceImpl<EmailMapper?, EmailConfig?>(), MailService {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Resource
    private val mailSender: JavaMailSender? = null

    @Resource
    private val emailMapper: EmailMapper? = null

    @Value("\${spring.mail.username}")
    private val from: String? = null
    override val defaultMail: EmailConfig?
        get() {
            val query = LambdaQueryWrapper<EmailConfig>().eq(EmailConfig::id, "1")
            return emailMapper!!.selectOne(query)
        }

    override fun sendTemplateEmail(to: String?, subject: String?, hashMap: Map<String?, Any?>?) {
        try {
            val engine = PebbleEngine.Builder().build()
            val compiledTemplate = engine.getTemplate("emailTemplate/emailFindPass.html")
            val writer: Writer = StringWriter()
            compiledTemplate.evaluate(writer, hashMap)
            val output = writer.toString()
            sendHtmlMail(to, subject, output)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 发送简单邮件
     *
     * @param to      来
     * @param subject 主题
     * @param content 内容
     */
    override fun sendSimpleMail(to: String?, subject: String?, content: String?) {
        val message = SimpleMailMessage()
        message.setFrom(from!!)
        message.setTo(to)
        message.setSubject(subject!!)
        message.setText(content!!)
        try {
            mailSender!!.send(message)
            logger.info("简单邮件已经发送。")
        } catch (e: Exception) {
            logger.error("发送简单邮件时发生异常！", e)
        }
    }

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    override fun sendHtmlMail(to: String?, subject: String?, content: String?) {
        val message = mailSender!!.createMimeMessage()
        try {
            //true表示需要创建一个multipart message
            val helper = MimeMessageHelper(message, true)
            helper.setFrom(from!!)
            helper.setTo(to!!)
            helper.setSubject(subject!!)
            helper.setText(content!!, true)
            mailSender.send(message)
            logger.info("html邮件发送成功")
        } catch (e: MessagingException) {
            logger.error("发送html邮件时发生异常！", e)
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
    override fun sendAttachmentsMail(to: String?, subject: String?, content: String?, filePath: String?) {
        val message = mailSender!!.createMimeMessage()
        try {
            val helper = MimeMessageHelper(message, true)
            helper.setFrom(from!!)
            helper.setTo(to!!)
            helper.setSubject(subject!!)
            helper.setText(content!!, true)
            val file = FileSystemResource(File(filePath))
            val fileName = file.filename
            helper.addAttachment(fileName, file)
            //helper.addAttachment("test"+fileName, file);
            mailSender.send(message)
            logger.info("带附件的邮件已经发送。")
        } catch (e: MessagingException) {
            logger.error("发送带附件的邮件时发生异常！", e)
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
    override fun sendInlineResourceMail(
        to: String?,
        subject: String?,
        content: String?,
        rscPath: String?,
        rscId: String?
    ) {
        val message = mailSender!!.createMimeMessage()
        try {
            val helper = MimeMessageHelper(message, true)
            helper.setFrom(from!!)
            helper.setTo(to!!)
            helper.setSubject(subject!!)
            helper.setText(content!!, true)
            val res = FileSystemResource(File(rscPath))
            helper.addInline(rscId!!, res)
            mailSender.send(message)
            logger.info("嵌入静态资源的邮件已经发送。")
        } catch (e: MessagingException) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e)
        }
    }

    override fun sendFindPassEmail(toEmail: String?, emailConfig: EmailConfig?) {
        try {
            val hashMap: MutableMap<String, Any> = HashMap(16)
            val pass = "123456"
            val newPass = HexUtil.encodeHexStr(pass)
            hashMap["websiteName"] = websiteName()
            hashMap["newPass"] = pass
            hashMap["url"] = domain() + "/v2/auth/findPass?email=" + toEmail + "&cip=" + newPass
            val engine = PebbleEngine.Builder().build()
            val compiledTemplate = engine.getTemplate("emailTemplate/emailFindPass.html")
            val writer: Writer = StringWriter()
            compiledTemplate.evaluate(writer, hashMap)
            val content = writer.toString()
            val message = mimeMessage(emailConfig)
            message.setFrom(emailConfig!!.email)
            message.setRecipients(Message.RecipientType.TO, toEmail)
            message.subject = "找回密码"
            //不然会乱码
            message.setContent(content, "text/html;charset=gbk")
            message.sentDate = Date()
            message.saveChanges()
            Transport.send(message)
            logger.info("找回密码邮件发送成功")
        } catch (e: MessagingException) {
            logger.error("发送html邮件时发生异常！", e)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun mimeMessage(emailConfig: EmailConfig?): MimeMessage {
            Console.log(emailConfig.toString())
            val p = Properties()
            p.setProperty("mail.smtp.auth", "true")
            p["mail.smtp.timeout"] = "20000"
            p.setProperty("mail.smtp.host", emailConfig!!.emailUrl)
            p.setProperty("mail.smtp.port", emailConfig.port)
            p.setProperty("mail.smtp.socketFactory.port", emailConfig.port)
            p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            val session = Session.getInstance(p, object : Authenticator() {
                // 设置认证账户信息
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(emailConfig.email, emailConfig.emailKey)
                }
            })
            session.debug = true
            return MimeMessage(session)
        }
    }
}