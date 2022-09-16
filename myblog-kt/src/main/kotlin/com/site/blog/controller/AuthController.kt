package com.site.blog.controller

import cn.hutool.core.lang.UUID
import cn.hutool.core.util.HexUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.constants.SessionConstants
import com.site.blog.constants.SysConfigConstants
import com.site.blog.context.ConfigContextHolder.domain
import com.site.blog.model.entity.AdminUser

import com.site.blog.model.vo.UserVo
import com.site.blog.service.AdminUserService
import com.site.blog.service.BlogConfigService
import com.site.blog.service.MailService
import com.site.blog.util.JwtUtil.sign
import com.site.blog.util.MD5Utils.MD5Encode
import com.site.blog.util.RequestHelper

import com.site.blog.util.Result.getResultByHttp
import com.site.blog.util.ResultDto
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpSession

/**
 * @author yanni
 * @date time 2022/6/17 20:37
 * @modified By:
 */
@Controller
@RequestMapping("/v2/auth")
class AuthController(
    private val adminUserService: AdminUserService,
    private val mailService: MailService,
    private val blogConfigService: BlogConfigService
) {
    @PostMapping(value = ["/login"])
    @ResponseBody
    fun login(
        username: String, password: String?,
        session: HttpSession
    ): ResultDto<*> {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return getResultByHttp(HttpStatusEnum.BAD_REQUEST)
        }
        val queryWrapper: QueryWrapper<AdminUser > = QueryWrapper<AdminUser>(
           AdminUser(username=username, password = MD5Encode(password, "UTF-8"))
        )
        val adminUser = adminUserService.getOne(queryWrapper)
        return if (adminUser != null) {
            if (!adminUser.locked!!) {
                val token = sign(adminUser.username, adminUser.id)
                session.setAttribute(SessionConstants.LOGIN_USER, adminUser.nickname)
                session.setAttribute(SessionConstants.LOGIN_USER_ID, adminUser.id)
                session.setAttribute(SessionConstants.LOGIN_USER_NAME, adminUser.username)
                session.setAttribute(
                    SessionConstants.AUTHOR_IMG, blogConfigService.getById(
                        SysConfigConstants.SYS_AUTHOR_IMG.configField
                    )
                )
                getResultByHttp(HttpStatusEnum.OK, true, token)
            } else {
                getResultByHttp(HttpStatusEnum.UNAUTHORIZED, false, "账户已被冻结")
            }
        } else {
            getResultByHttp(HttpStatusEnum.UNAUTHORIZED)
        }
    }

    /**
     * 获取html中的内容
     */
    @PostMapping(value = ["/reg"])
    @ResponseBody
    fun register(username: String?, password: String?): ResultDto<String> {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return getResultByHttp(HttpStatusEnum.BAD_REQUEST)
        }
        val queryWrapper = KtQueryWrapper (AdminUser()).eq(AdminUser::username, username)
        val adminUser = adminUserService.getOne(queryWrapper)
        return if (adminUser != null) {
            getResultByHttp(HttpStatusEnum.BAD_REQUEST, false, "用户名已存在")
        } else {
            val regUser = AdminUser(username=username,password=password,
                uuid=UUID.fastUUID().toString(),locked=true,role=0, )
            adminUserService.register(regUser)
            getResultByHttp(HttpStatusEnum.OK, true, "注册成功")
        }
    }

    @PostMapping("/findPassByMail/{email}")
    @ResponseBody
    fun findPassEmail(@PathVariable("email") email: String?): ResultDto<String> {
        mailService.sendFindPassEmail(email, mailService.defaultMail)
        return getResultByHttp(HttpStatusEnum.OK, true, "已发送邮件")
    }

    @PostMapping("/findPass")
    @ResponseBody
    fun sengFindPassEmail(): ResultDto<UserVo?> {
        val user = RequestHelper.getSessionUser()
        mailService.sendFindPassEmail(user!!.email, mailService.defaultMail)
        return getResultByHttp(HttpStatusEnum.OK, true, user)
    }

    @GetMapping("/findPass")
    fun findPass(model: Model, @RequestParam("email") email: String?, @RequestParam("cip") cip: String?): String {
        try {
            val sysUser = adminUserService.getOne(KtQueryWrapper(AdminUser()).eq(AdminUser::email, email))
            val userFlag = Optional.ofNullable(sysUser)
            if (userFlag.isPresent) {
                val newPass = HexUtil.decodeHexStr(cip)
                sysUser!!.password = newPass
                val result = adminUserService.updateUserInfo(sysUser)
                model.addAttribute("title", "成功")
                model.addAttribute("name", "新密码:$newPass") //
                model.addAttribute("note", "密码已被系统重置，请登录修改你的新密码")
            } else {
                model.addAttribute("title", "抱歉")
                model.addAttribute("name", "为获取到用户信息")
                model.addAttribute("note", "操作失败")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            model.addAttribute("title", "抱歉")
            model.addAttribute("name", "系统操作过程中发生错误")
            model.addAttribute("note", "操作失败")
        }
        model.addAttribute("webhost", domain())
        return "msg"
    }
}