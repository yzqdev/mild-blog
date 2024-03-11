package main

import (
	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"myblog-go/config"
	"time"
)

func main() {
	engine := gin.Default()
	g := config.GetGlobal()
	engine.Use(cors.New(cors.Config{
		AllowAllOrigins:  true,
		AllowMethods:     []string{"GET", "POST", "PUT", "PATCH", "DELETE", "HEAD"},
		AllowHeaders:     []string{"Origin", "Content-Length", "Content-Type", "Authorization"},
		ExposeHeaders:    []string{"Content-Length"},
		AllowCredentials: false,

		MaxAge: 12 * time.Hour,
	}))
	Embed.RestoreFolder(".")
	InitRouter(engine)
	engine.Run(g.Server.Port) // 监听并在 0.0.0.0:8080 上启动服务
}
