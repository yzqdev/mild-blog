package com.site.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.model.dto.AjaxPutPage
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.entity.Comment
import com.site.blog.model.vo.CommentVo
import com.site.blog.service.BlogInfoService
import com.site.blog.service.CommentService
import com.site.blog.util.BaseResult
import com.site.blog.util.BeanMapUtil
import com.site.blog.util.BaseResult.getResultByHttp
import com.site.blog.util.ResultDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.commons.text.StringEscapeUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.function.Consumer

@RestController
@RequestMapping("/v2/admin")
@Tag(name = "评论json")
class CommentController(private val commentService: CommentService, private val blogService: BlogInfoService) {


    /**
     * 返回评论列表
     *
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogComment>
     * @date 2020/4/24 21:23
    </com.site.blog.entity.BlogComment> */
    @GetMapping("/comment/paging")
    fun getCommentList(ajaxPutPage: AjaxPutPage<Comment>, condition: Comment): ResultDto<AjaxResultPage<CommentVo>> {
        val queryWrapper = QueryWrapper(condition)
        val page = ajaxPutPage.putPageToPage()
        commentService.page(page, queryWrapper)
        val commentVoPage = page.records.map { (BeanMapUtil::copyComment)(it) }
        commentVoPage.forEach(Consumer { item: CommentVo ->
            val blogInfo = blogService.getOne(KtQueryWrapper(BlogInfo()).eq(BlogInfo::blogId, item.blogId))
            item.blogInfo = blogInfo
        })

        val result = AjaxResultPage<CommentVo>()
        result.list = commentVoPage
        result.count = commentVoPage.size.toLong()
        return getResultByHttp(HttpStatusEnum.OK, true, result)
    }

    /**
     * 更新评论状态
     * 修改评论状态
     *
     * @param id id
     * @return com.site.blog.pojo.dto.ResultDto<java.lang.String>
     * @date 2020/4/24 21:21
    </java.lang.String> */
    @PostMapping("/comment/isDel/{id}")
    @SysLogAnnotation(title = "更新", opType = LogOperationEnum.CHANGE_STATUS)
    fun updateCommentStatus(@PathVariable("id") id: String?, @RequestParam("show") show: Boolean?): ResultDto<*> {
        val comment = commentService.getById(id)
        comment!!.commentStatus = show
        val flag = commentService.updateById(comment)
        return if (flag) {
            getResultByHttp<Comment?>(HttpStatusEnum.OK, comment)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 删除评论
     *
     * @param id
     * @return com.site.blog.pojo.dto.ResultDto<java.lang.String>
     * @date 2020/4/24 21:23
    </java.lang.String> */
    @DeleteMapping("/comment/delete/{id}")
    @SysLogAnnotation(title = "删除评论", opType = LogOperationEnum.DELETE)
    fun deleteComment(@PathVariable("id") id: String?): ResultDto<String> {
        val flag = commentService.removeById(id)
        return if (flag) {
           BaseResult.ok("删除成功")
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 编辑评论
     *
     * @param comment
     * @return com.site.blog.pojo.dto.ResultDto<java.lang.String>
     * @date 2020/4/24 21:21
    </java.lang.String> */
    @PostMapping("/comment/edit")
    @SysLogAnnotation(title = "编辑评论", opType = LogOperationEnum.EDIT)
    fun editComment(comment: Comment): ResultDto<String> {
        comment.replyCreateTime = LocalDateTime.now()
        comment.commentBody = StringEscapeUtils.escapeHtml4(comment.commentBody)
        val flag = commentService.updateById(comment)
        return if (flag) {
            BaseResult.ok("编辑成功")
        } else {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
    }
}