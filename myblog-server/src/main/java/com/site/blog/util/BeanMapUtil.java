package com.site.blog.util;

import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.vo.BlogDetailVO;
import org.springframework.beans.BeanUtils;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/8/26 9:43
 * @modified By:
 */
public class BeanMapUtil {
    public static BlogDetailVO copyBlog(BlogInfo blogInfo){
        BlogDetailVO blogDetailVO=new BlogDetailVO();
        BeanUtils.copyProperties(blogInfo,blogDetailVO);
        return  blogDetailVO;
    }
}