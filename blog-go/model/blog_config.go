package model

import (
	"time"

	"gorm.io/gorm"
)

type BlogConfig struct {
	Id          string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	ConfigCode  string         `json:"configCode" gorm:"column:config_code;type:varchar(100)"`
	ConfigName  string         `json:"configName" gorm:"column:config_name;type:varchar(100)"`
	ConfigValue string         `json:"configValue" gorm:"column:config_value;type:text"`
	CreatedAt   time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt   time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt   gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (BlogConfig) TableName() string {
	return "blog_config"
}

func GetAllConfigs() (result []BlogConfig) {
	db := GetDb()
	db.Find(&result)
	return
}

func GetConfigByCode(code string) (result BlogConfig) {
	db := GetDb()
	db.Where("config_code = ?", code).First(&result)
	return
}

func GetConfigByID(id string) (result BlogConfig) {
	db := GetDb()
	db.Where("id = ?", id).First(&result)
	return
}

func CreateConfig(config *BlogConfig) bool {
	db := GetDb()
	result := db.Create(config)
	return result.RowsAffected > 0
}

func UpdateConfig(config *BlogConfig) bool {
	db := GetDb()
	result := db.Save(config)
	return result.RowsAffected > 0
}

func DeleteConfig(id string) bool {
	db := GetDb()
	result := db.Where("id = ?", id).Delete(&BlogConfig{})
	return result.RowsAffected > 0
}

func GetAllConfigsAsMap() (result map[string]string) {
	configs := GetAllConfigs()
	result = make(map[string]string)
	for _, c := range configs {
		result[c.ConfigCode] = c.ConfigValue
	}
	return
}
