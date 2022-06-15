package com.site.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.site.blog.constants.BlogStatusEnum;
import com.site.blog.constants.ShowEnum;
import com.site.blog.mapper.*;
import com.site.blog.model.dto.PageDto;
import com.site.blog.model.entity.*;
import com.site.blog.model.vo.BlogDetailVO;
import com.site.blog.model.vo.SimpleBlogListVO;
import com.site.blog.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 博客信息表 服务实现类
 * </p>
 *
 * @author: 南街
 * @since 2019-08-27
 */
@Service
@RequiredArgsConstructor
public class BlogInfoServiceImpl extends ServiceImpl<BlogInfoMapper, BlogInfo> implements BlogInfoService {


    private final BlogInfoMapper blogInfoMapper;


    private final BlogTagMapper blogTagMapper;


    private final   CommentMapper commentMapper;

    @Override
    public List<SimpleBlogListVO> getNewBlog() {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        Page<BlogInfo> page = new Page<>(1, 5);
        blogInfoMapper.selectPage(page, new QueryWrapper<BlogInfo>()
                .lambda()
                .eq(BlogInfo::getShow, ShowEnum.SHOW.getStatus())
                .orderByDesc(BlogInfo::getCreateTime));
        for (BlogInfo blogInfo : page.getRecords()) {
            SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
            BeanUtils.copyProperties(blogInfo, simpleBlogListVO);
            simpleBlogListVOS.add(simpleBlogListVO);
        }
        return simpleBlogListVOS;
    }

    @Override
    public List<SimpleBlogListVO> getHotBlog() {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        Page<BlogInfo> page = new Page<>(1, 5);
        blogInfoMapper.selectPage(page, new QueryWrapper<BlogInfo>()
                .lambda()
                .eq(BlogInfo::getShow, ShowEnum.SHOW.getStatus())
                .orderByDesc(BlogInfo::getBlogViews));
        for (BlogInfo blogInfo : page.getRecords()) {
            SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
            BeanUtils.copyProperties(blogInfo, simpleBlogListVO);
            simpleBlogListVOS.add(simpleBlogListVO);
        }
        return simpleBlogListVOS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean clearBlogInfo(String blogId) {
        if (SqlHelper.retBool(blogInfoMapper.deleteById(blogId))) {
            QueryWrapper<BlogTag> tagRelationWrapper = new QueryWrapper<>();
            tagRelationWrapper.lambda().eq(BlogTag::getBlogId, blogId);
            blogTagMapper.delete(tagRelationWrapper);
            QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
            commentWrapper.lambda().eq(Comment::getBlogId, blogId);
            commentMapper.delete(commentWrapper);
            return true;
        }
        return false;
    }

    @Override
    public List<BlogDetailVO> getBlogs(PageDto pageDto) {

        return blogInfoMapper.getBlogDetail();
    }

    @Override
    public List<BlogInfo> searchBlog(String keyword) {
        return blogInfoMapper.searchBlog(keyword);
    }
}
