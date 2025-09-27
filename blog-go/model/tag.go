package model

import (
	"time"

	"gorm.io/gorm"
)

type Tag struct {
	TagId     string         `json:"tagId" gorm:"column:tag_id;type:uuid;primary_key;default:gen_random_uuid()"`
	TagName   string         `json:"tagName" gorm:"column:tag_name;type:varchar(50)"`
	Show      bool           `json:"show" gorm:"column:show;default:true"`
	CreatedAt time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (Tag) TableName() string {
	return "tag"
}

func GetTags() (result []Tag) {
	db := GetDb()
	db.Where("show = ?", true).Find(&result)
	return
}

func GetTagByID(id string) (result Tag) {
	db := GetDb()
	db.Where("tag_id = ?", id).First(&result)
	return
}

func CreateTag(tag *Tag) bool {
	db := GetDb()
	result := db.Create(tag)
	return result.RowsAffected > 0
}

func UpdateTag(tag *Tag) bool {
	db := GetDb()
	result := db.Save(tag)
	return result.RowsAffected > 0
}

func DeleteTag(id string) bool {
	db := GetDb()
	result := db.Where("tag_id = ?", id).Delete(&Tag{})
	return result.RowsAffected > 0
}

func GetTagPage(page, pageSize int, show *bool) (result []Tag, total int64) {
	db := GetDb()
	query := db.Model(&Tag{})
	if show != nil {
		query = query.Where("show = ?", *show)
	}
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

type BlogTagCount struct {
	TagId    string `json:"tagId" gorm:"column:tag_id"`
	TagName  string `json:"tagName" gorm:"column:tag_name"`
	TagCount int64  `json:"tagCount" gorm:"column:tag_count"`
}

func GetBlogTagCountForIndex() (result []BlogTagCount) {
	db := GetDb()
	db.Raw(`
		SELECT t.tag_id, t.tag_name, COUNT(bt.blog_id) as tag_count
		FROM tag t
		LEFT JOIN blog_tag bt ON t.tag_id = bt.tag_id
		WHERE t.show = true
		GROUP BY t.tag_id, t.tag_name
		ORDER BY tag_count DESC
		LIMIT 10
	`).Scan(&result)
	return
}
