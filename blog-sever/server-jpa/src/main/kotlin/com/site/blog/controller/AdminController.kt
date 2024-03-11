package com.site.blog.controller

import com.site.blog.constants.BaseConstants
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.constants.SessionConstants
import com.site.blog.model.entity.AdminUser
import com.site.blog.model.entity.BlogConfig
import com.site.blog.model.vo.UserVo
import com.site.blog.service.*
import com.site.blog.util.RequestHelper

import com.site.blog.util.BaseResult.getResultByHttp
import com.site.blog.util.ResultDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.web.bind.annotation.*
import java.lang.Boolean
import java.util.function.Consumer
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import kotlin.Any
import kotlin.Exception
import kotlin.String

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/7/9 13:43
 * @modified By:
 */
@RestController
@RequestMapping("/v2/admin")
@Tag(name = "后台json", description = "后台json")
class AdminController(
    private val adminUserService: AdminUserService,
    private val blogInfoService: BlogInfoService,
    private val tagService: TagService,
    private val categoryService: CategoryService,
    private val commentService: CommentService,
    private val blogConfigService: BlogConfigService,
    private val linkService: LinkService
) {
    @PostMapping("/del/{id}")
    fun removeUser(@PathVariable("id") id: Long): ResultDto<*> {
        return if (id == null) {
            getResultByHttp(HttpStatusEnum.BAD_REQUEST,false, "请输入id")
        } else {
            val flag = adminUserService.removeById(id)
            getResultByHttp(HttpStatusEnum.OK,true, flag)
        }
    }

    @GetMapping("/dashboard")
    fun dashboard(): ResultDto<*> {
        val res = HashMap<String, Any>()
        val articleCount = blogInfoService.count()
        val commentCount = commentService.count()
        val views  = blogInfoService.getViewsSum()
        res["articleCount"] = articleCount
        res["commentCount"] = commentCount
        res["viewsCount"] = views
        return getResultByHttp(HttpStatusEnum.OK, true, res)
    }

    @PostMapping("/userEdit")
    fun editUser(userVo: UserVo): ResultDto<*> {
        val sqlUser = adminUserService.getAdminUserById(userVo.id)
        BeanUtils.copyProperties(userVo, sqlUser!!)
        var conf = blogConfigService.getOne(KtQueryWrapper(BlogConfig::class.java).eq(BlogConfig::configCode, "sysAuthorImg"))
        conf!!.configValue = userVo.avatar
        blogConfigService.updateById(conf)
        adminUserService.updateUserInfo(sqlUser)
        return getResultByHttp<AdminUser?>(HttpStatusEnum.OK, true, sqlUser)
    }

    @get:GetMapping("/users")
    val users: ResultDto<*>
        get() {
            val users = adminUserService.list()

            val userVos = ArrayList<UserVo>()
            users.forEach(Consumer { item: AdminUser? ->
                val userVoItem = UserVo()
                BeanUtils.copyProperties(item!!, userVoItem)
                userVos.add(userVoItem)
            })
            return getResultByHttp(HttpStatusEnum.OK, true, userVos)
        }

    /**
     * @Description: 验证密码是否正确
     * @Param: [oldPwd, session]
     * @return: com.zhulin.blog.dto.Result
     * @date: 2019/8/25 9:15
     */
    @GetMapping("/password")
    fun validatePassword(oldPwd: String, session: HttpSession): ResultDto<String> {
        val userId = session.getAttribute(SessionConstants.LOGIN_USER_ID) as Long
        val flag = adminUserService.validatePassword(userId, oldPwd)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK)
        } else getResultByHttp(HttpStatusEnum.BAD_REQUEST)
    }

    @PostMapping("/unlock/{id}")
    fun unlock(@PathVariable("id") id: Long): ResultDto<*> {
        val user = adminUserService.getAdminUserById(id)
        val currentUser = RequestHelper.getSessionUser()
        if (user!!.id == currentUser!!.id) {
            return getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, "不能冻结自己")
        }
        user.locked = Boolean.TRUE == user.locked
        adminUserService.updateById(user)
        return getResultByHttp(HttpStatusEnum.OK, user.locked)
    }

    @GetMapping("/getUser")
    fun getUserInfo(request: HttpServletRequest): ResultDto<*> {
        return try {
            val user = request.getAttribute(BaseConstants.USER_ATTR) as UserVo
                ?: return getResultByHttp(HttpStatusEnum.UNAUTHORIZED, false, "请重新登录")
            log.info(user.toString())
            getResultByHttp(HttpStatusEnum.OK, true, user)
        } catch (e: Exception) {
            e.printStackTrace()
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, e.message)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(AdminController::class.java)
    }
}