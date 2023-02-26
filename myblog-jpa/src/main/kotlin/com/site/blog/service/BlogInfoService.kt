package com.site.blog.service


import com.site.blog.model.entity.BlogInfo
import com.site.blog.model.vo.SimpleBlogListVO

/**
 *
 *
 * 博客信息表 服务类
 *
 *
 */
interface BlogInfoService  {
    /**
     * 返回最新的五条文章列表
     *
     * @param
     * @return java.util.List<com.site.blog.pojo.vo.SimpleBlogListVO>
     * @date 2019/9/4 9:04
    </com.site.blog.pojo.vo.SimpleBlogListVO> */
    fun getNewBlog(): List<SimpleBlogListVO>

    /**
     * 返回点击量最多的五条文章
     *
     * @param
     * @return java.util.List<com.site.blog.pojo.vo.SimpleBlogListVO>
     * @date 2019/9/4 9:15
    </com.site.blog.pojo.vo.SimpleBlogListVO> */
    fun getHotBlog(): List<SimpleBlogListVO>

    /**
     * 清除文章
     *
     * @param blogId
     * @return boolean
     * @date 2020/1/29 21:54
     */
    fun clearBlogInfo(blogId: String): Boolean

    /**
     * 得到观点和
     *
     * @return [Integer]
     */
    fun getViewsSum(): Int
}