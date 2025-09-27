package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func HomeIndex(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("pageNum", "1"))
	pageSize, _ := strconv.Atoi(c.DefaultQuery("pageSize", "5"))

	blogs, total := model.QueryBlogList(page, pageSize, false)
	util.PageSuccess(c, blogs, total, page, pageSize)
}

func GetBlogsByTag(c *gin.Context) {
	tagId := c.Param("tagId")
	page, _ := strconv.Atoi(c.DefaultQuery("pageNum", "1"))
	pageSize, _ := strconv.Atoi(c.DefaultQuery("pageSize", "10"))

	blogTags := model.GetBlogsByTagId(tagId)
	var blogIds []string
	for _, bt := range blogTags {
		blogIds = append(blogIds, bt.BlogId)
	}

	if len(blogIds) == 0 {
		util.Success(c, []interface{}{})
		return
	}

	// Query blogs by IDs
	var blogs []model.BlogInfo
	db := model.GetDb()
	db.Where("blog_id IN ? AND is_deleted = false AND show = true", blogIds).Order("create_time DESC").Offset((page - 1) * pageSize).Limit(pageSize).Find(&blogs)
	util.Success(c, blogs)
}

func GetBlogsByCategory(c *gin.Context) {
	categoryId := c.Param("categoryId")
	page, _ := strconv.Atoi(c.DefaultQuery("pageNum", "1"))
	pageSize, _ := strconv.Atoi(c.DefaultQuery("pageSize", "10"))

	blogCategories := model.GetBlogsByCategoryId(categoryId)
	var blogIds []string
	for _, bc := range blogCategories {
		blogIds = append(blogIds, bc.BlogId)
	}

	if len(blogIds) == 0 {
		util.Success(c, []interface{}{})
		return
	}

	var blogs []model.BlogInfo
	db := model.GetDb()
	db.Where("blog_id IN ? AND is_deleted = false AND show = true", blogIds).Order("create_time DESC").Offset((page - 1) * pageSize).Limit(pageSize).Find(&blogs)
	util.Success(c, blogs)
}

func SearchBlogs(c *gin.Context) {
	keyword := c.Param("keyword")
	page, _ := strconv.Atoi(c.DefaultQuery("page", "0"))
	pageSize, _ := strconv.Atoi(c.DefaultQuery("pageSize", "10"))

	blogs, total := model.SearchBlogs(keyword, page+1, pageSize)
	util.PageSuccess(c, blogs, total, page, pageSize)
}

func GetTimeline(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("pageNum", "1"))
	pageSize, _ := strconv.Atoi(c.DefaultQuery("pageSize", "10"))

	blogs, total := model.QueryBlogList(page, pageSize, false)
	util.PageSuccess(c, blogs, total, page, pageSize)
}

func GetHomeTags(c *gin.Context) {
	tags := model.GetTags()
	util.Success(c, tags)
}

func GetHomeCategories(c *gin.Context) {
	categories := model.GetCategories()
	util.Success(c, categories)
}

func GetBlogDetail(c *gin.Context) {
	blogId := c.Param("blogId")
	blog := model.QueryBlogByID(blogId)
	if blog.BlogId == "" {
		util.Error(c, http.StatusNotFound, "文章不存在")
		return
	}

	// Increment views
	model.IncrementViews(blogId)
	blog.BlogViews++

	// Get tags
	blogTags := model.GetTagsByBlogId(blogId)
	var tagIds []string
	for _, bt := range blogTags {
		tagIds = append(tagIds, bt.TagId)
	}

	var tags []model.Tag
	if len(tagIds) > 0 {
		db := model.GetDb()
		db.Where("tag_id IN ?", tagIds).Find(&tags)
	}

	// Get comment count
	commentCount := model.CountCommentsByBlogId(blogId)

	result := gin.H{
		"blogDetailVO": blog,
		"tagList":      tags,
		"commentCount": commentCount,
	}
	util.Success(c, result)
}

func GetLinksHome(c *gin.Context) {
	favoriteLinks := model.GetLinksByType(0)
	recommendLinks := model.GetLinksByType(1)
	personalLinks := model.GetLinksByType(2)
	result := gin.H{
		"pageName":       "友情链接",
		"favoriteLinks":  favoriteLinks,
		"recommendLinks": recommendLinks,
		"personalLinks":  personalLinks,
	}
	util.Success(c, result)
}

func SubmitCommentHome(c *gin.Context) {
	comment := &model.Comment{}
	if err := c.ShouldBind(comment); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	comment.CommentStatus = true
	comment.Deleted = true
	comment.CommentCreateTime = time.Now()
	comment.CommentatorIp = c.ClientIP()
	flag := model.CreateComment(comment)
	if flag {
		util.Success(c, comment)
	} else {
		util.Error(c, http.StatusInternalServerError, "提交失败")
	}
}
