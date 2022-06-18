package com.site.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.site.blog.model.dto.PageDto;
import com.site.blog.model.entity.BlogInfo;
import com.site.blog.model.vo.BlogDetailVO;
import com.site.blog.model.vo.SimpleBlogListVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 博客信息表 服务类
 * </p>
 *
 */
public interface BlogInfoService extends IService<BlogInfo> {

    /**
     * 返回最新的五条文章列表
     *
     * @param
     * @return java.util.List<com.site.blog.pojo.vo.SimpleBlogListVO>
     * @date 2019/9/4 9:04
     */
    List<SimpleBlogListVO> getNewBlog();

    /**
     * 返回点击量最多的五条文章
     *
     * @param
     * @return java.util.List<com.site.blog.pojo.vo.SimpleBlogListVO>
     * @date 2019/9/4 9:15
     */
    List<SimpleBlogListVO> getHotBlog();

    /**
     * 清除文章
     *
     * @param blogId
     * @return boolean
     * @date 2020/1/29 21:54
     */
   Boolean clearBlogInfo(String blogId);

    /**
     * 得到观点和
     *
     * @return {@link Integer}
     */

    Integer getViewsSum();

}
