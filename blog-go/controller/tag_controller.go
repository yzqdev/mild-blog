package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func TagsList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	tags, total := model.GetTagPage(page, limit, nil)
	util.PageSuccess(c, tags, total, page, limit)
}

func GetTagList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	tags, total := model.GetTagPage(page, limit, nil)
	util.PageSuccess(c, tags, total, page, limit)
}

func UpdateTagStatus(c *gin.Context) {
	tag := &model.Tag{}
	if err := c.ShouldBind(tag); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	tag.UpdatedAt = time.Now()
	flag := model.UpdateTag(tag)
	if flag {
		util.Success(c, tag.TagName)
	} else {
		util.Error(c, http.StatusInternalServerError, "更新失败")
	}
}

func AddTag(c *gin.Context) {
	tag := &model.Tag{}
	if err := c.ShouldBind(tag); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	tag.TagId = util.GenerateUUID()
	tag.Show = true
	tag.CreatedAt = time.Now()
	tag.UpdatedAt = time.Now()
	flag := model.CreateTag(tag)
	if flag {
		util.Success(c, tag)
	} else {
		util.Error(c, http.StatusInternalServerError, "添加失败")
	}
}

func ClearTag(c *gin.Context) {
	id := c.Param("id")
	tag := model.GetTagByID(id)
	model.DeleteBlogTagsByTagId(id)
	flag := model.DeleteTag(id)
	if flag {
		util.Success(c, tag.TagName)
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}

func UpdateTag(c *gin.Context) {
	tag := &model.Tag{}
	if err := c.ShouldBindJSON(tag); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	tag.UpdatedAt = time.Now()
	model.UpdateTag(tag)
	util.Success(c, tag)
}
