package model

import (
	"time"

	"gorm.io/gorm"
)

type Link struct {
	LinkId        string         `json:"linkId" gorm:"column:link_id;type:uuid;primary_key;default:gen_random_uuid()"`
	LinkType      int            `json:"linkType" gorm:"column:link_type;default:0"`
	LinkName      string         `json:"linkName" gorm:"column:link_name;type:varchar(100)"`
	LinkUrl       string         `json:"linkUrl" gorm:"column:link_url;type:varchar(200)"`
	LinkDescription string       `json:"linkDescription" gorm:"column:link_description;type:varchar(500)"`
	LinkRank      int            `json:"linkRank" gorm:"column:link_rank;default:0"`
	Show          bool           `json:"show" gorm:"column:show;default:true"`
	CreatedAt     time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt     time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt     gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (Link) TableName() string {
	return "link"
}

func GetLinksByType(linkType int) (result []Link) {
	db := GetDb()
	db.Where("link_type = ? AND show = ?", linkType, true).Find(&result)
	return
}

func GetAllLinks(page, pageSize int) (result []Link, total int64) {
	db := GetDb()
	query := db.Model(&Link{}).Order("link_rank ASC, create_time ASC")
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func GetLinkByID(id string) (result Link) {
	db := GetDb()
	db.Where("link_id = ?", id).First(&result)
	return
}

func CreateLink(link *Link) bool {
	db := GetDb()
	result := db.Create(link)
	return result.RowsAffected > 0
}

func UpdateLink(link *Link) bool {
	db := GetDb()
	result := db.Save(link)
	return result.RowsAffected > 0
}

func DeleteLink(id string) bool {
	db := GetDb()
	result := db.Where("link_id = ?", id).Delete(&Link{})
	return result.RowsAffected > 0
}
