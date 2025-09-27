package model

import (
	"time"

	"gorm.io/gorm"
)

type AdminUser struct {
	ID        string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	Username  string         `json:"username" gorm:"column:username;type:varchar(50);uniqueIndex"`
	Password  string         `json:"password" gorm:"column:password;type:varchar(200)"`
	Nickname  string         `json:"nickname" gorm:"column:nickname;type:varchar(50)"`
	Locked    bool           `json:"locked" gorm:"column:locked;default:false"`
	Role      int            `json:"role" gorm:"column:role;default:0"`
	Avatar    string         `json:"avatar" gorm:"column:avatar;type:varchar(200)"`
	Email     string         `json:"email" gorm:"column:email;type:varchar(100)"`
	Uuid      string         `json:"uuid" gorm:"column:uuid;type:varchar(100)"`
	CreatedAt time.Time      `json:"createdAt" gorm:"column:create_time"`
	UpdatedAt time.Time      `json:"updatedAt" gorm:"column:update_time"`
	DeletedAt gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (AdminUser) TableName() string {
	return "admin_user"
}

func QueryByUsername(username string) (result AdminUser) {
	db := GetDb()
	db.Where("username = ?", username).First(&result)
	return
}

func QueryUserByID(id string) (result AdminUser) {
	db := GetDb()
	db.Where("id = ?", id).First(&result)
	return
}

func SaveUser(data *AdminUser) {
	db := GetDb()
	db.Create(data)
}

func GetUserCheck(username string) bool {
	db := GetDb()
	var count int64
	db.Model(&AdminUser{}).Where("username = ?", username).Count(&count)
	return count > 0
}

func GetAllUsers() (result []AdminUser) {
	db := GetDb()
	db.Find(&result)
	return
}

func DeleteUser(id string) bool {
	db := GetDb()
	result := db.Where("id = ?", id).Delete(&AdminUser{})
	return result.RowsAffected > 0
}

func UpdateUser(user *AdminUser) bool {
	db := GetDb()
	result := db.Save(user)
	return result.RowsAffected > 0
}
