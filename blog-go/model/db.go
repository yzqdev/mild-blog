package model

import (
	"fmt"
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"myblog-go/config"
)

type Database struct {
	*gorm.DB
}

var DB *gorm.DB

func init() {
	g := config.GetGlobal()

	dsn := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s sslmode=disable TimeZone=Asia/Shanghai",
		g.Pg.Host, g.Pg.User, g.Pg.Pass, g.Pg.Name, g.Pg.Port)

	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})
	if err != nil {
		log.Fatalf("failed to connect database: %v", err)
	}

	// Auto migrate all models
	err = db.AutoMigrate(
		&AdminUser{},
		&BlogInfo{},
		&Category{},
		&Tag{},
		&BlogTag{},
		&BlogCategory{},
		&Comment{},
		&Link{},
		&BlogConfig{},
		&Img{},
		&EmailConfig{},
		&SysOpLog{},
		&SysTimer{},
		&SysDictType{},
		&SysDictData{},
	)
	if err != nil {
		log.Fatalf("failed to migrate database: %v", err)
	}

	DB = db
}

func GetDb() *gorm.DB {
	return DB
}
