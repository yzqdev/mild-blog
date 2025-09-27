package model

import (
	"time"

	"gorm.io/gorm"
)

type EmailConfig struct {
	Id        string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	Email     string         `json:"email" gorm:"column:email;type:varchar(100)"`
	EmailKey  string         `json:"emailKey" gorm:"column:email_key;type:varchar(200)"`
	EmailUrl  string         `json:"emailUrl" gorm:"column:email_url;type:varchar(200)"`
	Port      string         `json:"port" gorm:"column:port;type:varchar(10)"`
	EmailName string         `json:"emailName" gorm:"column:email_name;type:varchar(100)"`
	Enable    bool           `json:"enable" gorm:"column:enable;default:true"`
	CreatedAt time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (EmailConfig) TableName() string {
	return "email_config"
}

func GetDefaultEmailConfig() (result EmailConfig) {
	db := GetDb()
	db.Where("id = ?", "1").First(&result)
	return
}
