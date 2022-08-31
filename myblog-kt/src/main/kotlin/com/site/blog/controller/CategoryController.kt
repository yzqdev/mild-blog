package com.site.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.site.blog.aop.LogOperationEnum
import com.site.blog.aop.SysLogAnnotation
import com.site.blog.constants.HttpStatusEnum
import com.site.blog.constants.ShowEnum
import com.site.blog.model.dto.AjaxPutPage
import com.site.blog.model.dto.AjaxResultPage
import com.site.blog.model.dto.Result
import com.site.blog.model.entity.Category
import com.site.blog.service.CategoryService
import com.site.blog.util.ResultGenerator.getResultByHttp
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.stereotype.Controller
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * 类别控制器
 *
 * @author yanni
 * @date 2022/06/17
 */
@Controller
@RequestMapping("/v2/admin")
@Tag(name = "分类json")
class CategoryController(private val categoryService: CategoryService) {
    /**
     * 分类的集合数据[用于下拉框]
     *
     * @param
     * @return com.site.blog.pojo.dto.Result<com.site.blog.entity.BlogCategory>
     * @date 2019/8/30 14:38
    </com.site.blog.entity.BlogCategory> */
    @ResponseBody
    @GetMapping("/category/list")
    fun categoryList(): Result<List<Category?>> {
        val queryWrapper = KtQueryWrapper (Category())
        queryWrapper.eq(Category::show, ShowEnum.SHOW.status).orderByDesc(Category::createTime)
        val list = categoryService.list(queryWrapper)
        if (CollectionUtils.isEmpty(list)) {
            getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
        }
        return getResultByHttp(HttpStatusEnum.OK, list)
    }

    /**
     * 分类的分页
     *
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogCategory>
     * @date 2019/8/30 14:38
    </com.site.blog.entity.BlogCategory> */
    @ResponseBody
    @GetMapping("/category/paging")
    fun getCategoryList(ajaxPutPage: AjaxPutPage<Category?>, condition: Category): Result<AjaxResultPage<Category?>> {
        val queryWrapper = LambdaQueryWrapper(condition)
        queryWrapper.ne(Category::categoryId, "1").orderByAsc(Category::categoryRank)
        val page = ajaxPutPage.putPageToPage()
        categoryService.page (page, queryWrapper)
        val result = AjaxResultPage<Category?>()
        result.list = page.records
        result.count = page.total
        return getResultByHttp(HttpStatusEnum.OK, true, result)
    }

    /**
     * 修改分类信息
     *
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @author Linn-cn
     * @date 2020/9/1
    </java.lang.String> */
    @ResponseBody
    @PostMapping("/category/update")
    @SysLogAnnotation(title = "更新分类", opType = LogOperationEnum.UPDATE)
    fun updateCategory(category: Category): Result<String> {
        val sqlCategory = categoryService.getById(category.categoryId)
        val flag = sqlCategory!!.categoryName == category.categoryName
        if (!flag) {
            categoryService.updateById(category)
        }
        return getResultByHttp(HttpStatusEnum.OK)
    }

    /**
     * 修改分类状态
     *
     * @param category
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 14:55
     */
    @ResponseBody
    @PostMapping("/category/isDel")
    @SysLogAnnotation(title = "更新分类", opType = LogOperationEnum.CHANGE_STATUS)
    fun updateCategoryStatus(category: Category): Result<String> {
        val flag = categoryService.updateById(category)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 清除分类信息
     *
     * @param id category id
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/1 15:48
     */
    @ResponseBody
    @PostMapping("/category/clear/{id}")
    @SysLogAnnotation(title = "清除分类", opType = LogOperationEnum.CLEAN)
    fun clearCategory(@PathVariable("id") id: String?): Result<*> {
        return if (categoryService.clearCategory(id)) {
            getResultByHttp(HttpStatusEnum.OK, true, id)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }

    /**
     * 新增分类信息
     *
     * @param category
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/1 15:48
     */
    @ResponseBody
    @PostMapping("/category/add")
    @SysLogAnnotation(title = "添加分类", opType = LogOperationEnum.ADD)
    fun addCategory(category: Category): Result<*> {
        category.createTime = LocalDateTime.now()
        category.show = ShowEnum.SHOW.status
        val flag = categoryService.save(category)
        return if (flag) {
            getResultByHttp(HttpStatusEnum.OK, true, category)
        } else getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR)
    }
}