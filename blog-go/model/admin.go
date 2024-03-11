package model

import (
	"github.com/gookit/color"
	"gorm.io/gorm"
)

type AdminUser struct {
	gorm.Model
	Username string `json:"username"`
	Password string `json:"password"`
	Salt     string `json:"salt"`
	Uid      string `json:"uid"`
}

func QueryByUsername(username string) (result AdminUser) {

	db := GetDb()
	db.Where("username = ?", username).First(&result)
	color.Red.Println(result)
	return

}
func SaveUser(data *AdminUser) {
	db := GetDb()
	db.Create(data)

}
func GetUserCheck(username string) bool {
	db := GetDb()
	obj := db.Model(&AdminUser{}).
		Where("username = ?  ", username).
		Where("status in (?)", []string{"1", "2"})
	var count int64
	obj.Count(&count)

	if count > 0 {
		return true
	} else {
		return false
	}
}
func QueryCheckToken() {

}
