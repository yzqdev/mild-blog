package com.site.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.site.blog.aop.LogOperationEnum;
import com.site.blog.aop.SysLogAnnotation;
import com.site.blog.constants.HttpStatusEnum;
import com.site.blog.constants.LinkConstants;
import com.site.blog.model.dto.AjaxPutPage;
import com.site.blog.model.dto.AjaxResultPage;
import com.site.blog.model.dto.Result;
import com.site.blog.model.entity.Link;
import com.site.blog.service.LinkService;
import com.site.blog.util.DateUtils;
import com.site.blog.util.ResultGenerator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@Tag(name = "链接json")
public class LinkController {

    @Resource
    private LinkService linkService;


    @GetMapping("/linkType/list")
    public Result<List<Link>> linkTypeList() {
        List<Link> links = new ArrayList<>();
        links.add(new Link().setLinkType(LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeName()));
        links.add(new Link().setLinkType(LinkConstants.LINK_TYPE_RECOMMEND.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_RECOMMEND.getLinkTypeName()));
        links.add(new Link().setLinkType(LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeId())
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
    public Result getLinkList(AjaxPutPage<Link> ajaxPutPage) {

       var queryWrapper = new QueryWrapper<Link>( );
        queryWrapper.lambda()
                .orderByAsc(Link::getLinkRank);
        if (ajaxPutPage != null) {
            Page<Link> page = ajaxPutPage.putPageToPage();
            linkService.page(page, queryWrapper);
            AjaxResultPage<Link> result = new AjaxResultPage<>();
            result.setList(page.getRecords());
            result.setCount(page.getTotal());
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true, result);
        } else {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST, false,"fail");
        }
    }

    @PostMapping("/link/hide")
    @SysLogAnnotation(title = "删除链接",opType = LogOperationEnum.CHANGE_STATUS)
    public Result  updateLinkStatus(Link link) {

        boolean flag = linkService.updateById(link);


        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, link );
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/link/clear/{id}")
    @SysLogAnnotation(title = "清除链接",opType = LogOperationEnum.CLEAN)
    public Result  clearLink(@PathVariable("id") String linkId) {
        boolean flag = linkService.removeById(linkId);
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK,true,linkId);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }





    @PostMapping("/link/edit")
    @SysLogAnnotation(title = "编辑链接",opType = LogOperationEnum.EDIT)
    public Result updateAndSaveLink(Link link) {
        link.setCreateTime(DateUtils.getLocalCurrentDate());
        boolean flag;
        if (link.getLinkId() != null) {
            link.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            flag = linkService.updateById(link);
        } else {
            flag = linkService.save(link);
        }
        if (flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, link.getLinkId());
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
    }
}
