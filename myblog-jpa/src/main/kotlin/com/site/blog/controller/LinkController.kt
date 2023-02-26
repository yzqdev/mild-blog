package com.site.blog.controller

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.constants.LinkConstants
import com.site.blog.model.dto.AjaxPutPage
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.entity.Link
import com.site.blog.service.LinkService
import com.site.blog.util.DateUtils.localCurrentDate
import com.site.blog.util.BaseResult.getResultByHttp
import com.site.blog.util.ResultDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp
import java.time.LocalDateTime

/**
 * @author yzqde
 * @qq 1320291471
 * @Description: 友链Controller
 * @create 2020/12/27
 */
@RestController
@RequestMapping("/v2/admin")
@Tag(name = "链接json")
class LinkController(private val linkService: LinkService) {

    @GetMapping("/linkType/list")
    fun linkTypeList(): ResultDto<List<Link>> {
        val links: MutableList<Link> = ArrayList()
        links.add(
            Link(
                linkType = LinkConstants.LINK_TYPE_FRIENDSHIP.linkTypeId,
                linkName = LinkConstants.LINK_TYPE_FRIENDSHIP.linkTypeName
            )
        )
        links.add(
            Link(
                linkType = LinkConstants.LINK_TYPE_RECOMMEND.linkTypeId,
                linkName = LinkConstants.LINK_TYPE_RECOMMEND.linkTypeName
            )
        )
        links.add(
            Link(
                linkType = LinkConstants.LINK_TYPE_PRIVATE.linkTypeId,
                linkName = LinkConstants.LINK_TYPE_PRIVATE.linkTypeName
            )
        )


        return getResultByHttp(HttpStatusEnum.OK, links)
    }

    /**
     * /v2/admin/link/paging?page=1&limit=30
     *
     * @param ajaxPutPage
     * @return
     */
    @GetMapping("/link/paging")
    fun getLinkList(ajaxPutPage: AjaxPutPage<Link?>?): ResultDto<*> {
        val queryWrapper = KtQueryWrapper(Link())
        queryWrapper
            .orderByAsc(Link::linkRank)
        return if (ajaxPutPage != null) {
            val page = ajaxPutPage.putPageToPage()
            linkService.page(
                page,
                queryWrapper
            )
            val result = AjaxResultPage<Link?>()
            result.list = page.records
            result.count = page.total
            getResultByHttp(
                HttpStatusEnum.OK,
                true,
                result
            )
        } else {
            getResultByHttp(HttpStatusEnum.BAD_REQUEST, false, "fail")
        }
    }

    @PostMapping("/link/hide")
    @SysLogAnnotation(title = "删除链接", opType = LogOperationEnum.CHANGE_STATUS)
    fun updateLinkStatus(link: Link): ResultDto<*> {
        val flag = linkService.updateById(link)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK, link)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    @DeleteMapping("/link/clear/{id}")
    @SysLogAnnotation(title = "清除链接", opType = LogOperationEnum.CLEAN)
    fun clearLink(@PathVariable("id") linkId: String): ResultDto<*> {
        val flag = linkService.removeById(linkId)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK, true, linkId)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    @PostMapping("/link/edit")
    @SysLogAnnotation(title = "编辑链接", opType = LogOperationEnum.EDIT)
    fun updateAndSaveLink(link: Link): ResultDto<*> {
        try {
            link.createTime = localCurrentDate
            val flag: Boolean
            if (link.linkId?.isNotEmpty() == true) {
                println(link.linkId)
                println("编辑链接->更改")
                link.updateTime = Timestamp.valueOf(LocalDateTime.now())
                flag = linkService.updateById(link)
            } else {
                link.linkRank = 1
                link.show = true
                flag = linkService.save(link)
            }
            if (flag) {
                return getResultByHttp(HttpStatusEnum.OK, link.linkId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, e.message)
        }
        return getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }
}