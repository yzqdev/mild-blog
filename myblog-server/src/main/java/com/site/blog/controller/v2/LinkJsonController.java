package com.site.blog.controller.v2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.LinkConstants;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.BlogLink;
import com.site.blog.service.BlogLinkService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yzqde
 * @qq 1320291471
 * @Description: 友链Controller
 * @create 2020/12/27
 */
@RestController
@RequestMapping("/v2/admin")
@Api(tags = "链接json")
public class LinkJsonController {

    @Resource
    private BlogLinkService blogLinkService;


    @GetMapping("/linkType/list")
    public Result<List<BlogLink>> linkTypeList() {
        List<BlogLink> links = new ArrayList<>();
        links.add(new BlogLink().setLinkType(LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeName()));
        links.add(new BlogLink().setLinkType(LinkConstants.LINK_TYPE_RECOMMEND.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_RECOMMEND.getLinkTypeName()));
        links.add(new BlogLink().setLinkType(LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeName()));
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, links);
    }

    /**
     * /v2/admin/link/paging?page=1&limit=30
     *
     * @param ajaxPutPage
     * @return
     */
    @GetMapping("/link/paging")
    public Result getLinkList(AjaxPutPage<BlogLink> ajaxPutPage) {
        BlogLink condition = new BlogLink();
        QueryWrapper<BlogLink> queryWrapper = new QueryWrapper<>(condition);
        queryWrapper.lambda()
                .orderByAsc(BlogLink::getLinkRank);
        if (ajaxPutPage != null) {
            Page<BlogLink> page = ajaxPutPage.putPageToPage();
            blogLinkService.page(page, queryWrapper);
            AjaxResultPage<BlogLink> result = new AjaxResultPage<>();
            result.setData(page.getRecords());
            result.setCount(page.getTotal());
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, "fail");
        }
    }

    @PostMapping("/link/isDel")
    public Result  updateLinkStatus(BlogLink blogLink) {
        System.out.println(blogLink);
        boolean flag = blogLinkService.updateById(blogLink);

        List<BlogLink> blogLinkList=blogLinkService.list();
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,blogLinkList);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/link/clear")
    public Result<String> clearLink(Integer linkId) {
        boolean flag = blogLinkService.removeById(linkId);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }





    @PostMapping("/link/edit")
    public Result<String> updateAndSaveLink(BlogLink blogLink) {
        blogLink.setCreateTime(DateUtils.getLocalCurrentDate());
        boolean flag;
        if (blogLink.getLinkId() != null) {
            flag = blogLinkService.updateById(blogLink);
        } else {
            flag = blogLinkService.save(blogLink);
        }
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }
}
