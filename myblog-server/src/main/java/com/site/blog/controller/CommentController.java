package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Comment;
import com.site.blog.service.CommentService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yzqde
 * @qq交流群 796794009
 * @qq 1320291471
 * @Description: 评论标签
 * @date: 2019/8/6 17:24
 */
@RestController
@RequestMapping("/v2/admin")
@Api(tags = "评论json")
public class CommentController {

    @Resource
    private CommentService commentService;



    /**
     * 返回评论列表
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogComment>
     * @date 2020/4/24 21:23
     */
    @GetMapping("/comment/paging")
    public AjaxResultPage<Comment> getCommentList(AjaxPutPage<Comment> ajaxPutPage, Comment condition){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>(condition);
        Page<Comment> page = ajaxPutPage.putPageToPage();
        commentService.page(page,queryWrapper);
        AjaxResultPage<Comment> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改评论状态
     * @param blogComment
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:21
     */
    @PostMapping( "/comment/isDel/{id}" )
    public Result  updateCommentStatus(@PathVariable("id") String id){
        Comment comment= commentService.getById(id);
        comment.setCommentStatus(0);
        boolean flag = commentService.updateById(comment);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,comment);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 删除评论
     * @param id
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:23
     */
    @DeleteMapping("/comment/delete/{id}")
    public Result<String> deleteComment(@PathVariable("id") Long id){
        boolean flag = commentService.removeById(id);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 编辑评论
     * @param comment
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:21
     */
    @PostMapping("/comment/edit")
    public Result<String> editComment(Comment comment){
        comment.setReplyCreateTime(DateUtils.getLocalCurrentDate());
        comment.setCommentBody(StringEscapeUtils.escapeHtml4(comment.getCommentBody()));
        boolean flag = commentService.updateById(comment);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
