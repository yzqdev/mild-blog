package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.ShowEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.*;
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
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;



@RestController
@Slf4j
@RequestMapping("/v2/admin")
@io.swagger.v3.oas.annotations.tags.Tag(name = "博客json")
@RequiredArgsConstructor

public class AdminBlogController {


    private final BlogCategoryService blogCategoryService;

    private final CategoryService categoryService;

    private final BlogInfoService blogInfoService;

    private final BlogTagService blogTagService;

    private final TagService tagService;

    private final BlogTagService blogService;

    /**
     * 跳转博客编辑界面
     *
     * @return java.lang.String
     * @date 2019/8/28 15:03
     */


    @GetMapping("/blog/get/{id}")
    public Result getBlogById(@PathVariable("id") String id) {

        BlogInfo blogInfo = blogInfoService.getOne(new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getBlogId, id));
        BlogEditVO blogDetailVO = new BlogEditVO();
        BeanUtils.copyProperties(blogInfo, blogDetailVO);
        blogDetailVO.setBlogCategoryId(blogCategoryService.getOne(new QueryWrapper<BlogCategory>().eq("blog_id", blogInfo.getBlogId())).getCategoryId());
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<BlogTag>().eq("blog_id", id);
        List<String> ids = blogTagService.list(queryWrapper).stream().map(BlogTag::getTagId).toList();
        blogDetailVO.setBlogTagIds(ids);
        log.info("home创建时间{}", blogDetailVO.getCreateTime().toString());
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
    @SysLogAnnotation(title = "修改博客", opType = LogOperationEnum.EDIT)
    @Transactional(rollbackFor = Exception.class)
    public Result saveBlog(@RequestBody BlogInfoDo blogInfoDo) {

        BlogInfo blogInfo = new BlogInfo();

        blogInfo.setBlogId(blogInfoDo.getBlogId());

        blogInfo.setBlogViews(0L);
        blogInfo.setBlogTitle(blogInfoDo.getBlogTitle());
        blogInfo.setSubUrl(blogInfoDo.getSubUrl());
        blogInfo.setEnableComment(blogInfoDo.getEnableComment());
        blogInfo.setShow(blogInfoDo.getShow());
        blogInfo.setCreateTime(LocalDateTime.now());
        blogInfo.setUpdateTime(LocalDateTime.now());


        blogInfo.setBlogContent(blogInfoDo.getBlogContent());
        blogInfo.setPreface(blogInfoDo.getPreface());
        log.info("这是bloginfo");
        log.info(blogInfo.toString());
        blogInfo.setDeleted(false);
        if (blogInfoService.saveOrUpdate(blogInfo)) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setBlogId(blogInfo.getBlogId());

            //添加blog和分类映射关系
            if (blogInfoDo.getBlogCategoryId() == null) {
                //blogCategoryService.save()
                blogCategory.setCategoryId("1");

            } else {
                LambdaQueryWrapper<BlogCategory> queryWrapper = new LambdaQueryWrapper<BlogCategory>().eq(BlogCategory::getBlogId, blogInfo.getBlogId());
                if (blogCategoryService.getOne(queryWrapper) == null) {
                    blogCategory.setCategoryId("1");
                } else {
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
     * @param ajaxPutPage
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogInfo>
     * @date 2019/8/28 16:43
     */

    @GetMapping("/blog/list")
    public Result getBlogList(AjaxPutPage<BlogInfo> ajaxPutPage) {
        try {
            QueryWrapper<BlogInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().orderByDesc(BlogInfo::getUpdateTime);
            if (Boolean.TRUE.equals(ajaxPutPage.getDeleted())) {
                queryWrapper.lambda().eq(BlogInfo::getDeleted, true);
            } else {
                queryWrapper.lambda().eq(BlogInfo::getDeleted, false);
            }
            Page<BlogInfo> page = ajaxPutPage.putPageToPage();
            Page<BlogInfo> blogInfoPage = blogInfoService.page(page, queryWrapper);
            log.info("blogInfoPage:====>{}", blogInfoPage.getRecords());
            List<BlogDetailVO> blogDetailVOS = blogInfoPage.getRecords().stream().map(BeanMapUtil::copyBlog).toList();
            log.info("blogDetailVOS:{}", blogDetailVOS);
            blogDetailVOS.forEach(post -> {


                LambdaQueryWrapper<BlogTag> tagQueryWrapper = new LambdaQueryWrapper<BlogTag>().eq(BlogTag::getBlogId, post.getBlogId());


                    var tags = blogTagService.list(tagQueryWrapper).stream().map(item ->
                            tagService.getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagId, item.getTagId()).eq(Tag::getShow, true))
                    ).filter(Objects::nonNull).toList();
                    post.setBlogTags(tags);



                String cateId = blogCategoryService.getOne(new LambdaQueryWrapper<BlogCategory>().eq(BlogCategory::getBlogId, post.getBlogId())).getCategoryId();
                if (cateId != null) {
                    post.setBlogCategory(categoryService.getById(cateId));
                }

            });
            AjaxResultPage<BlogDetailVO> list = new AjaxResultPage<>();
            list.setCount(blogInfoPage.getTotal());
            list.setList(blogDetailVOS);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false);
        }

    }


    /**
     * 更新博客状态
     *
     * @param id   id
     * @param show 显示
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 12:22
     */

    @PostMapping("/blog/show/{id}")
    @SysLogAnnotation(title = "更新博客状态", opType = LogOperationEnum.EDIT)
    public Result<String> hideBlog(@PathVariable("id") String id, @RequestParam("show") Boolean show) {
        BlogInfo sqlBlog = blogInfoService.getOne(new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getBlogId, id));

        sqlBlog.setShow(show);

        boolean flag = blogInfoService.updateById(sqlBlog);
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
    public Result deleteBlog(@PathVariable("id") String blogId, @RequestParam("restore") Boolean restore) {
        BlogInfo blogInfo = blogInfoService.getOne(new QueryWrapper<BlogInfo>().eq("blog_id", blogId));
        blogInfo.setShow(ShowEnum.NOT_SHOW.getStatus()).setUpdateTime(LocalDateTime.now());
        if (Boolean.TRUE.equals(restore)) {
            blogInfo.setDeleted(false);
        } else {
            blogInfo.setDeleted(true);
        }
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
    @SysLogAnnotation(title = "恢复博客",opType = LogOperationEnum.CHANGE_STATUS)
    public Result<String> restoreBlog(@RequestParam String blogId) {
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setShow(ShowEnum.SHOW.getStatus())
                .setUpdateTime(LocalDateTime.now());
        boolean flag = blogInfoService.updateById(blogInfo);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


}
