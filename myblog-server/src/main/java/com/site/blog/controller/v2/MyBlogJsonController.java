package com.site.blog.controller.v2;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.*;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.BlogPageCondition;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.*;
import com.site.blog.model.vo.BlogDetailVO;
import com.site.blog.service.*;
import com.site.blog.util.PageResult;
import com.site.blog.util.ResultGenerator;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 博客前台
 *
 * @author Linn-cn
 * @date 2020/12/7
 */
@RestController
@RequestMapping("/v2/home")
public class MyBlogJsonController {

    public static String theme = "amaze";

    @Resource
    private BlogInfoService blogInfoService;

    @Resource
    private BlogTagService blogTagService;

    @Resource
    private BlogConfigService blogConfigService;

    @Resource
    private BlogTagRelationService blogTagRelationService;

    @Resource
    private BlogCommentService blogCommentService;

    @Resource
    private BlogLinkService blogLinkService;

    /**
     * 博客首页
     *
     * @author Linn-cn
     * @date 2020/12/7
     */
    @GetMapping({"/", "/index"})
    public Result index() {
        return this.page(new BlogPageCondition()
                .setPageNum(1)
                .setPageName("首页")
        );
    }

    /**
     * 分类
     *
     * @param categoryName
     * @author Linn-cn
     * @date 2020/12/7
     */
    @GetMapping({"/category/{categoryName}"})
    public Result category(@PathVariable("categoryName") String categoryName) {
        return this.page(  new BlogPageCondition()
                .setPageNum(1)
                .setPageName("分类")
                .setCategoryName(categoryName));
    }

    /**
     * 搜索
     *
     * @param keyword
     * @return java.lang.String
     * @date 2019/9/6 7:03
     */
    @GetMapping({"/search/{keyword}"})
    public Result search(@PathVariable("keyword") String keyword) {
        return this.page( new BlogPageCondition()
                .setPageNum(1)
                .setPageName("首页")
                .setKeyword(keyword)
        );
    }

    /**
     * 标签
     *
     * @param tagId
     * @return java.lang.String
     * @date 2019/9/6 7:04
     */
    @GetMapping({"/tag/{tagId}"})
    public Result tag(@PathVariable("tagId") String tagId) {
        return this.page(  new BlogPageCondition()
                .setPageNum(1)
                .setPageName("标签")
                .setTagId(tagId));
    }

    /**
     * 博客分页
     *
     * @param condition
     * @throws
     * @author Linn-cn
     * @date 2020/12/7
     */
    @GetMapping({"/page"})
    public Result page(BlogPageCondition condition) {
        if (Objects.isNull(condition.getPageNum())) {
            condition.setPageNum(1);
        }
        if (Objects.isNull(condition.getPageSize())) {
            condition.setPageSize(5);
        }
        HashMap<String,Object> result=new HashMap<>();
        Page<BlogInfo> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        LambdaQueryWrapper<BlogInfo> sqlWrapper = Wrappers.<BlogInfo>lambdaQuery()
                .like(Objects.nonNull(condition.getKeyword()), BlogInfo::getBlogTitle, condition.getKeyword())
                .eq(Objects.nonNull(condition.getCategoryName()), BlogInfo::getBlogCategoryName, condition.getCategoryName())
                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        if (Objects.nonNull(condition.getTagId())) {
            List<BlogTagRelation> list = blogTagRelationService.list(new QueryWrapper<BlogTagRelation>()
                    .lambda().eq(BlogTagRelation::getTagId, condition.getTagId()));
            if (!CollectionUtils.isEmpty(list)) {
                sqlWrapper.in(BlogInfo::getBlogId, list.stream().map(BlogTagRelation::getBlogId).toArray());
            }
        }
        sqlWrapper.orderByDesc(BlogInfo::getCreateTime);
        blogInfoService.page(page, sqlWrapper);
        PageResult blogPageResult = new PageResult(page.getRecords(), page.getTotal(), condition.getPageSize(), condition.getPageNum());
        if (Objects.nonNull(condition.getKeyword())) {
            result.put("keyword", condition.getKeyword());
        }
        if (Objects.nonNull(condition.getTagId())) {
            result.put("tagId", condition.getTagId());
        }
        if (Objects.nonNull(condition.getCategoryName())) {
            result.put("categoryName", condition.getCategoryName());
        }
        result.put("blogPageResult", blogPageResult);
        result.put("pageName", condition.getPageName());
        result.put("newBlogs", blogInfoService.getNewBlog());
        result.put("hotBlogs", blogInfoService.getHotBlog());
        result.put("hotTags", blogTagService.getBlogTagCountForIndex());
        result.put("configurations", blogConfigService.getAllConfigs());
        return  ResultGenerator.getResultByHttp(HttpStatusEnum.OK,result);
    }

