package controller

import (
	"ginblog/model"
	"ginblog/utils"
	"github.com/gin-gonic/gin"
)

func GetArticleList(c *gin.Context) {

	articles := model.QueryArticleList()
	utils.JSON(c, 200, "chengg", articles)
}


func PartCount(c *gin.Context) {
	count := model.GetAllPartCount()
	utils.JSON(c, 200, "success", count)
}
