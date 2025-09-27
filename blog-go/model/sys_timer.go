package model

import (
	"time"

	"gorm.io/gorm"
)

type SysTimer struct {
	Id          string         `json:"id" gorm:"column:id;type:uuid;primary_key;default:gen_random_uuid()"`
	TimerName   string         `json:"timerName" gorm:"column:timer_name;type:varchar(100)"`
	ActionClass string         `json:"actionClass" gorm:"column:action_class;type:varchar(200)"`
	Cron        string         `json:"cron" gorm:"column:cron;type:varchar(50)"`
	Status      bool           `json:"status" gorm:"column:status;default:false"`
	Remark      string         `json:"remark" gorm:"column:remark;type:varchar(500)"`
	CreatedAt   time.Time      `json:"createTime" gorm:"column:create_time"`
	UpdatedAt   time.Time      `json:"updateTime" gorm:"column:update_time"`
	DeletedAt   gorm.DeletedAt `json:"-" gorm:"column:deleted_at;index"`
}

func (SysTimer) TableName() string {
	return "sys_timers"
}

func GetTimerByID(id string) (result SysTimer) {
	db := GetDb()
	db.Where("id = ?", id).First(&result)
	return
}

func GetTimerPage(page, pageSize int) (result []SysTimer, total int64) {
	db := GetDb()
	query := db.Model(&SysTimer{})
	query.Count(&total)
	query.Offset((page - 1) * pageSize).Limit(pageSize).Find(&result)
	return
}
