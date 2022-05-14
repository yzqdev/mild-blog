package controller

import (
	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/utils"
)

func GetArticleList(c *gin.Context) {

	articles := model.QueryArticleList()
	utils.JSON(c, 200, "chengg", articles)
}

func PartCount(c *gin.Context) {
	count := model.GetAllPartCount()
	utils.JSON(c, 200, "success", count)
}
