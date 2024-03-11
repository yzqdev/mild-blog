package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Tag;
import com.site.blog.service.TagService;
import com.site.blog.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/v2/admin")
public class TagController {

    @Resource
    private TagService tagService;


    /**
     * 返回未删除状态下的所有标签
     * @return
     */
    @GetMapping("/tags/list")
    public Result<AjaxResultPage<Tag>> tagsList(AjaxPutPage<Tag> ajaxPutPage) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        Optional.ofNullable(ajaxPutPage.getShow()) .ifPresent(show-> queryWrapper.eq(Tag::getShow,ajaxPutPage.getShow()));

        Page<Tag> page = ajaxPutPage.putPageToPage();
        tagService.page(page,queryWrapper);
        AjaxResultPage<Tag> result = new AjaxResultPage<>();
        result.setList(page.getRecords());
        result.setCount(page.getTotal());
        if (CollectionUtils.isEmpty(page.getRecords())) {
            ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, true,result);
    }

    /**
     * 标签分页
     *
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.Tag>
     * @date 2019/9/1 11:20
     */

    @GetMapping("/tags/paging")
    public AjaxResultPage<Tag> getCategoryList(AjaxPutPage<Tag> ajaxPutPage, Tag condition) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>(condition);
        queryWrapper.lambda()
                .ne(Tag::getTagId, 1);
        Page<Tag> page = ajaxPutPage.putPageToPage();
        tagService.page(page, queryWrapper);
        AjaxResultPage<Tag> result = new AjaxResultPage<>();
        result.setList(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改标签状态
     *
     * @param tag
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 14:55
     */

    @PostMapping("/tags/isDel")
    public Result<String> updateCategoryStatus(Tag tag) {
        tag.setUpdateTime(LocalDateTime.now());
        boolean flag = tagService.updateById(tag);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, tag.getTagName());
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 添加标签
     *
     * @param tag
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/2 10:12
     */

    @PostMapping("/tags/add")
    @SysLogAnnotation(title = "添加标签",opType = LogOperationEnum.ADD)
    public Result<?> addTag(Tag tag) {
        tag.setCreateTime(LocalDateTime.now());
        tag.setUpdateTime(LocalDateTime.now());
        boolean flag = tagService.save(tag);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, tag);
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 清除标签
     *
     * @param tagId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/2 18:41
     */

    @PostMapping("/tags/clear/{id}")
    @SysLogAnnotation(title = "清除标签",opType = LogOperationEnum.CLEAN)
    public Result<String> clearTag(@PathVariable("id") String tagId) throws RuntimeException {

        String name = tagService.getById(tagId).getTagName();
        if (tagService.clearTag(tagId)==1) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, name);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 修改标题名字
     *
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     * @author Linn-cn
     * @date 2020/9/1
     */

    @PostMapping("/tags/update")
    @SysLogAnnotation(title = "更新标签",opType = LogOperationEnum.UPDATE)
    public Result<Tag> updateCategory(@RequestBody Tag tag) {
         tag.setUpdateTime(LocalDateTime.now());

        tagService.updateById(tag);

        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,tag);
    }
}
