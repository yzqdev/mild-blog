package com.site.blog.controller.v2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.service.BlogTagService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @qq交流群 796794009
 * @qq 1320291471
 * @Description: 标签Controller
 * @create 2020/12/27
 */
@RestController
@RequestMapping("/v2/admin")
public class TagJsonController {

    @Resource
    private BlogTagService blogTagService;


     

    /**
     * @Description: 返回未删除状态下的所有标签
     * @Param: []
     * @return: com.zhulin.blog.dto.Result<com.zhulin.blog.entity.BlogTag>
     * @date: 2019/8/26 10:13
     */
     
    @GetMapping("/tags/list")
    public Result<List<BlogTag>> tagsList(){
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<BlogTag>();
        queryWrapper.lambda().eq(BlogTag::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        List<BlogTag> list = blogTagService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)){
            ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,list);
    }

    /**
     * 标签分页
     * @param ajaxPutPage
     * @param condition
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogTag>
     * @date 2019/9/1 11:20
     */
     
    @GetMapping("/tags/paging")
    public AjaxResultPage<BlogTag> getCategoryList(AjaxPutPage<BlogTag> ajaxPutPage, BlogTag condition){
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>(condition);
        queryWrapper.lambda()
                .ne(BlogTag::getTagId,1);
        Page<BlogTag> page = ajaxPutPage.putPageToPage();
        blogTagService.page(page,queryWrapper);
        AjaxResultPage<BlogTag> result = new AjaxResultPage<>();
        result.setData(page.getRecords());
        result.setCount(page.getTotal());
        return result;
    }

    /**
     * 修改标签状态
     * @param blogTag
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 14:55
     */
     
    @PostMapping("/tags/isDel")
    public Result<String> updateCategoryStatus(BlogTag blogTag){
        boolean flag = blogTagService.updateById(blogTag);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 添加标签
     * @param blogTag
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/2 10:12 
     */
     
    @PostMapping("/tags/add")
    public Result<String> addTag(BlogTag blogTag){
        blogTag.setCreateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogTagService.save(blogTag);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 清除标签
     * @param tagId
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/9/2 18:41
     */
     
    @PostMapping("/tags/clear")
    public Result<String> clearTag(Integer tagId) throws RuntimeException{
        if (blogTagService.clearTag(tagId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }


    /**
     * 修改标题名字
     * @author Linn-cn
     * @date 2020/9/1
     * @return com.site.blog.pojo.dto.Result<java.lang.String>
     */
     
    @PostMapping("/tags/update")
    public Result<String> updateCategory(BlogTag blogTag) {
        // TODO blogInfo的tags无实际意义，所以这里不再修改冗余的tags。
        BlogTag sqlBlogTag = blogTagService.getById(blogTag.getTagId());
        boolean flag = sqlBlogTag.getTagName().equals(blogTag.getTagName());
        if (!flag) {
            blogTagService.updateById(blogTag);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }
}
