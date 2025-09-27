package model

import (
	"time"

	"gorm.io/gorm"
)

type SysOpLog struct {
	Id        string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	Name      string         `json:"name" gorm:"column:name;type:varchar(100)"`
	OpType    string         `json:"opType" gorm:"column:op_type;type:varchar(50)"`
	Message   string         `json:"message" gorm:"column:message;type:text"`
	Ip        string         `json:"ip" gorm:"column:ip;type:varchar(50)"`
	Location  string         `json:"location" gorm:"column:location;type:varchar(200)"`
	Browser   string         `json:"browser" gorm:"column:browser;type:varchar(100)"`
	Os        string         `json:"os" gorm:"column:os;type:varchar(100)"`
	Url       string         `json:"url" gorm:"column:url;type:varchar(500)"`
	ClassName string         `json:"className" gorm:"column:class_name;type:varchar(200)"`
	MethodName string        `json:"methodName" gorm:"column:method_name;type:varchar(100)"`
	ReqMethod string         `json:"reqMethod" gorm:"column:req_method;type:varchar(10)"`
	Param     string         `json:"param" gorm:"column:param;type:text"`
	Result    string         `json:"result" gorm:"column:result;type:text"`
	OpTime    time.Time      `json:"opTime" gorm:"column:op_time"`
	Account   string         `json:"account" gorm:"column:account;type:varchar(100)"`
	CreatedAt time.Time      `json:"createdAt" gorm:"column:create_time"`
	UpdatedAt time.Time      `json:"updatedAt" gorm:"column:update_time"`
	DeletedAt gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (SysOpLog) TableName() string {
	return "sys_op_log"
}

func GetSysOpLogPage(page, pageSize int) (result []SysOpLog, total int64) {
	db := GetDb()
	query := db.Model(&SysOpLog{})
	query.Count(&total)
	query.Order("op_time DESC").Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}

func ClearAllSysOpLogs() bool {
	db := GetDb()
	result := db.Exec("DELETE FROM sys_op_log")
	return result.RowsAffected >= 0
}
