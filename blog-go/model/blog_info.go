package model

import (
	"time"

	"gorm.io/gorm"
)

type BlogInfo struct {
	BlogId       string         `json:"blogId" gorm:"column:blog_id;type:uuid;primary_key;default:gen_random_uuid()"`
	BlogTitle    string         `json:"blogTitle" gorm:"column:blog_title;type:varchar(200)"`
	SubUrl       string         `json:"subUrl" gorm:"column:sub_url;type:varchar(200)"`
	Preface      string         `json:"preface" gorm:"column:preface;type:text"`
	BlogContent  string         `json:"blogContent" gorm:"column:blog_content;type:text"`
	BlogViews    int64          `json:"blogViews" gorm:"column:blog_views;default:0"`
	EnableComment bool          `json:"enableComment" gorm:"column:enable_comment;default:true"`
	Show         bool           `json:"show" gorm:"column:show;default:true"`
	Deleted      bool           `json:"deleted" gorm:"column:is_deleted;default:false"`
	CreatedAt    time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt    time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt    gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (BlogInfo) TableName() string {
	return "blog_info"
}

func QueryBlogList(page, pageSize int, deleted bool) (result []BlogInfo, total int64) {
	db := GetDb()
	query := db.Model(&BlogInfo{}).Where("is_deleted = ?", deleted).Order("update_time DESC")
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func QueryBlogByID(id string) (result BlogInfo) {
	db := GetDb()
	db.Where("blog_id = ?", id).First(&result)
	return
}

func CreateBlog(blog *BlogInfo) bool {
	db := GetDb()
	result := db.Create(blog)
	return result.RowsAffected > 0
}

func UpdateBlog(blog *BlogInfo) bool {
	db := GetDb()
	result := db.Save(blog)
	return result.RowsAffected > 0
}

func DeleteBlog(id string) bool {
	db := GetDb()
	result := db.Where("blog_id = ?", id).Delete(&BlogInfo{})
	return result.RowsAffected > 0
}

func GetNewBlogs(limit int) (result []BlogInfo) {
	db := GetDb()
	db.Where("is_deleted = ? AND show = ?", false, true).Order("create_time DESC").Limit(limit).Find(&result)
	return
}

func GetHotBlogs(limit int) (result []BlogInfo) {
	db := GetDb()
	db.Where("is_deleted = ? AND show = ?", false, true).Order("blog_views DESC").Limit(limit).Find(&result)
	return
}

func GetViewsSum() (sum int64) {
	db := GetDb()
	db.Model(&BlogInfo{}).Select("COALESCE(SUM(blog_views), 0)").Scan(&sum)
	return
}

func SearchBlogs(keyword string, page, pageSize int) (result []BlogInfo, total int64) {
	db := GetDb()
	query := db.Model(&BlogInfo{}).Where("is_deleted = false AND show = true AND (blog_title LIKE ? OR blog_content LIKE ?)", "%"+keyword+"%", "%"+keyword+"%")
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func IncrementViews(blogId string) {
	db := GetDb()
	db.Model(&BlogInfo{}).Where("blog_id = ?", blogId).UpdateColumn("blog_views", gorm.Expr("blog_views + 1"))
}
