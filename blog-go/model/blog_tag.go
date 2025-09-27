package model

import (
	"time"
)

type BlogTag struct {
	RelationId string    `json:"relationId" gorm:"column:relation_id;type:uuid;primary_key;default:gen_random_uuid()"`
	BlogId     string    `json:"blogId" gorm:"column:blog_id;type:uuid"`
	TagId      string    `json:"tagId" gorm:"column:tag_id;type:uuid"`
	CreateTime time.Time `json:"createTime" gorm:"column:create_time"`
}

func (BlogTag) TableName() string {
	return "blog_tag"
}

func GetTagsByBlogId(blogId string) (result []BlogTag) {
	db := GetDb()
	db.Where("blog_id = ?", blogId).Find(&result)
	return
}

func GetBlogsByTagId(tagId string) (result []BlogTag) {
	db := GetDb()
	db.Where("tag_id = ?", tagId).Find(&result)
	return
}

func CreateBlogTag(blogTag *BlogTag) bool {
	db := GetDb()
	result := db.Create(blogTag)
	return result.RowsAffected > 0
}

func DeleteBlogTagsByBlogId(blogId string) bool {
	db := GetDb()
	result := db.Where("blog_id = ?", blogId).Delete(&BlogTag{})
	return result.RowsAffected > 0
}

func DeleteBlogTagsByTagId(tagId string) bool {
	db := GetDb()
	result := db.Where("tag_id = ?", tagId).Delete(&BlogTag{})
	return result.RowsAffected > 0
}
