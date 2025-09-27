package controller

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func GetSysLogs(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	logs, total := model.GetSysOpLogPage(page, limit)
	util.PageSuccess(c, logs, total, page, limit)
}

func ClearSysLogs(c *gin.Context) {
	flag := model.ClearAllSysOpLogs()
	if flag {
		util.Success(c, "")
	} else {
		util.Error(c, http.StatusInternalServerError, "清除失败")
	}
}