    /**
     * 文章详情
     *
     * @param blogId
     * @return java.lang.String
     * @date 2019/9/6 13:09
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(@PathVariable("blogId") Long blogId) {
        // 获得文章info
        BlogInfo blogInfo = blogInfoService.getById(blogId);
        List<BlogTagRelation> blogTagRelations = blogTagRelationService.list(new QueryWrapper<BlogTagRelation>()
                .lambda()
                .eq(BlogTagRelation::getBlogId, blogId));
        blogInfoService.updateById(new BlogInfo()
                .setBlogId(blogInfo.getBlogId())
                .setBlogViews(blogInfo.getBlogViews() + 1));

        // 获得关联的标签列表
        List<Integer> tagIds;
        List<BlogTag> tagList = new ArrayList<>();
        if (!blogTagRelations.isEmpty()) {
            tagIds = blogTagRelations.stream()
                    .map(BlogTagRelation::getTagId).collect(Collectors.toList());
            tagList = blogTagService.list(new QueryWrapper<BlogTag>().lambda().in(BlogTag::getTagId, tagIds));
        }

        // 关联评论的Count
        Integer blogCommentCount = blogCommentService.count(new QueryWrapper<BlogComment>()
                .lambda()
                .eq(BlogComment::getCommentStatus, CommentStatusEnum.ALLOW.getStatus())
                .eq(BlogComment::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .eq(BlogComment::getBlogId, blogId));
HashMap<String,Object> result=new HashMap<>();
        BlogDetailVO blogDetailVO = new BlogDetailVO();
        BeanUtils.copyProperties(blogInfo, blogDetailVO);
        blogDetailVO.setCommentCount(blogCommentCount);
        result.put("blogDetailVO", blogDetailVO);
        result.put("tagList", tagList);
        result.put("pageName", "详情");
        result.put("configurations", blogConfigService.getAllConfigs());
        return "blog/" + theme + "/detail";
    }

    /**
     * 评论列表
     *
     * @param ajaxPutPage
     * @param blogId
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogComment>
     * @date 2019/11/19 8:42
     */
    @GetMapping("/blog/listComment")
    @ResponseBody
    public AjaxResultPage<BlogComment> listComment(AjaxPutPage<BlogComment> ajaxPutPage, Integer blogId) {
        Page<BlogComment> page = ajaxPutPage.putPageToPage();
        blogCommentService.page(page, new QueryWrapper<BlogComment>()
                .lambda()
                .eq(BlogComment::getBlogId, blogId)
                .eq(BlogComment::getCommentStatus, CommentStatusEnum.ALLOW.getStatus())
                .eq(BlogComment::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .orderByDesc(BlogComment::getCommentCreateTime));
        AjaxResultPage<BlogComment> ajaxResultPage = new AjaxResultPage<>();
        ajaxResultPage.setCount(page.getTotal());
        ajaxResultPage.setData(page.getRecords());
        return ajaxResultPage;
    }

    /**
     * 友链界面
     *
     * @param request
     * @return java.lang.String
     * @date 2019/9/6 17:26
     */
    @GetMapping({"/link"})
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "友情链接");
        List<BlogLink> favoriteLinks = blogLinkService.list(new QueryWrapper<BlogLink>()
                .lambda().eq(BlogLink::getLinkType, LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeId())
        );
        List<BlogLink> recommendLinks = blogLinkService.list(new QueryWrapper<BlogLink>()
                .lambda().eq(BlogLink::getLinkType, LinkConstants.LINK_TYPE_RECOMMEND.getLinkTypeId())
        );
        List<BlogLink> personalLinks = blogLinkService.list(new QueryWrapper<BlogLink>()
                .lambda().eq(BlogLink::getLinkType, LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeId())
        );
        //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
        request.setAttribute("favoriteLinks", favoriteLinks);
        request.setAttribute("recommendLinks", recommendLinks);
        request.setAttribute("personalLinks", personalLinks);
        request.setAttribute("configurations", blogConfigService.getAllConfigs());
        return "blog/" + theme + "/link";
    }

    /**
     * 提交评论
     *
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/6 17:40
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public Result<String> comment(HttpServletRequest request,
                                  @Validated BlogComment blogComment) {
        String ref = request.getHeader("Referer");
        // 对非法字符进行转义，防止xss漏洞
        blogComment.setCommentBody(StringEscapeUtils.escapeHtml4(blogComment.getCommentBody()));
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, "非法请求");
        }
        boolean flag = blogCommentService.save(blogComment);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

}
