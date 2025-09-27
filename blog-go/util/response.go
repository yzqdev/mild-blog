package util

import (
	"time"

	"github.com/gin-gonic/gin"
)

type ApiResponse struct {
	ResultCode int         `json:"resultCode"`
	Message    string      `json:"message"`
	Data       interface{} `json:"data"`
	Success    *bool       `json:"success"`
	Timestamp  int64       `json:"timestamp"`
}

type PageResult struct {
	List      interface{} `json:"list"`
	Count     int64       `json:"count"`
	Page      int         `json:"page"`
	Limit     int         `json:"limit"`
	TotalPage int         `json:"totalPage"`
}

func JSON(c *gin.Context, code int, msg string, data ...interface{}) {
	responseData := interface{}(nil)
	if len(data) > 0 {
		responseData = data[0]
	}
	success := code == 200
	c.JSON(code, ApiResponse{
		ResultCode: code,
		Message:    msg,
		Data:       responseData,
		Success:    &success,
		Timestamp:  time.Now().UnixMilli(),
	})
}

func Success(c *gin.Context, data interface{}) {
	success := true
	c.JSON(200, ApiResponse{
		ResultCode: 200,
		Message:    "success",
		Data:       data,
		Success:    &success,
		Timestamp:  time.Now().UnixMilli(),
	})
}

func Error(c *gin.Context, code int, msg string) {
	success := false
	c.JSON(code, ApiResponse{
		ResultCode: code,
		Message:    msg,
		Success:    &success,
		Timestamp:  time.Now().UnixMilli(),
	})
}

func PageSuccess(c *gin.Context, list interface{}, total int64, page, limit int) {
	totalPage := int(total) / limit
	if int(total)%limit > 0 {
		totalPage++
	}
	success := true
	c.JSON(200, ApiResponse{
		ResultCode: 200,
		Message:    "success",
		Data: PageResult{
			List:      list,
			Count:     total,
			Page:      page,
			Limit:     limit,
			TotalPage: totalPage,
		},
		Success:   &success,
		Timestamp: time.Now().UnixMilli(),
	})
}
