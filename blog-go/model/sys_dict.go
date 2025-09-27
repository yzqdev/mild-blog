package model

import (
	"time"

	"gorm.io/gorm"
)

type SysDictType struct {
	Id        string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	Name      string         `json:"name" gorm:"column:name;type:varchar(100)"`
	Code      string         `json:"code" gorm:"column:code;type:varchar(100)"`
	Sort      int            `json:"sort" gorm:"column:sort;default:0"`
	Remark    string         `json:"remark" gorm:"column:remark;type:varchar(500)"`
	Status    bool           `json:"status" gorm:"column:status;default:true"`
	CreatedAt time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (SysDictType) TableName() string {
	return "sys_dict_type"
}

func GetDictTypePage(page, pageSize int) (result []SysDictType, total int64) {
	db := GetDb()
	query := db.Model(&SysDictType{})
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func CreateDictType(dictType *SysDictType) bool {
	db := GetDb()
	result := db.Create(dictType)
	return result.RowsAffected > 0
}

func DeleteDictType(id string) bool {
	db := GetDb()
	result := db.Where("id = ?", id).Delete(&SysDictType{})
	return result.RowsAffected > 0
}

type SysDictData struct {
	Id        string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	TypeId    string         `json:"typeId" gorm:"column:type_id;type:uuid"`
	Value     string         `json:"value" gorm:"column:value;type:varchar(200)"`
	Code      string         `json:"code" gorm:"column:code;type:varchar(100)"`
	Sort      int            `json:"sort" gorm:"column:sort;default:0"`
	Remark    string         `json:"remark" gorm:"column:remark;type:varchar(500)"`
	Status    bool           `json:"status" gorm:"column:status;default:true"`
	CreatedAt time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (SysDictData) TableName() string {
	return "sys_dict_data"
}

func GetDictDataByTypeId(typeId string, page, pageSize int) (result []SysDictData, total int64) {
	db := GetDb()
	query := db.Model(&SysDictData{}).Where("type_id = ?", typeId)
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func CreateDictData(dictData *SysDictData) bool {
	db := GetDb()
	result := db.Create(dictData)
	return result.RowsAffected > 0
}

func DeleteDictDataByTypeId(typeId string) bool {
	db := GetDb()
	result := db.Where("type_id = ?", typeId).Delete(&SysDictData{})
	return result.RowsAffected >= 0
}
