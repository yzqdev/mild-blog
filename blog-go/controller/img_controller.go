package controller

import (
	"crypto/md5"
	"fmt"
	"io"
	"net/http"
	"os"
	"path/filepath"
	"strconv"
	"time"

	"github.com/gin-gonic/gin"
	"myblog-go/model"
	"myblog-go/util"
)

func UploadImage(c *gin.Context) {
	file, err := c.FormFile("img")
	if err != nil {
		util.Error(c, http.StatusBadRequest, "请选择文件")
		return
	}

	// Create upload directory
	uploadDir := filepath.Join(os.Getenv("HOME"), ".mildblog", "upload")
	if err := os.MkdirAll(uploadDir, 0755); err != nil {
		util.Error(c, http.StatusInternalServerError, "创建目录失败")
		return
	}

	// Generate filename
	ext := filepath.Ext(file.Filename)
	filename := fmt.Sprintf("%d%s", time.Now().UnixNano(), ext)
	filePath := filepath.Join(uploadDir, filename)

	// Save file
	if err := c.SaveUploadedFile(file, filePath); err != nil {
		util.Error(c, http.StatusInternalServerError, "保存文件失败")
		return
	}

	// Calculate MD5
	f, err := os.Open(filePath)
	if err != nil {
		util.Error(c, http.StatusInternalServerError, "读取文件失败")
		return
	}
	defer f.Close()

	h := md5.New()
	if _, err := io.Copy(h, f); err != nil {
		util.Error(c, http.StatusInternalServerError, "计算MD5失败")
		return
	}
	md5Sum := fmt.Sprintf("%x", h.Sum(nil))

	// Save to database
	img := model.Img{
		ImgName:    filename,
		ImgPath:    filePath,
		ImgSize:    file.Size,
		ImgUrl:     "upload/" + filename,
		Md5:        md5Sum,
		UploadTime: time.Now(),
	}
	model.CreateImg(&img)

	result := gin.H{
		"message": "上传成功",
		"url":     img.ImgUrl,
		"img":     img,
	}
	util.Success(c, result)
}

func ListImages(c *gin.Context) {
	page, _ := strconv.Atoi(c.DefaultQuery("page", "1"))
	limit, _ := strconv.Atoi(c.DefaultQuery("limit", "10"))

	images, total := model.GetImgPage(page, limit)
	util.PageSuccess(c, images, total, page, limit)
}

func DeleteImage(c *gin.Context) {
	id := c.Param("id")
	img := model.GetImgByID(id)

	// Delete files
	if img.ImgPath != "" {
		os.Remove(img.ImgPath)
	}
	if img.ThumbnailPath != "" {
		os.Remove(img.ThumbnailPath)
	}

	flag := model.DeleteImg(id)
	if flag {
		util.Success(c, img)
	} else {
		util.Error(c, http.StatusInternalServerError, "删除失败")
	}
}
