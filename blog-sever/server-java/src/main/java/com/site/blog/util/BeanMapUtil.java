package com.site.blog.util;

import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.entity.Comment;
import com.site.blog.model.vo.BlogDetailVO;
import com.site.blog.model.vo.CommentVo;
import org.springframework.beans.BeanUtils;


public class BeanMapUtil {
    public static BlogDetailVO copyBlog(BlogInfo blogInfo){
        BlogDetailVO blogDetailVO=new BlogDetailVO();
        BeanUtils.copyProperties(blogInfo,blogDetailVO);
        return  blogDetailVO;
    }
    public static CommentVo copyComment(Comment comment){
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        return  commentVo;
    }
}
