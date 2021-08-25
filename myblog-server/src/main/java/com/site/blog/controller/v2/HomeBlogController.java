package com.site.blog.controller.v2;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.*;
import com.site.blog.model.dto.*;
import com.site.blog.model.entity.*;
import com.site.blog.model.vo.BlogDetailVO;
import com.site.blog.service.*;
import com.site.blog.util.PageResult;
import com.site.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@Api(tags = "首页数据")
public class HomeBlogController {

    public static String theme = "amaze";

    @Resource
    private BlogInfoService blogInfoService;

    @Resource
    private TagService tagService;

    @Resource
    private BlogConfigService blogConfigService;

    @Resource
    private BlogService blogService;

    @Resource
    private CommentService commentService;

    @Resource
    private LinkService linkService;

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
        return this.page(new BlogPageCondition()
                .setPageNum(1)
                .setPageName("分类")
                .setCategoryId(categoryName));
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
        return this.page(new BlogPageCondition()
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
    @PostMapping({"/tag/{tagId}"})
    public Result tag(@PathVariable("tagId") String tagId, @RequestBody PageDto pageDto) {

        Page<BlogInfo> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        LambdaQueryWrapper<BlogInfo> sqlWrapper = Wrappers.<BlogInfo>lambdaQuery()

                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        //获取tag下的文章
        if (Objects.nonNull(tagId)) {
            List<BlogTag> list = blogService.list(new QueryWrapper<BlogTag>()
                    .lambda().eq(BlogTag::getTagId, tagId));
            if (!CollectionUtils.isEmpty(list)) {
                sqlWrapper.in(BlogInfo::getBlogId, list.stream().map(BlogTag::getBlogId).toArray());
            }
        }
        sqlWrapper.orderByDesc(BlogInfo::getCreateTime);
        blogInfoService.page(page, sqlWrapper);
        PageResult blogPageResult = new PageResult(page.getRecords(), page.getTotal(), pageDto.getPageSize(), pageDto.getPageNum());



        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogPageResult);
    }

    @GetMapping("/configs")
    public Result getConfigs() {
        try {

            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogConfigService.getAllConfigs());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, "00");
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
        HashMap<String, Object> result = new HashMap<>();
        Page<BlogInfo> page = new Page<>(condition.getPageNum(), condition.getPageSize());
        LambdaQueryWrapper<BlogInfo> sqlWrapper = Wrappers.<BlogInfo>lambdaQuery()
                .like(Objects.nonNull(condition.getKeyword()), BlogInfo::getBlogTitle, condition.getKeyword())
                .eq(Objects.nonNull(condition.getCategoryId()), BlogInfo::getBlogCategoryId, condition.getCategoryId())
                .eq(BlogInfo::getBlogStatus, BlogStatusEnum.RELEASE.getStatus())
                .eq(BlogInfo::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        //获取tag下的文章
        if (Objects.nonNull(condition.getTagId())) {
            List<BlogTag> list = blogService.list(new QueryWrapper<BlogTag>()
                    .lambda().eq(BlogTag::getTagId, condition.getTagId()));
            if (!CollectionUtils.isEmpty(list)) {
                sqlWrapper.in(BlogInfo::getBlogId, list.stream().map(BlogTag::getBlogId).toArray());
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
        if (Objects.nonNull(condition.getCategoryId())) {
            result.put("categoryName", condition.getCategoryId());
        }
        result.put("blogPageResult", blogPageResult);
        result.put("pageName", condition.getPageName());
        result.put("newBlogs", blogInfoService.getNewBlog());
        result.put("hotBlogs", blogInfoService.getHotBlog());
        result.put("hotTags", tagService.getBlogTagCountForIndex());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    /**
     * 文章详情
     *
     * @param blogId
     * @return java.lang.String
     * @date 2019/9/6 13:09
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public Result  detail(@PathVariable("blogId") Long blogId) {
        // 获得文章info
        BlogInfo blogInfo = blogInfoService.getById(blogId);
        List<BlogTag> blogTags = blogService.list(new QueryWrapper<BlogTag>()
                .lambda()
                .eq(BlogTag::getBlogId, blogId));
        blogInfoService.updateById(new BlogInfo()
                .setBlogId(blogInfo.getBlogId())
                .setBlogViews(blogInfo.getBlogViews() + 1));

        // 获得关联的标签列表
        List<Integer> tagIds;
        List<Tag> tagList = new ArrayList<>();
        if (!blogTags.isEmpty()) {
            tagIds = blogTags.stream()
                    .map(BlogTag::getTagId).collect(Collectors.toList());
            tagList = tagService.list(new QueryWrapper<Tag>().lambda().in(Tag::getTagId, tagIds));
        }

        // 关联评论的Count
        long blogCommentCount = commentService.count(new QueryWrapper<Comment>()
                .lambda()
                .eq(Comment::getCommentStatus, CommentStatusEnum.ALLOW.getStatus())
                .eq(Comment::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .eq(Comment::getBlogId, blogId));
        HashMap<String, Object> result = new HashMap<>();
        BlogDetailVO blogDetailVO = new BlogDetailVO();
        BeanUtils.copyProperties(blogInfo, blogDetailVO);
        blogDetailVO.setCommentCount(blogCommentCount);
        result.put("blogDetailVO", blogDetailVO);
        result.put("tagList", tagList);
        result.put("pageName", "详情");
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,result);
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
    public AjaxResultPage<Comment> listComment(AjaxPutPage<Comment> ajaxPutPage, Integer blogId) {
        Page<Comment> page = ajaxPutPage.putPageToPage();
        commentService.page(page, new QueryWrapper<Comment>()
                .lambda()
                .eq(Comment::getBlogId, blogId)
                .eq(Comment::getCommentStatus, CommentStatusEnum.ALLOW.getStatus())
                .eq(Comment::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus())
                .orderByDesc(Comment::getCommentCreateTime));
        AjaxResultPage<Comment> ajaxResultPage = new AjaxResultPage<>();
        ajaxResultPage.setCount(page.getTotal());
        ajaxResultPage.setData(page.getRecords());
        return ajaxResultPage;
    }

    /**
     * 友链界面
     *
     * @return java.lang.String
     * @date 2019/9/6 17:26
     */
    @GetMapping({"/link"})
    public Result link() {
        List<Link> favoriteLinks = linkService.list(new QueryWrapper<Link>()
                .lambda().eq(Link::getLinkType, LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeId())
        );
        List<Link> recommendLinks = linkService.list(new QueryWrapper<Link>()
                .lambda().eq(Link::getLinkType, LinkConstants.LINK_TYPE_RECOMMEND.getLinkTypeId())
        );
        List<Link> personalLinks = linkService.list(new QueryWrapper<Link>()
                .lambda().eq(Link::getLinkType, LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeId())
        );
        //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
        HashMap<String, Object> result = new HashMap<>();
        result.put("pageName", "友情链接");
        result.put("favoriteLinks", favoriteLinks);
        result.put("recommendLinks", recommendLinks);
        result.put("personalLinks", personalLinks);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
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
                                  @Validated Comment comment) {
        String ref = request.getHeader("Referer");
        // 对非法字符进行转义，防止xss漏洞
        comment.setCommentBody(StringEscapeUtils.escapeHtml4(comment.getCommentBody()));
        comment.setCommentStatus(1);
        if (StringUtils.isEmpty(ref)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, "非法请求");
        }
        boolean flag = commentService.save(comment);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

}
