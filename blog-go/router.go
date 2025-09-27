package main

import (
	"github.com/gin-gonic/gin"
	"myblog-go/controller"
	"myblog-go/middleware"
)

func InitRouter(e *gin.Engine) {
	// Auth routes (public)
	authRouter := e.Group("/v2/auth")
	{
		authRouter.POST("/login", controller.Login)
		authRouter.POST("/reg", controller.Register)
	}

	// Admin routes (JWT protected)
	adminRouter := e.Group("/v2/admin", middleware.JwtHandler())
	{
		// User management
		adminRouter.GET("/getUser", controller.GetUserInfo)
		adminRouter.GET("/users", controller.GetUsers)
		adminRouter.POST("/userEdit", controller.EditUser)
		adminRouter.POST("/del/:id", controller.DeleteUser)
		adminRouter.GET("/password", controller.ValidatePassword)
		adminRouter.POST("/unlock/:id", controller.UnlockUser)
		adminRouter.GET("/dashboard", controller.Dashboard)

		// Blog management
		adminRouter.GET("/blog/get/:id", controller.GetBlogById)
		adminRouter.POST("/blog/edit", controller.SaveBlog)
		adminRouter.GET("/blog/list", controller.GetBlogList)
		adminRouter.POST("/blog/show/:id", controller.UpdateBlogShow)
		adminRouter.POST("/blog/delete/:id", controller.DeleteBlog)
		adminRouter.POST("/blog/clear/:id", controller.ClearBlog)
		adminRouter.POST("/blog/restore", controller.RestoreBlog)

		// Category management
		adminRouter.GET("/category/list", controller.CategoryList)
		adminRouter.GET("/category/paging", controller.GetCategoryList)
		adminRouter.POST("/category/update", controller.UpdateCategory)
		adminRouter.POST("/category/isDel", controller.UpdateCategoryStatus)
		adminRouter.POST("/category/clear/:id", controller.ClearCategory)
		adminRouter.POST("/category/add", controller.AddCategory)

		// Tag management
		adminRouter.GET("/tags/list", controller.TagsList)
		adminRouter.GET("/tags/paging", controller.GetTagList)
		adminRouter.POST("/tags/isDel", controller.UpdateTagStatus)
		adminRouter.POST("/tags/add", controller.AddTag)
		adminRouter.POST("/tags/clear/:id", controller.ClearTag)
		adminRouter.POST("/tags/update", controller.UpdateTag)

		// Comment management
		adminRouter.GET("/comment/paging", controller.GetCommentList)
		adminRouter.POST("/comment/isDel/:id", controller.UpdateCommentStatus)
		adminRouter.DELETE("/comment/delete/:id", controller.DeleteComment)
		adminRouter.POST("/comment/edit", controller.EditComment)

		// Link management
		adminRouter.GET("/linkType/list", controller.LinkTypeList)
		adminRouter.GET("/link/paging", controller.GetLinkList)
		adminRouter.POST("/link/hide", controller.UpdateLinkStatus)
		adminRouter.DELETE("/link/clear/:id", controller.ClearLink)
		adminRouter.POST("/link/edit", controller.EditLink)

		// Config management
		adminRouter.GET("/blogConfig/list", controller.GetBlogConfig)
		adminRouter.POST("/blogConfig/edit", controller.UpdateBlogConfig)
		adminRouter.POST("/blogConfig/add", controller.AddBlogConfig)
		adminRouter.DELETE("/blogConfig/del/:id", controller.DeleteBlogConfig)

		// Image management
		adminRouter.POST("/img/upload", controller.UploadImage)
		adminRouter.POST("/img/list", controller.ListImages)
		adminRouter.DELETE("/img/del/:id", controller.DeleteImage)

		// System log
		adminRouter.POST("/log", controller.GetSysLogs)
		adminRouter.DELETE("/log/clear", controller.ClearSysLogs)

		// System dictionary
		adminRouter.GET("/dict/list", controller.GetDictList)
		adminRouter.POST("/dict/add", controller.AddDict)
		adminRouter.DELETE("/dict/clear/:dictType", controller.ClearDictType)
		adminRouter.POST("/dictData/add", controller.AddDictData)
		adminRouter.GET("/dictData/list/:dictType", controller.GetDictDataList)

		// Timer
		adminRouter.GET("/timer/list", controller.GetTimers)
		adminRouter.POST("/timer/start/:id", controller.StartTimer)
	}

	// Home routes (public)
	homeRouter := e.Group("/v2/home")
	{
		homeRouter.GET("/", controller.HomeIndex)
		homeRouter.GET("/index", controller.HomeIndex)
		homeRouter.POST("/tag/:tagId", controller.GetBlogsByTag)
		homeRouter.POST("/category/:categoryId", controller.GetBlogsByCategory)
		homeRouter.GET("/search/:keyword", controller.SearchBlogs)
		homeRouter.POST("/timeline", controller.GetTimeline)
		homeRouter.GET("/tags", controller.GetHomeTags)
		homeRouter.GET("/categories", controller.GetHomeCategories)
		homeRouter.GET("/configs", controller.GetHomeConfigs)
		homeRouter.GET("/blog/:blogId", controller.GetBlogDetail)
		homeRouter.GET("/article/:blogId", controller.GetBlogDetail)
		homeRouter.GET("/blog/listComment", controller.ListComments)
		homeRouter.GET("/link", controller.GetLinks)
		homeRouter.POST("/blog/comment", controller.SubmitComment)
	}
}
