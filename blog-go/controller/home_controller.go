package controller

import (
	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
	"net/http"
)

func GetArticleList(c *gin.Context) {

	articles := model.QueryArticleList()
	util.JSON(c, http.StatusOK, "成功", articles)
}
