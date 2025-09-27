package model

import (
	"time"
)

type BlogCategory struct {
	RelationId string    `json:"relationId" gorm:"column:relation_id;type:uuid;primary_key;default:gen_random_uuid()"`
	BlogId     string    `json:"blogId" gorm:"column:blog_id;type:uuid"`
	CategoryId string    `json:"categoryId" gorm:"column:category_id;type:uuid"`
	CreateTime time.Time `json:"createTime" gorm:"column:create_time"`
}

func (BlogCategory) TableName() string {
	return "blog_category"
}

func GetCategoryByBlogId(blogId string) (result BlogCategory) {
	db := GetDb()
	db.Where("blog_id = ?", blogId).First(&result)
	return
}

func GetBlogsByCategoryId(categoryId string) (result []BlogCategory) {
	db := GetDb()
	db.Where("category_id = ?", categoryId).Find(&result)
	return
}

func CreateBlogCategory(blogCategory *BlogCategory) bool {
	db := GetDb()
	result := db.Create(blogCategory)
	return result.RowsAffected > 0
}

func DeleteBlogCategoryByBlogId(blogId string) bool {
	db := GetDb()
	result := db.Where("blog_id = ?", blogId).Delete(&BlogCategory{})
	return result.RowsAffected > 0
}

func UpdateBlogCategory(blogId, categoryId string) bool {
	db := GetDb()
	result := db.Model(&BlogCategory{}).Where("blog_id = ?", blogId).Update("category_id", categoryId)
	return result.RowsAffected > 0
}
