package util

import (
	"github.com/gin-gonic/gin"
	"github.com/gookit/color"
)

type ApiResponse struct {
	Data    interface{} `json:"data"`
	Msg     string      `json:"msg"`
	Success bool        `json:"success"`
	Code    int         `json:"code"`
}

// JSON 标准返回结果数据结构封装。
// 返回固定数据结构的JSON:
// err:  错误码(0:成功, 1:失败, >1:错误码);
// msg:  请求结果信息;
// data: 请求结果,根据不同接口返回结果的数据结构不同;
func JSON(c *gin.Context, code int, msg string, data ...interface{}) {
	responseData := interface{}(nil)
	if len(data) > 0 {
		responseData = data[0]
	}
	var success = false
	if code == 200 {
		success = true
	}
	color.Red.Println(c)
	c.JSON(code, ApiResponse{
		Data:    responseData,
		Msg:     msg,
		Success: success,
		Code:    code,
	})

}
