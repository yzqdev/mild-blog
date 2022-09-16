package com.site.blog.controller

import cn.hutool.core.lang.Console
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.site.blog.constants.CommentStatusEnum
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.constants.LinkConstants
import com.site.blog.constants.ShowEnum
import com.site.blog.model.dto.*
import com.site.blog.model.entity.*
import com.site.blog.model.vo.BlogDetailVO
import com.site.blog.service.*
import com.site.blog.util.BeanMapUtil
import com.site.blog.util.RequestHelper
import com.site.blog.util.BaseResult.getResultByHttp
import com.site.blog.util.ResultDto
import org.apache.commons.text.StringEscapeUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

/**
 * 博客前台
 *
 * @author Linn-cn
 * @date 2020/12/7
 */
@RestController
@RequestMapping("/v2/home")
@io.swagger.v3.oas.annotations.tags.Tag(name = "首页数据", description = "首页")
class HomeBlogController(
    private val blogInfoService: BlogInfoService,
    private val tagService: TagService,
    private val blogCategoryService: BlogCategoryService,
    private val categoryService: CategoryService,
    private val blogTagService: BlogTagService,
    private val blogConfigService: BlogConfigService,
    private val blogService: BlogTagService,
    private val commentService: CommentService,
    private val linkService: LinkService
) {
    val log=LoggerFactory.getLogger(this.javaClass)
    /**
     * 博客首页
     *
     * @author Linn-cn
     * @date 2020/12/7
     */
    @GetMapping("/", "/index")
    fun index(): ResultDto<*> {
        return page(
            BlogPageCondition(pageNum = 1, pageName = "首页")

        )
    }

    /**
     * 标签
     *
     * @param tagId
     * @return java.lang.String
     * @date 2019/9/6 7:04
     */
    @PostMapping(*["/tag/{tagId}"])
    fun tag(@PathVariable("tagId") tagId: String?, @RequestBody pageDto: PageDto): ResultDto<*> {
        val page = Page<BlogInfo>(
            pageDto.pageNum!!.toLong(), pageDto.pageSize!!.toLong()
        )
        val sqlWrapper = Wrappers.lambdaQuery<BlogInfo>()
            .eq(BlogInfo::show, ShowEnum.SHOW.status).eq(BlogInfo::deleted, false)
        //获取tag下的文章
        if (Objects.nonNull(tagId)) {
            val list = blogService.list(
                QueryWrapper<BlogTag>()
                    .lambda().eq(BlogTag::tagId, tagId)
            )
            if (!CollectionUtils.isEmpty(list)) {
                sqlWrapper.`in`(BlogInfo::blogId,  list.map{ it?.blogId })
            } else {
                return getResultByHttp(HttpStatusEnum.OK, true, emptyList<Any>())
            }
        }
        sqlWrapper.orderByDesc(BlogInfo::createTime)
        val blogInfoPage = blogInfoService.page(page, sqlWrapper)
        val blogDetailVOS = toBlogVo(blogInfoPage)
        return getResultByHttp(HttpStatusEnum.OK, true, blogDetailVOS)
    }

    /**
     * bloginfo转blogdetailvo
     *
     * @param blogInfoPage
     * @return
     */
    fun toBlogVo(blogInfoPage: Page<BlogInfo>): List<BlogDetailVO> {
        Console.log("toBlog", blogInfoPage.records)
        val blogDetailVOS = blogInfoPage.records.map {(BeanMapUtil::copyBlog)(it)  }
     log.info("blogDetailVOS:{}", blogDetailVOS)
        blogDetailVOS.forEach {
            val tagQueryWrapper = KtQueryWrapper(BlogTag()).eq(BlogTag::blogId, it.blogId)
            val tags = blogTagService.list(tagQueryWrapper).map {
                tagService.getOne(KtQueryWrapper (Tag()).eq(Tag::tagId, it?.tagId).eq(Tag::show, true))
            }
            it.blogTags=tags
            val cateId=blogCategoryService.getOne(KtQueryWrapper (BlogCategory()).eq(BlogCategory::blogId,it.blogId))?.categoryId
            if (cateId!=null){
                it.blogCategory=categoryService.getById(cateId)
            }
        }

        return blogDetailVOS
    }

    @PostMapping("/timeline")
    fun timeline(@RequestBody pageDto: PageDto): ResultDto<*> {
        return try {
            val queryWrapper = KtQueryWrapper (BlogInfo())
            queryWrapper.orderByDesc(BlogInfo::updateTime).eq(BlogInfo::deleted, false).eq(BlogInfo::show, true)
            val ipage = Page<BlogInfo>(
                pageDto.pageNum!!.toLong(), pageDto.pageSize!!.toLong()
            )
            val blogInfoPage = blogInfoService.page(ipage, queryWrapper)
            val blogDetailVOS = blogInfoPage.records.map { (BeanMapUtil::copyBlog)(it) }
            log.info("blogDetailVOS:{}", blogDetailVOS)
            blogDetailVOS.forEach {
                val tagQueryWrapper = KtQueryWrapper (BlogTag()).eq(BlogTag::blogId, it.blogId)
                val tags = blogTagService.list(tagQueryWrapper).map {
                    tagService.getOne( KtQueryWrapper (Tag()).eq(Tag::tagId, it?.tagId).eq(Tag::show, true))
                }
                it.blogTags=tags
                val cateId=blogCategoryService.getOne(KtQueryWrapper (BlogCategory()).eq(BlogCategory::blogId,it.blogId))?.categoryId
                if (cateId!=null){
                    it.blogCategory=categoryService.getById(cateId)
                }
            }
            getResultByHttp(HttpStatusEnum.OK, true, blogDetailVOS)
        } catch (e: Exception) {
            e.printStackTrace()
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, "失败了")
        }
    }

    /**
     * 分类
     *
     * @param categoryId
     * @author Linn-cn
     * @date 2020/12/7
     */
    @PostMapping(*["/category/{categoryId}"])
    fun category(@PathVariable("categoryId") categoryId: String?, @RequestBody pageDto: PageDto): ResultDto<*> {
        val page = Page<BlogInfo>(
            pageDto.pageNum!!.toLong(), pageDto.pageSize!!.toLong()
        )
        val sqlWrapper = Wrappers.lambdaQuery<BlogInfo>()
            .eq(BlogInfo::show, ShowEnum.SHOW.status).eq(BlogInfo::deleted, false)
        //获取tag下的文章
        if (Objects.nonNull(categoryId)) {
            val list = blogCategoryService.list(
                QueryWrapper<BlogCategory>()
                    .lambda().eq(BlogCategory::categoryId, categoryId)
            )
            if (!CollectionUtils.isEmpty(list)) {
                sqlWrapper.`in`(BlogInfo::blogId, list.map{ it?.blogId } )
            } else {
                return getResultByHttp(HttpStatusEnum.OK, true, emptyList<Any>())
            }
        }
        sqlWrapper.orderByDesc(BlogInfo::createTime)
        val blogInfoPage = blogInfoService.page(page, sqlWrapper)
        val blogDetailVOS = toBlogVo(blogInfoPage)
        return getResultByHttp(HttpStatusEnum.OK, true, blogDetailVOS)
    }

    /**
     * 搜索
     *
     * @param keyword
     * @return java.lang.String
     * @date 2019/9/6 7:03
     */
    @GetMapping("/search/{keyword}")
    fun search(@PathVariable("keyword") keyword: String?): ResultDto<*> {
        try {
            val (pageNum, pageSize) = PageDto(0, 10)
            val ipage = Page<BlogInfo>(
                pageNum!!.toLong(), pageSize!!.toLong()
            )
            val queryWrapper = QueryWrapper<BlogInfo>()
            queryWrapper.lambda().like(BlogInfo::blogTitle, keyword).or().like(BlogInfo::blogContent, keyword)
            val blogInfos = blogInfoService.page(ipage, queryWrapper)
            return getResultByHttp(HttpStatusEnum.OK, toBlogVo(blogInfos))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return getResultByHttp<Any?>(HttpStatusEnum.OK, null)
    }

    @get:GetMapping("/tags")
    val tags: ResultDto<*>
        get() {
            val queryWrapper = QueryWrapper<Tag>()
            queryWrapper.lambda().eq(Tag::show, ShowEnum.SHOW.status)
            val list = tagService.list()
            if (CollectionUtils.isEmpty(list)) {
                getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, "失败了")
            }
            return getResultByHttp(HttpStatusEnum.OK, true, list)
        }

    @GetMapping("/categories")
    fun cate(): ResultDto<*>
        {
            val queryWrapper = KtQueryWrapper (Category())
            queryWrapper. eq(Category::show, ShowEnum.SHOW.status).orderByDesc(Category::createTime)
            val list = categoryService.list(queryWrapper)
            if (CollectionUtils.isEmpty(list)) {
                getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
            }
            return getResultByHttp(HttpStatusEnum.OK, true, list)
        }

    @GetMapping("/configs")
    fun configs(): ResultDto<Any>
         {
            try {
                return getResultByHttp(HttpStatusEnum.OK, blogConfigService.allConfigs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return getResultByHttp(HttpStatusEnum.BAD_REQUEST, "00")
        }

    /**
     * 博客分页
     *
     * @param condition
     * @throws
     * @author Linn-cn
     * @date 2020/12/7
     */
    private fun page(condition: BlogPageCondition): ResultDto<*> {
        if (Objects.isNull(condition.pageNum)) {
            condition.pageNum = 1
        }
        if (Objects.isNull(condition.pageSize)) {
            condition.pageSize = 5
        }
        val result = HashMap<String, Any>(16)


        //start
        val queryWrapper = KtQueryWrapper(BlogInfo())
        queryWrapper.orderByDesc(BlogInfo::updateTime).eq(BlogInfo::deleted, false).eq(BlogInfo::show, true)
        val ipage = Page<BlogInfo>(
            condition.pageNum!!.toLong(), condition.pageSize!!.toLong()
        )
        val blogInfoPage = blogInfoService.page(ipage, queryWrapper)
        println("获得bloginfo")
        println(blogInfoPage.toString())
        val blogDetailVOS = toBlogVo(blogInfoPage)
        result["blogPageResult"] = blogDetailVOS
        result["newBlogs"] = blogInfoService.getNewBlog()
        result["hotBlogs"] = blogInfoService.getHotBlog()
        result["hotTags"] = tagService.getBogTagCountForIndex()
        return getResultByHttp(HttpStatusEnum.OK, result)
    }

    /**
     * 文章详情
     *
     * @param blogId
     * @return java.lang.String
     * @date 2019/9/6 13:09
     */
    @GetMapping("/blog/{blogId}", "/article/{blogId}")
    fun detail(@PathVariable("blogId") blogId: String?): ResultDto<Any> {
        // 获得文章info
        val blogInfo = blogInfoService.getById(blogId)
        val blogTags = blogService.list(
            KtQueryWrapper (BlogTag())

                .eq(BlogTag::blogId, blogId)
        )
        if (blogInfo != null) {
            blogInfoService.updateById(
                BlogInfo(blogId=blogInfo.blogId, blogViews = blogInfo.blogViews?.plus(1))
            )
        }

        // 获得关联的标签列表
        val tagIds: List<String?>
        var tagList: List<Tag?> = ArrayList()
        if (blogTags.isNotEmpty()) {
            tagIds = blogTags.map { it?.tagId    }
            tagList = tagService.list(
                KtQueryWrapper (Tag())
                    .`in`(Tag::tagId, tagIds)
            )
        }

        // 关联评论的Count
        val blogCommentCount = commentService.count(
            KtQueryWrapper (Comment())
                .eq(Comment::commentStatus, CommentStatusEnum.ALLOW.status)
                .eq(Comment::deleted, ShowEnum.SHOW.status)
                .eq(Comment::blogId, blogId)
        )
        val result = HashMap<String, Any>()
        val blogDetailVO = BlogDetailVO()
        BeanUtils.copyProperties(blogInfo!!, blogDetailVO)
        blogDetailVO.commentCount = blogCommentCount
        result["blogDetailVO"] = blogDetailVO
        result["tagList"] = tagList
        return getResultByHttp(HttpStatusEnum.OK, true, result)
    }

    /**
     * 评论列表
     *
     * @param ajaxPutPage
     * @param blogId
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogComment>
     * @date 2019/11/19 8:42
    </com.site.blog.entity.BlogComment> */
    @GetMapping("/blog/listComment")
    @ResponseBody
    fun listComment(ajaxPutPage: AjaxPutPage<Comment?>, blogId: String?): ResultDto<AjaxResultPage<Comment?>> {
        val page = ajaxPutPage.putPageToPage()
        commentService.page(
            page, KtQueryWrapper(Comment())

                .eq(Comment::blogId, blogId)
                .eq(Comment::commentStatus, CommentStatusEnum.ALLOW.status)
                .eq(Comment::deleted, ShowEnum.SHOW.status)
                .orderByDesc(Comment::commentCreateTime)
        )
        val ajaxResultPage = AjaxResultPage<Comment?>()
        ajaxResultPage.count = page.total
        ajaxResultPage.list = page.records
        return getResultByHttp(HttpStatusEnum.OK, true, ajaxResultPage)
    }

    /**
     * 友链界面
     *
     * @return java.lang.String
     * @date 2019/9/6 17:26
     */
    @GetMapping("/link")
    fun link(): ResultDto<*> {
        val favoriteLinks = linkService.list(
            KtQueryWrapper (Link())
                .eq(Link::linkType, LinkConstants.LINK_TYPE_FRIENDSHIP.linkTypeId).eq(Link::show, true)
        )
        val recommendLinks = linkService.list(
            KtQueryWrapper (Link())
                .eq(Link::linkType, LinkConstants.LINK_TYPE_RECOMMEND.linkTypeId).eq(Link::show, true)
        )
        val personalLinks = linkService.list(
            KtQueryWrapper (Link())
                .eq(Link::linkType, LinkConstants.LINK_TYPE_PRIVATE.linkTypeId).eq(Link::show, true)
        )
        //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
        val result = HashMap<String, Any>()
        result["pageName"] = "友情链接"
        result["favoriteLinks"] = favoriteLinks
        result["recommendLinks"] = recommendLinks
        result["personalLinks"] = personalLinks
        return getResultByHttp(HttpStatusEnum.OK, true, result)
    }

    /**
     * 提交评论
     *
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/6 17:40
     */
    @PostMapping(value = ["/blog/comment"])
    @ResponseBody
    fun comment(comment: Comment): ResultDto<in String?> {
        try {
            val request = RequestHelper.getHttpServletRequest()
            val ref = request.getHeader("Referer")


            // 对非法字符进行转义，防止xss漏洞
            comment.commentBody = StringEscapeUtils.escapeHtml4(comment.commentBody)
            comment.commentStatus = true
            comment.commentatorIp =RequestHelper.getRequestIp()
            comment.userAgent =RequestHelper.getUa().browser.toString() + RequestHelper.getUa().version
            comment.os =  RequestHelper.getUa().os.toString()
            comment.commentCreateTime = LocalDateTime.now()
            comment.deleted = true

            val flag = commentService.save(comment)

             return   getResultByHttp(HttpStatusEnum.OK, true, comment)

        }catch (e:Exception){
            e.printStackTrace()
          return getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR, false, e.toString())
        }
    }
}