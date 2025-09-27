package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func GetBlogById(c *gin.Context) {
	id := c.Param("id")
	blog := model.QueryBlogByID(id)
	if blog.BlogId == "" {
		util.Error(c, http.StatusNotFound, "博客不存在")
		return
	}
	util.Success(c, blog)
}

func SaveBlog(c *gin.Context) {
	blogInfoDo := &struct {
		BlogId        string   `json:"blogId"`
		BlogTitle     string   `json:"blogTitle"`
		SubUrl        string   `json:"subUrl"`
		Preface       string   `json:"preface"`
		BlogContent   string   `json:"blogContent"`
		BlogCategoryId string  `json:"blogCategoryId"`
		BlogTagIds    []string `json:"blogTagIds"`
		Show          *bool    `json:"show"`
		EnableComment *bool    `json:"enableComment"`
	}{}

	if err := c.ShouldBindJSON(blogInfoDo); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}

	blog := model.BlogInfo{
		BlogId:       blogInfoDo.BlogId,
		BlogTitle:    blogInfoDo.BlogTitle,
		SubUrl:       blogInfoDo.SubUrl,
		Preface:      blogInfoDo.Preface,
		BlogContent:  blogInfoDo.BlogContent,
		BlogViews:    0,
		EnableComment: true,
		Show:         true,
		Deleted:      false,
		CreatedAt:    time.Now(),
		UpdatedAt:    time.Now(),
	}

	if blogInfoDo.Show != nil {
		blog.Show = *blogInfoDo.Show
	}
	if blogInfoDo.EnableComment != nil {
		blog.EnableComment = *blogInfoDo.EnableComment
	}

	if blog.BlogId == "" {
		blog.BlogId = util.GenerateUUID()
		model.CreateBlog(&blog)
	} else {
		model.UpdateBlog(&blog)
	}

	// Save category
	categoryId := "1"
	if blogInfoDo.BlogCategoryId != "" {
		categoryId = blogInfoDo.BlogCategoryId
	}
	model.DeleteBlogCategoryByBlogId(blog.BlogId)
	model.CreateBlogCategory(&model.BlogCategory{
		BlogId:     blog.BlogId,
		CategoryId: categoryId,
		CreateTime: time.Now(),
	})

	// Save tags
	model.DeleteBlogTagsByBlogId(blog.BlogId)
	for _, tagId := range blogInfoDo.BlogTagIds {
		model.CreateBlogTag(&model.BlogTag{
			BlogId:     blog.BlogId,
			TagId:      tagId,
			CreateTime: time.Now(),
		})
	}

	util.Success(c, blog)
}

func GetBlogList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))
	deletedStr := c.Query("deleted")
	deleted := deletedStr == "true"

	blogs, total := model.QueryBlogList(page, limit, deleted)
	util.PageSuccess(c, blogs, total, page, limit)
}

func UpdateBlogShow(c *gin.Context) {
	id := c.Param("id")
	showStr := c.Query("show")
	show := showStr == "true"

	blog := model.QueryBlogByID(id)
	blog.Show = show
	model.UpdateBlog(&blog)
	util.Success(c, "成功")
}

func DeleteBlog(c *gin.Context) {
	id := c.Param("id")
	restoreStr := c.Query("restore")
	restore := restoreStr == "true"

	blog := model.QueryBlogByID(id)
	blog.Show = false
	blog.UpdatedAt = time.Now()
	if restore {
		blog.Deleted = false
	} else {
		blog.Deleted = true
	}
	model.UpdateBlog(&blog)
	util.Success(c, blog)
}

func ClearBlog(c *gin.Context) {
	id := c.Param("id")
	model.DeleteBlogTagsByBlogId(id)
	model.DeleteBlogCategoryByBlogId(id)
	flag := model.DeleteBlog(id)
	if flag {
		util.Success(c, id)
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}

func RestoreBlog(c *gin.Context) {
	blogId := c.PostForm("blogId")
	blog := model.QueryBlogByID(blogId)
	blog.Show = true
	blog.UpdatedAt = time.Now()
	model.UpdateBlog(&blog)
	util.Success(c, "成功")
}
