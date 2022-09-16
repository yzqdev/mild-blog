package com.site.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.AjaxPutPage
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.entity.Tag
import com.site.blog.service.TagService
import com.site.blog.util.Result.getResultByHttp
import com.site.blog.util.ResultDto
import lombok.extern.slf4j.Slf4j
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.annotation.Resource

@Slf4j
@RestController
@RequestMapping("/v2/admin")
class TagController {
    @Resource
    private val tagService: TagService? = null

    /**
     * 返回未删除状态下的所有标签
     * @return
     */
    @GetMapping("/tags/list")
    fun tagsList(ajaxPutPage: AjaxPutPage<Tag?>): ResultDto<AjaxResultPage<Tag?>> {
        val queryWrapper = KtQueryWrapper (Tag())
        Optional.ofNullable(ajaxPutPage.show)
            .ifPresent { show: Boolean? -> queryWrapper.eq(Tag::show, ajaxPutPage.show) }
        val page = ajaxPutPage.putPageToPage()
        tagService?.page (page, queryWrapper)
        val result = AjaxResultPage<Tag?>()
        result.list = page.records
        result.count = page.total
        if (CollectionUtils.isEmpty(page.records)) {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
        return getResultByHttp(HttpStatusEnum.OK, true, result)
    }

    /**
     * 标签分页
     *
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.Tag>
     * @date 2019/9/1 11:20
    </com.site.blog.entity.Tag> */
    @GetMapping("/tags/paging")
    fun getCategoryList(ajaxPutPage: AjaxPutPage<Tag?>, condition: Tag): AjaxResultPage<Tag?> {
        val queryWrapper = QueryWrapper(condition)
        queryWrapper.lambda()
            .ne(Tag::tagId, 1)
        val page = ajaxPutPage.putPageToPage()
        tagService!!.page (page, queryWrapper)
        val result = AjaxResultPage<Tag?>()
        result.list = page.records
        result.count = page.total
        return result
    }

    /**
     * 修改标签状态
     *
     * @param tag
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 14:55
     */
    @PostMapping("/tags/isDel")
    fun updateCategoryStatus(tag: Tag): ResultDto<out String?> {
        tag.updateTime = LocalDateTime.now()
        val flag = tagService!!.updateById(tag)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK, tag.tagName)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR )
    }

    /**
     * 添加标签
     *
     * @param tag
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/2 10:12
     */
    @PostMapping("/tags/add")
    @SysLogAnnotation(title = "添加标签", opType = LogOperationEnum.ADD)
    fun addTag(tag: Tag): ResultDto<*> {
        tag.createTime = LocalDateTime.now()
        tag.updateTime = LocalDateTime.now()
        val flag = tagService!!.save(tag)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK, tag)
        } else {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * 清除标签
     *
     * @param tagId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/2 18:41
     */
    @PostMapping("/tags/clear/{id}")
    @SysLogAnnotation(title = "清除标签", opType = LogOperationEnum.CLEAN)
    @Throws(
        RuntimeException::class
    )
    fun clearTag(@PathVariable("id") tagId: String?): ResultDto<out String?> {
        val name = tagService!!.getById(tagId)!!.tagName
        return if (tagService.clearTag(tagId) == 1) {
            getResultByHttp(HttpStatusEnum.OK, name)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 修改标题名字
     *
     * @return com.site.blog.pojo.dto.ResultDto<java.lang.String>
     * @author Linn-cn
     * @date 2020/9/1
    </java.lang.String> */
    @PostMapping("/tags/update")
    @SysLogAnnotation(title = "更新标签", opType = LogOperationEnum.UPDATE)
    fun updateCategory(@RequestBody tag: Tag): ResultDto<Tag> {
        tag.updateTime = LocalDateTime.now()
        tagService!!.updateById(tag)
        return getResultByHttp(HttpStatusEnum.OK, true, tag)
    }
}