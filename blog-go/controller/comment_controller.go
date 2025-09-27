package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func GetCommentList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	comments, total := model.GetAllComments(page, limit)
	util.PageSuccess(c, comments, total, page, limit)
}

func UpdateCommentStatus(c *gin.Context) {
	id := c.Param("id")
	showStr := c.Query("show")
	show := showStr == "true"

	comment := model.GetCommentByID(id)
	comment.CommentStatus = show
	flag := model.UpdateComment(&comment)
	if flag {
		util.Success(c, comment)
	} else {
		util.Error(c, http.StatusInternalServerError, "更新失败")
	}
}

func DeleteComment(c *gin.Context) {
	id := c.Param("id")
	flag := model.DeleteComment(id)
	if flag {
		util.Success(c, nil)
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}

func EditComment(c *gin.Context) {
	comment := &model.Comment{}
	if err := c.ShouldBind(comment); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	now := time.Now()
	comment.ReplyCreateTime = &now
	flag := model.UpdateComment(comment)
	if flag {
		util.Success(c, "成功")
	} else {
		util.Error(c, http.StatusInternalServerError, "编辑失败")
	}
}

func ListComments(c *gin.Context) {
	blogId := c.Query("blogId")
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	comments, total := model.GetCommentsByBlogId(blogId, page, limit)
	util.PageSuccess(c, comments, total, page, limit)
}

func SubmitComment(c *gin.Context) {
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
