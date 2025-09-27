package model

import (
	"time"
)

type Img struct {
	Id            string    `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	ImgName       string    `json:"imgName" gorm:"column:img_name;type:varchar(200)"`
	ImgPath       string    `json:"imgPath" gorm:"column:img_path;type:varchar(500)"`
	ImgSize       int64     `json:"imgSize" gorm:"column:img_size"`
	ImgUrl        string    `json:"imgUrl" gorm:"column:img_url;type:varchar(500)"`
	ThumbnailPath string    `json:"thumbnailPath" gorm:"column:thumbnail_path;type:varchar(500)"`
	ImgType       string    `json:"imgType" gorm:"column:img_type;type:varchar(50)"`
	MediaType     string    `json:"mediaType" gorm:"column:media_type;type:varchar(100)"`
	Md5           string    `json:"md5" gorm:"column:md5;type:varchar(100)"`
	UploadTime    time.Time `json:"uploadTime" gorm:"column:upload_time"`
}

func (Img) TableName() string {
	return "img"
}

func GetImgByID(id string) (result Img) {
	db := GetDb()
	db.Where("id = ?", id).First(&result)
	return
}

func GetImgPage(page, pageSize int) (result []Img, total int64) {
	db := GetDb()
	query := db.Model(&Img{})
	query.Count(&total)
	query.Order("upload_time DESC").Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func CreateImg(img *Img) bool {
	db := GetDb()
	result := db.Create(img)
	return result.RowsAffected > 0
}

func DeleteImg(id string) bool {
	db := GetDb()
	result := db.Where("id = ?", id).Delete(&Img{})
	return result.RowsAffected > 0
}
