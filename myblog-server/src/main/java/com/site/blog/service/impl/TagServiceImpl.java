package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.constants.ShowEnum;
import com.site.blog.constants.SysConfigConstants;
import com.site.blog.service.TagService;
import com.site.blog.mapper.TagMapper;
import com.site.blog.model.entity.BlogTag;
import com.site.blog.model.entity.BlogTagCount;
import com.site.blog.model.entity.Tag;
import com.site.blog.service.BlogTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    private final BlogTagService blogTagService;


    private final TagMapper tagMapper;

    @Override
    public List<BlogTagCount> getBlogTagCountForIndex() {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Tag::getShow, ShowEnum.SHOW.getStatus());
        List<Tag> list = baseMapper.selectList(queryWrapper);
        List<BlogTagCount> blogTagCounts = list.stream()
                .map(blogTag -> new BlogTagCount()
                        .setTagId(blogTag.getTagId())
                        .setTagName(blogTag.getTagName())
                        .setTagCount(
                                blogTagService.count(new QueryWrapper<BlogTag>()
                                        .lambda().eq(BlogTag::getTagId, blogTag.getTagId()))
                        )).toList();
        return blogTagCounts;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int clearTag(String tagId) {
        LambdaQueryWrapper<BlogTag> queryWrapper = Wrappers.<BlogTag>lambdaQuery()
                .eq(BlogTag::getTagId, tagId);
        List<BlogTag> blogTagList = blogTagService.list(queryWrapper);
        //如果存在不tag和blog的对应关系
        if (blogTagList.isEmpty()) {
            return tagMapper.delete(new LambdaQueryWrapper<Tag>().eq(Tag::getTagId, tagId));


        } else {
            blogTagList = blogTagList.stream()
                    .peek(blogTag -> {

                        //如果blogtag包含默认标签,就不重置为默认标签,直接删除关系
                        if (blogTagService.list(new QueryWrapper<BlogTag>().eq("blog_id", blogTag.getBlogId())).stream().map(BlogTag::getTagId).collect(Collectors.toList()).contains(Integer.valueOf(SysConfigConstants.DEFAULT_TAG.getConfigField()))) {
                            blogTagService.removeById(blogTag);
                        } else {
                            blogTagService.updateById(blogTag.setTagId(  SysConfigConstants.DEFAULT_TAG.getConfigField()  ));
                        }
                    })
                    .collect(Collectors.toList());
            //blogTagService.updateBatchById(blogTagList);

            return tagMapper.deleteById(tagId);
        }
    }
}
