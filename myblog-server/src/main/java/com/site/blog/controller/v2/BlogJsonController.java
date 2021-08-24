package com.site.blog.controller.v2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.UploadConstants;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.BlogInfoDo;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.BlogTagRelation;
import com.site.blog.service.BlogInfoService;
import com.site.blog.service.BlogTagRelationService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import com.site.blog.util.UploadFileUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
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
public class BlogJsonController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogTagRelationService blogTagRelationService;

    /**
     * 跳转博客编辑界面
     *
     * @return java.lang.String
     * @date 2019/8/28 15:03
     */
    @GetMapping("/blog/edit")
    public String gotoBlogEdit(@RequestParam(required = false) Long blogId, Model model) {
        if (blogId != null) {
            BlogInfo blogInfo = blogInfoService.getById(blogId);
            List<BlogTagRelation> list = blogTagRelationService.list(
                    new QueryWrapper<BlogTagRelation>()
                            .lambda()
                            .eq(BlogTagRelation::getBlogId, blogId)
            );
            List<Integer> tags = null;
            if (!CollectionUtils.isEmpty(list)) {
                tags = list.stream().map(
                                BlogTagRelation::getTagId)
                        .collect(Collectors.toList());
            }
            model.addAttribute("blogTags", tags);
            model.addAttribute("blogInfo", blogInfo);
        }
        return "adminLayui/blog-edit";
    }

    @GetMapping("/blog/get/{id}")
    public Result getBlogById(@PathVariable("id") String id) {

        BlogInfo blogInfo = blogInfoService.getById(id);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogInfo);
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
                                                    @RequestParam(name = "editormd-image-file") MultipartFile file) throws URISyntaxException {
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
    public Result  saveBlog(@RequestBody BlogInfoDo blogInfoDo) {

        //if (ObjectUtils.isEmpty(blogInfoDo.getBlogTags()) || ObjectUtils.isEmpty(blogInfoDo)) {
        //    return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        //}
        BlogInfo blogInfo = new BlogInfo();
        if (null!=blogInfoDo.getBlogId()){
            blogInfo.setBlogId(blogInfoDo.getBlogId());
        }
        blogInfo.setBlogViews(blogInfoDo.getBlogViews());
        blogInfo.setBlogTitle(blogInfoDo.getBlogTitle());
        blogInfo.setBlogTags(blogInfoDo.getBlogTags());
        blogInfo.setBlogSubUrl(blogInfoDo.getBlogSubUrl());
        blogInfo.setEnableComment(blogInfoDo.getEnableComment());
        blogInfo.setBlogStatus(blogInfoDo.getBlogStatus());
        blogInfo.setCreateTime(DateUtils.getLocalCurrentDate());
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentDate());
        blogInfo.setBlogCategoryId(blogInfoDo.getBlogCategoryId());
        blogInfo.setBlogContent(blogInfoDo.getBlogContent());
        blogInfo.setBlogPreface(blogInfoDo.getBlogPreface());
        blogInfo.setIsDeleted(0);
        blogInfo.setBlogStatus(1);

        if (blogInfoService.saveOrUpdate(blogInfo)) {
            blogTagRelationService.removeAndsaveBatch(Arrays.asList(blogInfo.getBlogTags().split(",")), blogInfo);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 文章分页列表
     *
     * @param ajaxPutPage 分页参数
     * @param condition   筛选条件
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogInfo>
     * @date 2019/8/28 16:43
     */

    @GetMapping("/blog/list")
    public AjaxResultPage<BlogInfo> getContractList(AjaxPutPage<BlogInfo> ajaxPutPage, BlogInfo condition) {
        QueryWrapper<BlogInfo> queryWrapper = new QueryWrapper<>(condition);
        queryWrapper.lambda().orderByDesc(BlogInfo::getUpdateTime);
        Page<BlogInfo> page = ajaxPutPage.putPageToPage();
        blogInfoService.page(page, queryWrapper);
        AjaxResultPage<BlogInfo> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
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
        BlogInfo blogInfo = new BlogInfo()
                .setBlogId(blogId)
                .setIsDeleted(DeleteStatusEnum.DELETED.getStatus())
                .setUpdateTime(DateUtils.getLocalCurrentDate());
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


    @GetMapping("v1/blog/select")
    public List<BlogInfo> getBlogInfoSelect() {
        return blogInfoService.list();
    }

}
