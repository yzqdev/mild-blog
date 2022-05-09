package model

import (
	"github.com/gookit/color"
)

type AdminUser struct {
	Username string `json:"username"`
	Password string `json:"password"`
	Salt     string `json:"salt"`
}

func QueryByUsername(username string) (result AdminUser) {

	db := GetDb()
	db.Table("admin_user").Where("username = ?", username).First(&result)
	color.Red.Println(result)
	return

}
func SaveUser(data map[string]interface{}) {
	db := GetDb()
	db.Table("admin_user").Create(data)

}
func GetUserCheck(username string) bool {
	db := GetDb()
	obj := db.Table("admin_user").
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
