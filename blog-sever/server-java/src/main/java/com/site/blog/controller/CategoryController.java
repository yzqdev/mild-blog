package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.ShowEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Category;
import com.site.blog.service.CategoryService;
import com.site.blog.util.ResultGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 类别控制器
 *
 * @author yanni
 * @date 2022/06/17
 */
@Controller
@RequestMapping("/v2/admin")
@Tag(name = "分类json")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;


    /**
     * 分类的集合数据[用于下拉框]
     *
     * @param
     * @return com.site.blog.pojo.dto.Result<com.site.blog.entity.BlogCategory>
     * @date 2019/8/30 14:38
     */
    @ResponseBody
    @GetMapping("/category/list")
    public Result<List<Category>> categoryList() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getShow, ShowEnum.SHOW.getStatus()).orderByDesc(Category::getCreateTime);
        List<Category> list = categoryService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, list);
    }


    /**
     * 分类的分页
     *
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogCategory>
     * @date 2019/8/30 14:38
     */
    @ResponseBody
    @GetMapping("/category/paging")
    public Result<AjaxResultPage<Category>> getCategoryList(AjaxPutPage<Category> ajaxPutPage, Category condition) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>(condition);
        queryWrapper .ne(Category::getCategoryId, "1").orderByAsc(Category::getCategoryRank);
        Page<Category> page = ajaxPutPage.putPageToPage();
        categoryService.page(page, queryWrapper);
        AjaxResultPage<Category> result = new AjaxResultPage<>();
        result.setList(page.getRecords());
        result.setCount(page.getTotal());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, result);
    }

    /**
     * 修改分类信息
     *
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @author Linn-cn
     * @date 2020/9/1
     */
    @ResponseBody
    @PostMapping("/category/update")
    @SysLogAnnotation(title = "更新分类",opType = LogOperationEnum.UPDATE)
    public Result<String> updateCategory(Category category) {
        Category sqlCategory = categoryService.getById(category.getCategoryId());
        boolean flag = sqlCategory.getCategoryName().equals(category.getCategoryName());
        if (!flag) {


            categoryService.updateById(category);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,"成功");
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
    @SysLogAnnotation(title = "更新分类",opType = LogOperationEnum.CHANGE_STATUS)
    public Result<String> updateCategoryStatus(Category category) {
        boolean flag = categoryService.updateById(category);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,"成功");
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
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
    @SysLogAnnotation(title = "清除分类",opType = LogOperationEnum.CLEAN)
    public Result clearCategory(@PathVariable("id") String id) {
        if (categoryService.clearCategory(id)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, id);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
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
    @SysLogAnnotation(title = "添加分类",opType = LogOperationEnum.ADD)
    public Result addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setShow(ShowEnum.SHOW.getStatus());
        boolean flag = categoryService.save(category);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true, category);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


}
