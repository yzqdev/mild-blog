package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.UploadConstants;
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
import com.site.blog.util.UploadFileUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @qq交流群 796794009
 * @qq 1320291471
 * @Description: 管理员controller
 * @date: 2019/8/24 9:43
 */
@RestController
@RequestMapping("/v2/admin")
@Api(tags = "博客json")
public class AdminBlogController {

    @Resource
    BlogCategoryService blogCategoryService;
    @Resource
    CategoryService categoryService;
    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogTagService blogTagService;
    @Resource
    private TagService tagService;
    @Resource
    private BlogService blogService;

    /**
     * 跳转博客编辑界面
     *
     * @return java.lang.String
     * @date 2019/8/28 15:03
     */


    @GetMapping("/blog/get/{id}")
    public Result getBlogById(@PathVariable("id") Integer id) {

        BlogInfo blogInfo = blogInfoService.getById(id);
        BlogEditVO blogDetailVO = new BlogEditVO();
        blogDetailVO.setBlogContent(blogInfo.getBlogContent());
        blogDetailVO.setBlogTitle(blogInfo.getBlogTitle());
        blogDetailVO.setBlogViews(blogInfo.getBlogViews());
        blogDetailVO.setBlogCategoryId(blogCategoryService.getOne(new QueryWrapper<BlogCategory>().eq("blog_id", blogInfo.getBlogId())).getCategoryId());
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<BlogTag>().eq("blog_id", id);
        List<Integer> ids = blogTagService.list(queryWrapper).stream().map(BlogTag::getTagId).collect(Collectors.toList());
        blogDetailVO.setBlogTagIds(ids);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogDetailVO);
    }

    /**
     * 保存文章图片
     *
     * @param request
     * @param file
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @date 2019/8/26 13:57
     */

    @PostMapping("/blog/uploadFile")
    public Map<String, Object> uploadFileByEditormd(HttpServletRequest request,
                                                    @RequestParam(name = "editormd-image-file") MultipartFile file) {
        String suffixName = UploadFileUtils.getSuffixName(file);
        //生成文件名称通用方法
        String newFileName = UploadFileUtils.getNewFileName(suffixName);
        File fileDirectory = new File(UploadConstants.FILE_UPLOAD_DIC);
        //创建文件
        File destFile = new File(UploadConstants.FILE_UPLOAD_DIC + newFileName);
        Map<String, Object> result = new HashMap<>();
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdirs()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            String fileUrl = UploadConstants.FILE_SQL_DIC + newFileName;
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", fileUrl);
        } catch (IOException e) {
            result.put("success", 0);
        }
        return result;
    }

    /**
     * 保存文章内容
     *
     * @param blogInfoDo
     * @return com.zhulin.blog.dto.Result
     * @date 2019/8/28 15:04
     */

    @PostMapping("/blog/edit")
    public Result saveBlog(@RequestBody BlogInfoDo blogInfoDo) {

        //if (ObjectUtils.isEmpty(blogInfoDo.getBlogTags()) || ObjectUtils.isEmpty(blogInfoDo)) {
        //    return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        //}
        BlogInfo blogInfo = new BlogInfo();
        if (null != blogInfoDo.getBlogId()) {
            blogInfo.setBlogId(blogInfoDo.getBlogId());
        }
        blogInfo.setBlogViews(blogInfoDo.getBlogViews());
        blogInfo.setBlogTitle(blogInfoDo.getBlogTitle());
        blogInfo.setBlogSubUrl(blogInfoDo.getBlogSubUrl());
        blogInfo.setEnableComment(blogInfoDo.getEnableComment());
        blogInfo.setBlogStatus(blogInfoDo.getBlogStatus());
        blogInfo.setCreateTime(DateUtils.getLocalCurrentDate());
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentDate());


        blogInfo.setBlogContent(blogInfoDo.getBlogContent());
        blogInfo.setBlogPreface(blogInfoDo.getBlogPreface());
        blogInfo.setIsDeleted(0);
        blogInfo.setBlogStatus(blogInfoDo.getBlogStatus());

        if (blogInfoService.saveOrUpdate(blogInfo)) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setBlogId(blogInfo.getBlogId());
            System.out.println(blogInfo);
            //添加blog和分类映射关系
            if (blogInfoDo.getBlogCategoryId() == null) {
                //blogCategoryService.save()
                blogCategory.setCategoryId(1);

            } else {
                blogCategoryService.remove(new QueryWrapper<BlogCategory>().eq("blog_id", blogInfo.getBlogId()));
                blogCategory.setCategoryId(blogInfoDo.getBlogCategoryId());
            }
            blogCategory.setCreateTime(DateUtils.getLocalCurrentDate());
            blogCategoryService.save(blogCategory);

            //添加blog和标签映射关系
            blogTagService.remove(new QueryWrapper<BlogTag>().eq("blog_id", blogInfo.getBlogId()));
            for (Integer tagId : blogInfoDo.getBlogTagIds()) {
                BlogTag blogTag = new BlogTag();
                blogTag.setBlogId(blogInfo.getBlogId());
                blogTag.setTagId(tagId);
                blogTag.setCreateTime(DateUtils.getLocalCurrentDate());
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
    public Result  getBlogList(PageDto pageDto) {
        try {
            QueryWrapper<BlogInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().orderByDesc(BlogInfo::getUpdateTime);
            Page<BlogInfo> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
            Page<BlogInfo> blogInfoPage = blogInfoService.page(page, queryWrapper);

            List<BlogDetailVO> blogDetailVOS = blogInfoPage.getRecords().stream().map(BeanMapUtil::copyBlog).collect(Collectors.toList());

            blogDetailVOS.forEach(post -> {

                QueryWrapper<BlogTag> tagQueryWrapper = new QueryWrapper<BlogTag>().eq("blog_id", post.getBlogId());
                List<Tag> tags = blogTagService.list(tagQueryWrapper).stream().map(item -> tagService.getById(item.getTagId())).collect(Collectors.toList());
                post.setBlogTags(tags);
                Integer cateId = blogCategoryService.getOne(new QueryWrapper<BlogCategory>().eq("blog_id", post.getBlogId())).getCategoryId();
                if (cateId != null) {
                    post.setBlogCategory(categoryService.getById(cateId));
                }
            });
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, blogDetailVOS);
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
    public Result deleteBlog(@PathVariable("id") Long blogId) {
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
    public Result clearBlog(@PathVariable("id") Long blogId) {
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
    public Result<String> restoreBlog(@RequestParam Long blogId) {
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
