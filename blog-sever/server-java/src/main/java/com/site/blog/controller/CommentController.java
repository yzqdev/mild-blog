package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.Comment;
import com.site.blog.model.vo.CommentVo;
import com.site.blog.service.BlogInfoService;
import com.site.blog.service.CommentService;
import com.site.blog.util.BeanMapUtil;
import com.site.blog.util.ResultGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/v2/admin")
@Tag(name = "评论json")
@RequiredArgsConstructor
public class CommentController {


    private  final    CommentService commentService;

    private  final    BlogInfoService blogService;


    /**
     * 返回评论列表
     *
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogComment>
     * @date 2020/4/24 21:23
     */
    @GetMapping("/comment/paging")
    public Result<AjaxResultPage<CommentVo>> getCommentList(AjaxPutPage<Comment> ajaxPutPage, Comment condition) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>(condition);
        Page<Comment> page = ajaxPutPage.putPageToPage();
        commentService.page(page, queryWrapper);
        List<CommentVo> commentVoPage = page.getRecords().stream().map(BeanMapUtil::copyComment).toList();
        commentVoPage.forEach(item -> {
            BlogInfo blogInfo = blogService.getOne(new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getBlogId, item.getBlogId()));
            item.setBlogInfo(blogInfo);
        });


        AjaxResultPage<CommentVo> result = new AjaxResultPage<>();
        result.setList(commentVoPage);
        result.setCount(commentVoPage.size());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, result);
    }

    /**
     * 更新评论状态
     * 修改评论状态
     *
     * @param id id
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:21
     */
    @PostMapping("/comment/isDel/{id}")
    @SysLogAnnotation(title = "更新", opType = LogOperationEnum.CHANGE_STATUS)
    public Result updateCommentStatus(@PathVariable("id") String id, @RequestParam("show") Boolean show) {
        Comment comment = commentService.getById(id);
        comment.setCommentStatus(show);
        boolean flag = commentService.updateById(comment);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, comment);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除评论
     *
     * @param id
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:23
     */
    @DeleteMapping("/comment/delete/{id}")
    @SysLogAnnotation(title = "删除评论", opType = LogOperationEnum.DELETE)
    public Result<String> deleteComment(@PathVariable("id") String id) {
        boolean flag = commentService.removeById(id);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 编辑评论
     *
     * @param comment
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:21
     */
    @PostMapping("/comment/edit")
    @SysLogAnnotation(title = "编辑评论", opType = LogOperationEnum.EDIT)
    public Result<String> editComment(Comment comment) {
        comment.setReplyCreateTime(LocalDateTime.now());
        comment.setCommentBody(StringEscapeUtils.escapeHtml4(comment.getCommentBody()));
        boolean flag = commentService.updateById(comment);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
