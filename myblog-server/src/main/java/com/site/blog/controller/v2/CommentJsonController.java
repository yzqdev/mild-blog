package com.site.blog.controller.v2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogComment;
import com.site.blog.service.BlogCommentService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
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
public class CommentJsonController {

    @Resource
    private BlogCommentService blogCommentService;



    /**
     * 返回评论列表
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogComment>
     * @date 2020/4/24 21:23
     */
    @GetMapping("/comment/paging")
    public AjaxResultPage<BlogComment> getCommentList(AjaxPutPage<BlogComment> ajaxPutPage, BlogComment condition){
        QueryWrapper<BlogComment> queryWrapper = new QueryWrapper<>(condition);
        Page<BlogComment> page = ajaxPutPage.putPageToPage();
        blogCommentService.page(page,queryWrapper);
        AjaxResultPage<BlogComment> result = new AjaxResultPage<>();
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
        BlogComment comment=blogCommentService.getById(id);
        comment.setCommentStatus(0);
        boolean flag = blogCommentService.updateById(comment);
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
        boolean flag = blogCommentService.removeById(id);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 编辑评论
     * @param blogComment
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @date 2020/4/24 21:21
     */
    @PostMapping("/comment/edit")
    public Result<String> editComment(BlogComment blogComment){
        blogComment.setReplyCreateTime(DateUtils.getLocalCurrentDate());
        blogComment.setCommentBody(StringEscapeUtils.escapeHtml4(blogComment.getCommentBody()));
        boolean flag = blogCommentService.updateById(blogComment);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
