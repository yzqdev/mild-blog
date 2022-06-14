package com.site.blog.controller;

import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogConfig;
import com.site.blog.service.BlogConfigService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yzqde
 * @qq交流群 796794009
 * @qq 1320291471
 * @Description: blog配置controller
 * @create 2020/12/27
 */
@RestController
@RequestMapping("/v2/admin")
@Tag(name = "配置信息")
@Slf4j
public class ConfigController {

    @Resource
    private BlogConfigService blogConfigService;



    /**
     * 返回系统配置信息
     * @param
     * @return com.site.blog.pojo.dto.AjaxResultPage<com.site.blog.entity.BlogConfig>
     * @date 2019/8/29 19:30
     */
    @GetMapping("/blogConfig/list")
    public Result<AjaxResultPage<BlogConfig>> getBlogConfig(){
        AjaxResultPage<BlogConfig> ajaxResultPage = new AjaxResultPage<>();
        List<BlogConfig> list = blogConfigService.lambdaQuery().orderByDesc(BlogConfig::getUpdateTime).list();
        if (CollectionUtils.isEmpty(list)){
            ajaxResultPage.setCode(500);
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR,false,ajaxResultPage);
        }
        ajaxResultPage.setList(list);
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,ajaxResultPage);
    }

    /**
     * 修改系统信息
     * @param blogConfig
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/29 19:45
     */
    @PostMapping("/blogConfig/edit")
    public Result<String> updateBlogConfig(@RequestBody  BlogConfig blogConfig){
        blogConfig.setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogConfigService.updateById(blogConfig);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }




    /**
     * 新增系统信息项
     * @param blogConfig
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 10:57
     */
    @PostMapping("/blogConfig/add")
    public Result  addBlogConfig(@RequestBody BlogConfig blogConfig){
       log.info(String.valueOf(blogConfig));
        blogConfig.setCreateTime(DateUtils.getLocalCurrentDate());
        blogConfig.setUpdateTime(DateUtils.getLocalCurrentDate());
        boolean flag = blogConfigService.save(blogConfig);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除配置信息项
     * @param configField
     * @return com.site.blog.pojo.dto.Result
     * @date 2019/8/30 11:21
     */
    @DeleteMapping("/blogConfig/del/{id}")
    public Result<String> delBlogConfig(@PathVariable("id") String configField){
        boolean flag = blogConfigService.removeById(configField);
        if (flag){
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }else{
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
    }
}
