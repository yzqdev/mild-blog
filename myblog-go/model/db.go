package model

import (
	"github.com/gookit/color"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"myblog-go/config"
)

type Database struct {
	*gorm.DB
}

var DB *gorm.DB

// Init Open pgsql连接
func init() {
	g := config.GetGlobal()

	dsn := "host=" + g.Pg.Host + " user=" + g.Pg.User + " password=" + g.Pg.Pass + " dbname=" + g.Pg.Name + " port=" + g.Pg.Port
	color.Redln(dsn)
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{Logger: logger.Default.LogMode(logger.Info)})
	db.AutoMigrate(AdminUser{}, Article{})
	if err != nil {
		return
	}

	DB = db
	return
}

func GetDb() *gorm.DB {
	return DB
}
