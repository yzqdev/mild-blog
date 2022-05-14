package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.BlogInfoDo;
import com.site.blog.model.dto.PageDto;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogCategory;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.model.entity.Tag;
import com.site.blog.model.vo.BlogDetailVO;
import com.site.blog.model.vo.BlogEditVO;
import com.site.blog.service.*;
import com.site.blog.util.BeanMapUtil;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @qq交流群 796794009
 * @qq 1320291471
 * @Description: 管理员controller
 * @date: 2019/8/24 9:43
 */
@RestController
@Slf4j
@RequestMapping("/v2/admin")
@io.swagger.v3.oas.annotations.tags.Tag(name = "博客json")
@RequiredArgsConstructor

public class AdminBlogController {

     
    private final BlogCategoryService blogCategoryService;
     
  private final   CategoryService categoryService;
     
     private final BlogInfoService blogInfoService;
     
     private final BlogTagService blogTagService;
     
     private final TagService tagService;
     
     private final BlogService blogService;

    /**
     * 跳转博客编辑界面
     *
     * @return java.lang.String
     * @date 2019/8/28 15:03
     */


    @GetMapping("/blog/get/{id}")
    public Result getBlogById(@PathVariable("id") String id) {

        BlogInfo blogInfo = blogInfoService.getById(id);
        BlogEditVO blogDetailVO = new BlogEditVO();
        blogDetailVO.setBlogId(blogInfo.getBlogId());
        blogDetailVO.setBlogContent(blogInfo.getBlogContent());
        blogDetailVO.setBlogTitle(blogInfo.getBlogTitle());
        blogDetailVO.setBlogViews(blogInfo.getBlogViews());
        blogDetailVO.setBlogStatus(blogInfo.getBlogStatus());
        blogDetailVO.setBlogPreface(blogInfo.getBlogPreface());
        blogDetailVO.setEnableComment(blogInfo.getEnableComment());
        blogDetailVO.setCreateTime(blogInfo.getCreateTime().toLocalDateTime());
        blogDetailVO.setBlogCategoryId(blogCategoryService.getOne(new QueryWrapper<BlogCategory>().eq("blog_id", blogInfo.getBlogId())).getCategoryId());
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<BlogTag>().eq("blog_id", id);
        List<String> ids = blogTagService.list(queryWrapper).stream().map(BlogTag::getTagId).collect(Collectors.toList());
        blogDetailVO.setBlogTagIds(ids);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogDetailVO);
    }


    /**
     * 保存文章内容
     *
     * @param blogInfoDo
     * @return com.zhulin.blog.dto.Result
     * @date 2019/8/28 15:04
     */

    @PostMapping("/blog/edit")
    @Transactional(rollbackFor = Exception.class)
    public Result saveBlog(@RequestBody BlogInfoDo blogInfoDo) {
//todo 当然这里可以直接用sql语句,不过我为了学习方便用了DO
        //if (ObjectUtils.isEmpty(blogInfoDo.getBlogTags()) || ObjectUtils.isEmpty(blogInfoDo)) {
        //    return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        //}
        BlogInfo blogInfo = new BlogInfo();

            blogInfo.setBlogId(blogInfoDo.getBlogId());

        blogInfo.setBlogViews(0L);
        blogInfo.setBlogTitle(blogInfoDo.getBlogTitle());
        blogInfo.setBlogSubUrl(blogInfoDo.getBlogSubUrl());
        blogInfo.setEnableComment(blogInfoDo.getEnableComment());
        blogInfo.setBlogStatus(blogInfoDo.getBlogStatus());
        blogInfo.setCreateTime(Optional.ofNullable(Timestamp.valueOf(blogInfoDo.getCreateTime())).orElse(new Timestamp(System.currentTimeMillis())));
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentDate());


        blogInfo.setBlogContent(blogInfoDo.getBlogContent());
        blogInfo.setBlogPreface(blogInfoDo.getBlogPreface());
        blogInfo.setIsDeleted(0);
        blogInfo.setBlogStatus(blogInfoDo.getBlogStatus());
        log.info("这是bloginfo");
