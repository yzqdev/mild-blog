package main

import (
	"github.com/gin-gonic/gin"
	"myblog-go/controller"
	"myblog-go/middleware"
)

func InitRouter(e *gin.Engine) {
	baseRouter := e.Group("/auth")
	{
		baseRouter.POST("/login", controller.Login)
		baseRouter.POST("/reg", controller.Register)
	}
	adminRouter := e.Group("/admin", middleware.JwtHandler())
	{

		adminRouter.GET("/index", controller.GetUser)
		adminRouter.POST("/addArticle", controller.AddArticle)
		adminRouter.PUT("/updateArticle", controller.UpdateArticle)
		adminRouter.DELETE("/delArticle/:id", controller.DelArticle)
		adminRouter.GET("/getArticleById/:id", controller.GetArticleById)
		adminRouter.GET("/checkToken", controller.CheckToken)

	}
	homeRouter := e.Group("/home")
	{
		homeRouter.GET("/index")
		homeRouter.GET("/getArticleList", controller.GetArticleList)
		homeRouter.GET("/getArticleById/:id", controller.GetArticleById)

	}

}
func StaticRoute(e *gin.Engine) {
	e.Static("./public", "views")
}
