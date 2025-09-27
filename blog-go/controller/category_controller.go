package controller

import (
	"net/http"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func CategoryList(c *gin.Context) {
	categories := model.GetCategories()
	util.Success(c, categories)
}

func GetCategoryList(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	categories, total := model.GetCategoryPage(page, limit)
	util.PageSuccess(c, categories, total, page, limit)
}

func UpdateCategory(c *gin.Context) {
	category := &model.Category{}
	if err := c.ShouldBind(category); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	model.UpdateCategory(category)
	util.Success(c, "成功")
}

func UpdateCategoryStatus(c *gin.Context) {
	category := &model.Category{}
	if err := c.ShouldBind(category); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	flag := model.UpdateCategory(category)
	if flag {
		util.Success(c, "成功")
	} else {
		util.Error(c, http.StatusInternalServerError, "更新失败")
	}
}

func ClearCategory(c *gin.Context) {
	id := c.Param("id")
	flag := model.DeleteCategory(id)
	if flag {
		util.Success(c, id)
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}

func AddCategory(c *gin.Context) {
	category := &model.Category{}
	if err := c.ShouldBind(category); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	category.CategoryId = util.GenerateUUID()
	category.Show = true
	category.CreatedAt = time.Now()
	flag := model.CreateCategory(category)
	if flag {
		util.Success(c, category)
	} else {
		util.Error(c, http.StatusInternalServerError, "添加失败")
	}
}