log.info(blogInfo.toString());
        if (blogInfoService.saveOrUpdate(blogInfo)) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setBlogId(blogInfo.getBlogId());
            System.out.println(blogInfo);
            //添加blog和分类映射关系
            if (blogInfoDo.getBlogCategoryId() == null) {
                //blogCategoryService.save()
                blogCategory.setCategoryId("1");

            } else {
                LambdaQueryWrapper<BlogCategory> queryWrapper=new LambdaQueryWrapper<BlogCategory>().eq(BlogCategory::getBlogId, blogInfo.getBlogId());
                if (blogCategoryService.getOne(queryWrapper)==null){
                    blogCategory.setCategoryId("1");
                }else{
                    blogCategoryService.remove(queryWrapper);
                    blogCategory.setCategoryId(blogInfoDo.getBlogCategoryId());
                }

                //blogCategory.setCategoryId(blogInfoDo.getBlogCategoryId());
            }
            blogCategory.setCreateTime(DateUtils.getLocalCurrentDate());
            blogCategoryService.save(blogCategory);

            //添加blog和标签映射关系
            blogTagService.remove(new QueryWrapper<BlogTag>().eq("blog_id", blogInfo.getBlogId()));
            for (String tagId : blogInfoDo.getBlogTagIds()) {
                BlogTag blogTag = BlogTag.builder().blogId(blogInfo.getBlogId()).tagId(tagId).createTime(DateUtils.getLocalCurrentDate()).build();

                blogTagService.save(blogTag);
            }
            //blogService.removeAndsaveBatch(Arrays.asList(blogInfo.getBlogTags().split(",")), blogInfo);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogInfo);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 文章分页列表
     *
     * @param pageDto 分页参数
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogInfo>
     * @date 2019/8/28 16:43
     */

    @GetMapping("/blog/list")
    public Result getBlogList(PageDto pageDto) {
        try {
            QueryWrapper<BlogInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().orderByDesc(BlogInfo::getUpdateTime);
            Page<BlogInfo> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
            Page<BlogInfo> blogInfoPage = blogInfoService.page(page, queryWrapper);
log.info("blogInfoPage:====>{}",blogInfoPage.getRecords());
            List<BlogDetailVO> blogDetailVOS = blogInfoPage.getRecords().stream().map(BeanMapUtil::copyBlog).collect(Collectors.toList());
log.info("blogDetailVOS:{}",blogDetailVOS);
            blogDetailVOS.forEach(post -> {

                QueryWrapper<BlogTag> tagQueryWrapper = new QueryWrapper<BlogTag>().eq("blog_id", post.getBlogId());
                List<Tag> tags = blogTagService.list(tagQueryWrapper).stream().map(item -> tagService.getById(item.getTagId())).collect(Collectors.toList());
                post.setBlogTags(tags);
                System.out.println(post);
                String cateId = blogCategoryService.getOne(new QueryWrapper<BlogCategory>().eq("blog_id", post.getBlogId())).getCategoryId();
                if (cateId != null) {
                    post.setBlogCategory(categoryService.getById(cateId));
                }
            });
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, blogDetailVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, "error");
        }

    }

    /**
     * 修改博客的部分状态相关信息
     *
     * @param blogInfo
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 12:22
     */

    @PostMapping("/blog/blogStatus")
    public Result<String> updateBlogStatus(BlogInfo blogInfo) {
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 修改文章的删除状态为已删除
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 14:02
     */

    @PostMapping("/blog/delete/{id}")
    public Result deleteBlog(@PathVariable("id") String blogId) {
        BlogInfo blogInfo = blogInfoService.getOne(new QueryWrapper<BlogInfo>().eq("blog_id", blogId));
        blogInfo.setIsDeleted(DeleteStatusEnum.DELETED.getStatus()).setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogInfo);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 清除文章
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 14:02
     */

    @PostMapping("/blog/clear/{id}")
    public Result clearBlog(@PathVariable("id") String blogId) {
        if (blogInfoService.clearBlogInfo(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogId);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 还原文章
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 16:36
     */

    @PostMapping("/blog/restore")
    public Result<String> restoreBlog(@RequestParam String blogId) {
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setIsDeleted(DeleteStatusEnum.NO_DELETED.getStatus())
                .setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


}
