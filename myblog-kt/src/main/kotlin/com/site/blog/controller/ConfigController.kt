package com.site.blog.controller

import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.dto.Result
import com.site.blog.model.entity.BlogConfig
import com.site.blog.service.BlogConfigService
import com.site.blog.util.ResultGenerator.getResultByHttp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.annotation.Resource

@RestController
@RequestMapping("/v2/admin")
@Tag(name = "配置信息")
class ConfigController(val blogConfigService: BlogConfigService) {


    /**
     * 返回系统配置信息
     * @param
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogConfig>
     * @date 2019/8/29 19:30
    </com.site.blog.entity.BlogConfig> */
    @GetMapping("/blogConfig/list")
   fun blogConfig(): Result<AjaxResultPage<BlogConfig?>>
       {
            val ajaxResultPage = AjaxResultPage<BlogConfig?>()
            val list = blogConfigService.query().orderByDesc("update_time").list()
            if (CollectionUtils.isEmpty(list)) {
                ajaxResultPage.code = 500
                return getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, ajaxResultPage)
            }
            ajaxResultPage.list = list
            return getResultByHttp(HttpStatusEnum.OK, true, ajaxResultPage)
        }

    /**
     * 修改系统信息
     * @param blogConfig
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 19:45
     */
    @PostMapping("/blogConfig/edit")
    fun updateBlogConfig(@RequestBody blogConfig: BlogConfig): Result<String> {
        blogConfig.updateTime = LocalDateTime.now()
        val flag = blogConfigService.updateById(blogConfig)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK)
        } else {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * 新增系统信息项
     * @param blogConfig
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 10:57
     */
    @PostMapping("/blogConfig/add")
    @SysLogAnnotation(title = "添加配置", opType = LogOperationEnum.ADD)
    fun addBlogConfig(@RequestBody blogConfig: BlogConfig): Result<*> {
        blogConfig.createTime = LocalDateTime.now()
        blogConfig.updateTime = LocalDateTime.now()
        val flag = blogConfigService.save(blogConfig)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK)
        } else {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * 删除配置信息项
     * @param configField
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 11:21
     */
    @DeleteMapping("/blogConfig/del/{id}")
    @SysLogAnnotation(title = "删除配置", opType = LogOperationEnum.DELETE)
    fun delBlogConfig(@PathVariable("id") configField: String?): Result<String> {
        val flag = blogConfigService.removeById(configField)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK)
        } else {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
    }
}