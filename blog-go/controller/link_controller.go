package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func LinkTypeList(c *gin.Context) {
	links := []gin.H{
		{"linkType": 0, "linkName": "友情链接"},
		{"linkType": 1, "linkName": "推荐网站"},
		{"linkType": 2, "linkName": "个人网站"},
	}
	util.Success(c, links)
}

func GetLinkList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	links, total := model.GetAllLinks(page, limit)
	util.PageSuccess(c, links, total, page, limit)
}

func UpdateLinkStatus(c *gin.Context) {
	link := &model.Link{}
	if err := c.ShouldBindJSON(link); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	flag := model.UpdateLink(link)
	if flag {
		util.Success(c, link)
	} else {
		util.Error(c, http.StatusInternalServerError, "更新失败")
	}
}

func ClearLink(c *gin.Context) {
	id := c.Param("id")
	flag := model.DeleteLink(id)
	if flag {
		util.Success(c, id)
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}

func EditLink(c *gin.Context) {
	link := &model.Link{}
	if err := c.ShouldBind(link); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	link.CreatedAt = time.Now()
	if link.LinkId != "" {
		model.UpdateLink(link)
		util.Success(c, link.LinkId)
	} else {
		link.LinkId = util.GenerateUUID()
		flag := model.CreateLink(link)
		if flag {
			util.Success(c, link.LinkId)
		} else {
			util.Error(c, http.StatusInternalServerError, "添加失败")
		}
	}
}

func GetLinks(c *gin.Context) {
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
