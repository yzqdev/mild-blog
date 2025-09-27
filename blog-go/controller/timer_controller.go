package controller

import (
	"net/http"
	"strconv"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func GetTimers(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	timers, total := model.GetTimerPage(page, limit)
	util.PageSuccess(c, timers, total, page, limit)
}

func StartTimer(c *gin.Context) {
	id := c.Param("id")
	timer := model.GetTimerByID(id)
	if timer.Id == "" {
		util.Error(c, http.StatusNotFound, "定时任务不存在")
		return
	}
	// TODO: Implement cron job execution
	util.Success(c, "启动成功")
}
