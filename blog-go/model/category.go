package model

import (
	"time"

	"gorm.io/gorm"
)

type Category struct {
	CategoryId   string         `json:"categoryId" gorm:"column:category_id;type:uuid;primary_key;default:gen_random_uuid()"`
	CategoryName string         `json:"categoryName" gorm:"column:category_name;type:varchar(50)"`
	CategoryIcon string         `json:"categoryIcon" gorm:"column:category_icon;type:varchar(200)"`
	CategoryRank int            `json:"categoryRank" gorm:"column:category_rank;default:0"`
	Show         bool           `json:"show" gorm:"column:show;default:true"`
	CreatedAt    time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt    time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt    gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (Category) TableName() string {
	return "category"
}

func GetCategories() (result []Category) {
	db := GetDb()
	db.Where("show = ?", true).Order("create_time DESC").Find(&result)
	return
}

func GetCategoryByID(id string) (result Category) {
	db := GetDb()
	db.Where("category_id = ?", id).First(&result)
	return
}

func CreateCategory(cat *Category) bool {
	db := GetDb()
	result := db.Create(cat)
	return result.RowsAffected > 0
}

func UpdateCategory(cat *Category) bool {
	db := GetDb()
	result := db.Save(cat)
	return result.RowsAffected > 0
}

func DeleteCategory(id string) bool {
	db := GetDb()
	result := db.Where("category_id = ?", id).Delete(&Category{})
	return result.RowsAffected > 0
}

func GetCategoryPage(page, pageSize int) (result []Category, total int64) {
	db := GetDb()
	query := db.Model(&Category{}).Where("category_id != ?", "1").Order("category_rank ASC")
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}
