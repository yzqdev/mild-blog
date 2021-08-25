package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.constants.DeleteStatusEnum;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.mapper.BlogInfoMapper;
import com.site.blog.mapper.TagMapper;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.Tag;
import com.site.blog.model.entity.BlogTagCount;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.service.BlogInfoService;
import com.site.blog.service.BlogService;
import com.site.blog.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-28
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private BlogService blogService;

    @Resource
    private BlogInfoMapper blogInfoMapper;

    @Resource
    private BlogInfoService blogInfoService;

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Tag::getIsDeleted, DeleteStatusEnum.NO_DELETED.getStatus());
        List<Tag> list = baseMapper.selectList(queryWrapper);
        List<BlogTagCount> blogTagCounts = list.stream()
                .map(blogTag -> new BlogTagCount()
                        .setTagId(blogTag.getTagId())
                        .setTagName(blogTag.getTagName())
                        .setTagCount(
                                blogService.count(new QueryWrapper<BlogTag>()
                                        .lambda().eq(BlogTag::getTagId,blogTag.getTagId()))
                        )).collect(Collectors.toList());
        return blogTagCounts;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean clearTag(Integer tagId) {
        LambdaQueryWrapper<BlogTag> queryWrapper = Wrappers.<BlogTag>lambdaQuery()
                .eq(BlogTag::getTagId,tagId);
        List<BlogTag> tagRelationList = blogService.list(queryWrapper);
        // 批量更新的BlogInfo信息
        List<BlogInfo> infoList = tagRelationList.stream()
                .map(tagRelation -> new BlogInfo()
                        .setBlogId(tagRelation.getBlogId())
                        //.setBlogTags(SysConfigConstants.DEFAULT_TAG.getConfigName())
                ).collect(Collectors.toList());
        List<Long> blogIds = infoList.stream().map(BlogInfo::getBlogId).collect(Collectors.toList());
        // 批量更新的tagRelation信息
        List<BlogTag> tagRelations = tagRelationList.stream()
                .map(tagRelation -> new BlogTag()
                        .setBlogId(tagRelation.getBlogId())
                        .setTagId(Integer.valueOf(SysConfigConstants.DEFAULT_CATEGORY.getConfigField())))
                .collect(Collectors.toList());
        blogInfoService.updateBatchById(infoList);
        blogService.remove(new QueryWrapper<BlogTag>()
                .lambda()
                .in(BlogTag::getBlogId,blogIds));
        blogService.saveBatch(tagRelations);
        return retBool(baseMapper.deleteById(tagId));
    }
}
