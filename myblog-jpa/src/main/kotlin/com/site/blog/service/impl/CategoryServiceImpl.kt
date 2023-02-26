package com.site.blog.service.impl


import com.site.blog.constants.SysConfigConstants
import com.site.blog.mapper.CategoryMapper
import com.site.blog.model.entity.BlogCategory
import com.site.blog.model.entity.Category
import com.site.blog.service.BlogCategoryService
import com.site.blog.service.CategoryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 *
 * 博客分类 服务实现类
 *
 *
 */
@Service
class CategoryServiceImpl(
    private val categoryMapper: CategoryMapper,
    private val blogCategoryService: BlogCategoryService
) :  CategoryService {
    @Transactional(rollbackFor = [Exception::class])
    override fun clearCategory(id: Long ): Boolean {
        val category = categoryMapper.getReferenceById(id)
        val blogCategory = BlogCategory()
        blogCategory.categoryId = SysConfigConstants.DEFAULT_CATEGORY.configField
        val updateWrapper = KtQueryWrapper(BlogCategory::class.java)
            .eq(BlogCategory::categoryId, category!!.categoryId)
        blogCategoryService.update(blogCategory, updateWrapper)
        return JpaRepository!!.deleteById(category.categoryId) == 1
    }
}