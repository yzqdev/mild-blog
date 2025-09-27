package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func GetDictList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	dictTypes, total := model.GetDictTypePage(page, limit)
	util.PageSuccess(c, dictTypes, total, page, limit)
}

func AddDict(c *gin.Context) {
	dictType := &model.SysDictType{}
	if err := c.ShouldBindJSON(dictType); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	dictType.Id = util.GenerateUUID()
	dictType.Status = true
	flag := model.CreateDictType(dictType)
	if flag {
		util.Success(c, dictType)
	} else {
		util.Error(c, http.StatusInternalServerError, "添加失败")
	}
}

func ClearDictType(c *gin.Context) {
	dictType := c.Param("dictType")
	model.DeleteDictDataByTypeId(dictType)
	flag := model.DeleteDictType(dictType)
	util.Success(c, flag)
}

func AddDictData(c *gin.Context) {
	dictData := &model.SysDictData{}
	if err := c.ShouldBindJSON(dictData); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	dictData.Id = util.GenerateUUID()
	dictData.Status = true
	dictData.CreatedAt = time.Now()
	dictData.UpdatedAt = time.Now()
	flag := model.CreateDictData(dictData)
	if flag {
		util.Success(c, dictData)
	} else {
		util.Error(c, http.StatusInternalServerError, "添加失败")
	}
}

func GetDictDataList(c *gin.Context) {
	dictType := c.Param("dictType")
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	dictData, total := model.GetDictDataByTypeId(dictType, page, limit)
	util.PageSuccess(c, dictData, total, page, limit)
}
