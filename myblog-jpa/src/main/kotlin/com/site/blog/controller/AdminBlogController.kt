package com.site.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.constants.ShowEnum
import com.site.blog.model.dto.AjaxPutPage
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.dto.BlogInfoDo
import com.site.blog.model.entity.BlogCategory
import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.entity.BlogTag
import com.site.blog.model.entity.Tag
import com.site.blog.model.vo.BlogDetailVO
import com.site.blog.model.vo.BlogEditVO
import com.site.blog.service.*
import com.site.blog.util.BeanMapUtil
import com.site.blog.util.DateUtils.localCurrentDate
import com.site.blog.util.BaseResult.getResultByHttp
import com.site.blog.util.BaseResult.ok
import com.site.blog.util.ResultDto

import org.slf4j.LoggerFactory
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/v2/admin")
@io.swagger.v3.oas.annotations.tags.Tag(name = "博客json")
class AdminBlogController(
    private val blogCategoryService: BlogCategoryService,
    private val categoryService: CategoryService,
    private val blogInfoService: BlogInfoService,
    private val blogTagService: BlogTagService,
    private val tagService: TagService,
    private val blogService: BlogTagService
) {
    /**
     * 跳转博客编辑界面
     *
     * @return java.lang.String
     * @date 2019/8/28 15:03
     */
    @GetMapping("/blog/get/{id}")
    fun getBlogById(@PathVariable("id") id: String?): ResultDto<*> {
        val blogInfo = blogInfoService.getOne(KtQueryWrapper (BlogInfo::class.java).eq(BlogInfo::blogId, id))
        val blogDetailVO = BlogEditVO()
        BeanUtils.copyProperties(blogInfo!!, blogDetailVO)
        blogDetailVO.blogCategoryId =
            blogCategoryService.getOne(QueryWrapper<BlogCategory>().eq("blog_id", blogInfo.blogId))!!.categoryId
        val queryWrapper = QueryWrapper<BlogTag>().eq("blog_id", id)
        val ids = blogTagService.list(queryWrapper).map { it?.tagId }
        blogDetailVO.blogTagIds = ids
        log.info("home创建时间{}", blogDetailVO.createTime.toString())
        return getResultByHttp(HttpStatusEnum.OK,true, blogDetailVO)
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
    @Transactional(rollbackFor = [Exception::class])
    fun saveBlog(@RequestBody blogInfoDo: BlogInfoDo): ResultDto<*> {
        val blogInfo = BlogInfo().apply {
            blogId = blogInfoDo.blogId
            blogViews = 0L
            blogTitle = blogInfoDo.blogTitle
            subUrl = blogInfoDo.subUrl
            enableComment = blogInfoDo.enableComment
            show = blogInfoDo.show
            createTime = LocalDateTime.now()
            updateTime = LocalDateTime.now()
            blogContent = blogInfoDo.blogContent
            preface = blogInfoDo.preface
            deleted = false
        }
    
        if (blogInfoService.saveOrUpdate(blogInfo)) {
            val blogCategory = BlogCategory()
            blogCategory.blogId = blogInfo.blogId

            //添加blog和分类映射关系
            if (blogInfoDo.blogCategoryId == null) {
                //blogCategoryService.save()
                blogCategory.categoryId = "1"
            } else {
                val queryWrapper =KtQueryWrapper(BlogCategory::class.java).eq(BlogCategory::blogId, blogInfo.blogId)
                if (blogCategoryService.getOne(queryWrapper) == null) {
                    blogCategory.categoryId = "1"
                } else {
                    blogCategoryService.remove(queryWrapper)
                    blogCategory.categoryId = blogInfoDo.blogCategoryId
                }

                //blogCategory.setCategoryId(blogInfoDo.getBlogCategoryId());
            }
            blogCategory.createTime = localCurrentDate
            blogCategoryService.save(blogCategory)

            //添加blog和标签映射关系
            blogTagService.remove(QueryWrapper<BlogTag>().eq("blog_id", blogInfo.blogId))
            for (tagId in blogInfoDo.blogTagIds!!) {
                val blogTag: BlogTag = BlogTag(
                    blogId = blogInfo.blogId, tagId = tagId, createTime =
                    localCurrentDate
                )
                blogTagService.save(blogTag)
            }
            //blogService.removeAndsaveBatch(Arrays.asList(blogInfo.getBlogTags().split(",")), blogInfo);
            return getResultByHttp(HttpStatusEnum.OK,true, blogInfo)
        }
        return getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR,false,)
    }

    /**
     * 文章分页列表
     *
     * @param ajaxPutPage
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogInfo>
     * @date 2019/8/28 16:43
    </com.site.blog.entity.BlogInfo> */
    @GetMapping("/blog/list")
    fun getBlogList(ajaxPutPage: AjaxPutPage<BlogInfo?>): ResultDto<*> {
        return try {
            val queryWrapper = KtQueryWrapper (BlogInfo())
            queryWrapper .orderByDesc(BlogInfo::updateTime)
            if (java.lang.Boolean.TRUE == ajaxPutPage.deleted) {
                queryWrapper .eq(BlogInfo::deleted, true)
            } else {
                queryWrapper .eq(BlogInfo::deleted, false)
            }
            val page = ajaxPutPage.putPageToPage()
            val blogInfoPage = blogInfoService.page(page, queryWrapper)
            log.info("blogInfoPage:====>{}", blogInfoPage.records)
            val blogDetailVOS = blogInfoPage.records.map { (BeanMapUtil::copyBlog)(it) }
            log.info("blogDetailVOS:{}", blogDetailVOS)
            blogDetailVOS.forEach {
                val tagQueryWrapper = KtQueryWrapper (BlogTag::class.java).eq(BlogTag::blogId, it.blogId)
                val tags = blogTagService.list(tagQueryWrapper).map {
                    tagService.getOne(KtQueryWrapper (Tag::class.java).eq(Tag::tagId, it?.tagId).eq(Tag::show, true))
                }
                it.blogTags=tags
                val cateId=blogCategoryService.getOne( KtQueryWrapper (BlogCategory::class.java).eq(BlogCategory::blogId,it.blogId))?.categoryId
                if (cateId!=null){
                    it.blogCategory=categoryService.getById(cateId)
                }
            }
            val list = AjaxResultPage<BlogDetailVO>()
            list.count = blogInfoPage.total
            list.list = blogDetailVOS
            getResultByHttp(HttpStatusEnum.OK, true, list)
        } catch (e: Exception) {
            e.printStackTrace()
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR )
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
    fun hideBlog(@PathVariable("id") id: String?, @RequestParam("show") show: Boolean?): ResultDto<String> {
        val sqlBlog = blogInfoService.getOne(KtQueryWrapper (BlogInfo::class.java).eq(BlogInfo::blogId, id))
        sqlBlog!!.show = show
        val flag = blogInfoService.updateById(sqlBlog)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK,true,"成功")
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 修改文章的删除状态为已删除
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 14:02
     */
    @PostMapping("/blog/delete/{id}")
    fun deleteBlog(@PathVariable("id") blogId: String?, @RequestParam("restore") restore: Boolean): ResultDto<*> {
        val blogInfo = blogInfoService.getOne(QueryWrapper<BlogInfo>().eq("blog_id", blogId))!!
        blogInfo.apply {
            show = ShowEnum.NOT_SHOW.status
            updateTime = LocalDateTime.now()
        }
        blogInfo.deleted = java.lang.Boolean.TRUE != restore
        val flag = blogInfoService.updateById(blogInfo)
        return if (flag) {
            getResultByHttp<BlogInfo?>(HttpStatusEnum.OK,true, blogInfo)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 清除文章
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 14:02
     */
    @PostMapping("/blog/clear/{id}")
    fun clearBlog(@PathVariable("id") blogId: String ): ResultDto<*> {
        return if (blogInfoService.clearBlogInfo(blogId) ) {
            getResultByHttp(HttpStatusEnum.OK,true, blogId)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR,false)
    }

    /**
     * 还原文章
     *
     * @param blogId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 16:36
     */
    @PostMapping("/blog/restore")
    @SysLogAnnotation(title = "恢复博客", opType = LogOperationEnum.CHANGE_STATUS)
    fun restoreBlog(@RequestParam blogId: String): ResultDto<String> {
        val blogInfo: BlogInfo =
            BlogInfo(blogId = blogId, show = ShowEnum.SHOW.status, updateTime = LocalDateTime.now())

        val flag = blogInfoService.updateById(blogInfo)
        return if (flag) {
           ok("恢复成功")
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    companion object {
        private val log = LoggerFactory.getLogger(AdminBlogController::class.java)
    }
}