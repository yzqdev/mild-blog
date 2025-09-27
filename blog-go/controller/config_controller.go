package controller

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func GetBlogConfig(c *gin.Context) {
	configs := model.GetAllConfigs()
	util.Success(c, configs)
}

func UpdateBlogConfig(c *gin.Context) {
	config := &model.BlogConfig{}
	if err := c.ShouldBindJSON(config); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	config.UpdatedAt = time.Now()
	flag := model.UpdateConfig(config)
	if flag {
		util.Success(c, "成功")
	} else {
		util.Error(c, http.StatusInternalServerError, "更新失败")
	}
}

func AddBlogConfig(c *gin.Context) {
	config := &model.BlogConfig{}
	if err := c.ShouldBindJSON(config); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	config.Id = util.GenerateUUID()
	config.CreatedAt = time.Now()
	config.UpdatedAt = time.Now()
	flag := model.CreateConfig(config)
	if flag {
		util.Success(c, "成功")
	} else {
		util.Error(c, http.StatusInternalServerError, "添加失败")
	}
}

func DeleteBlogConfig(c *gin.Context) {
	id := c.Param("id")
	flag := model.DeleteConfig(id)
	if flag {
		util.Success(c, "成功")
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}

func GetHomeConfigs(c *gin.Context) {
	configs := model.GetAllConfigsAsMap()
	util.Success(c, configs)
}
