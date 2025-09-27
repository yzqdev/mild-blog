package model

import (
	"time"

	"gorm.io/gorm"
)

type Comment struct {
	ID              string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	BlogId          string         `json:"blogId" gorm:"column:blog_id;type:uuid"`
	Commentator     string         `json:"commentator" gorm:"column:commentator;type:varchar(50)"`
	Email           string         `json:"email" gorm:"column:email;type:varchar(100)"`
	WebsiteUrl      string         `json:"websiteUrl" gorm:"column:website_url;type:varchar(200)"`
	CommentBody     string         `json:"commentBody" gorm:"column:comment_body;type:text"`
	CommentStatus   bool           `json:"commentStatus" gorm:"column:comment_status;default:true"`
	CommentatorIp   string         `json:"commentatorIp" gorm:"column:commentator_ip;type:varchar(50)"`
	ReplyBody       string         `json:"replyBody" gorm:"column:reply_body;type:text"`
	ReplyCreateTime *time.Time     `json:"replyCreateTime" gorm:"column:reply_create_time"`
	CommentCreateTime time.Time    `json:"commentCreateTime" gorm:"column:comment_create_time"`
	Deleted         bool           `json:"deleted" gorm:"column:is_deleted;default:true"`
	UserAgent       string         `json:"userAgent" gorm:"column:user_agent;type:varchar(500)"`
	Os              string         `json:"os" gorm:"column:os;type:varchar(100)"`
	CreatedAt       time.Time      `json:"createdAt" gorm:"column:create_time"`
	UpdatedAt       time.Time      `json:"updatedAt" gorm:"column:update_time"`
	DeletedAt       gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (Comment) TableName() string {
	return "comment"
}

func GetCommentsByBlogId(blogId string, page, pageSize int) (result []Comment, total int64) {
	db := GetDb()
	query := db.Model(&Comment{}).Where("blog_id = ? AND comment_status = true AND is_deleted = true", blogId).Order("comment_create_time DESC")
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func GetAllComments(page, pageSize int) (result []Comment, total int64) {
	db := GetDb()
	query := db.Model(&Comment{})
	query.Count(&total)
	query.Order("comment_create_time DESC").Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func GetCommentByID(id string) (result Comment) {
	db := GetDb()
	db.Where("id = ?", id).First(&result)
	return
}

func CreateComment(comment *Comment) bool {
	db := GetDb()
	result := db.Create(comment)
	return result.RowsAffected > 0
}

func UpdateComment(comment *Comment) bool {
	db := GetDb()
	result := db.Save(comment)
	return result.RowsAffected > 0
}

func DeleteComment(id string) bool {
	db := GetDb()
	result := db.Where("id = ?", id).Delete(&Comment{})
	return result.RowsAffected > 0
}

func CountCommentsByBlogId(blogId string) (count int64) {
	db := GetDb()
	db.Model(&Comment{}).Where("blog_id = ? AND comment_status = true AND is_deleted = true", blogId).Count(&count)
	return
}

func CountAllComments() (count int64) {
	db := GetDb()
	db.Model(&Comment{}).Count(&count)
	return
}
