package controller

import (
	"net/http"
	"os"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v4"
	"github.com/rs/xid"
	"myblog-go/model"
	"myblog-go/util"
)

var SecretKey = []byte(os.Getenv("JWT_SECRET"))
var jwtSecret = []byte(os.Getenv("JWT_SECRET"))

func init() {
	if len(SecretKey) == 0 {
		SecretKey = []byte("thisistokensecret2022-dev-only")
		jwtSecret = SecretKey
	}
}

type ReqLogin struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type ReqReg struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

type NewJwtClaims struct {
	Uid string
	jwt.RegisteredClaims
}

func Login(c *gin.Context) {
	user := &ReqLogin{}
	if err := c.ShouldBindJSON(user); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}

	if user.Username == "" || user.Password == "" {
		util.Error(c, http.StatusBadRequest, "用户名和密码不能为空")
		return
	}

	sqlU := model.QueryByUsername(user.Username)
	if sqlU.ID == "" {
		util.Error(c, http.StatusUnauthorized, "用户不存在")
		return
	}

	if sqlU.Locked {
		util.Error(c, http.StatusUnauthorized, "账户已被冻结")
		return
	}

	// Simple password check (in production, use bcrypt)
	if sqlU.Password != user.Password {
		util.Error(c, http.StatusUnauthorized, "密码错误")
		return
	}

	expiresTime := jwt.NewNumericDate(time.Now().Add(15 * time.Minute))
	stdClaims := jwt.RegisteredClaims{
		Audience:  []string{"blog"},
		ExpiresAt: expiresTime,
		ID:        xid.New().String(),
		IssuedAt:  jwt.NewNumericDate(time.Now()),
		Issuer:    "blog-server",
		NotBefore: jwt.NewNumericDate(time.Now()),
		Subject:   "login",
	}
	newClaims := NewJwtClaims{
		Uid:              sqlU.ID,
		RegisteredClaims: stdClaims,
	}
	tokenClaims := jwt.NewWithClaims(jwt.SigningMethodHS256, newClaims)
	token, err := tokenClaims.SignedString(SecretKey)
	if err != nil {
		util.Error(c, http.StatusInternalServerError, "生成token失败")
		return
	}

	util.Success(c, token)
}

func Register(c *gin.Context) {
	u := &ReqReg{}
	if err := c.ShouldBindJSON(u); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}

	if u.Username == "" || u.Password == "" {
		util.Error(c, http.StatusBadRequest, "用户名和密码不能为空")
		return
	}

	if model.GetUserCheck(u.Username) {
		util.Error(c, http.StatusBadGateway, "用户已经存在")
		return
	}

	data := model.AdminUser{
		Username: u.Username,
		Password: u.Password,
		Uuid:     xid.New().String(),
		Locked:   true,
		Role:     0,
	}
	model.SaveUser(&data)

	util.Success(c, "注册成功")
}

func GetUser(c *gin.Context) {
	userContext, exist := c.Get("user")
	if !exist {
		util.Error(c, http.StatusUnauthorized, "未登录")
		return
	}
	userId, ok := userContext.(string)
	if !ok {
		util.Error(c, http.StatusUnauthorized, "token无效")
		return
	}
	util.Success(c, userId)
}

func CheckToken(c *gin.Context) {
	userContext, exist := c.Get("user")
	if !exist {
		util.Error(c, http.StatusUnauthorized, "未登录")
		return
	}
	userId, ok := userContext.(string)
	if !ok {
		util.Error(c, http.StatusUnauthorized, "token无效")
		return
	}
	util.Success(c, userId)
}

func GetUserInfo(c *gin.Context) {
	userContext, exist := c.Get("user")
	if !exist {
		util.Error(c, http.StatusUnauthorized, "请重新登录")
		return
	}
	userId, ok := userContext.(string)
	if !ok {
		util.Error(c, http.StatusUnauthorized, "token无效")
		return
	}
	user := model.QueryUserByID(userId)
	if user.ID == "" {
		util.Error(c, http.StatusUnauthorized, "用户不存在")
		return
	}
	util.Success(c, user)
}

func GetUsers(c *gin.Context) {
	users := model.GetAllUsers()
	util.Success(c, users)
}

func EditUser(c *gin.Context) {
	user := &model.AdminUser{}
	if err := c.ShouldBind(user); err != nil {
		util.Error(c, http.StatusBadRequest, "数据绑定失败")
		return
	}
	model.UpdateUser(user)
	util.Success(c, user)
}

func DeleteUser(c *gin.Context) {
	id := c.Param("id")
	if id == "" {
		util.Error(c, http.StatusBadRequest, "请输入id")
		return
	}
	flag := model.DeleteUser(id)
	util.Success(c, flag)
}

func ValidatePassword(c *gin.Context) {
	oldPwd := c.Query("oldPwd")
	userContext, exist := c.Get("user")
	if !exist {
		util.Error(c, http.StatusUnauthorized, "未登录")
		return
	}
	userId, ok := userContext.(string)
	if !ok {
		util.Error(c, http.StatusUnauthorized, "token无效")
		return
	}
	user := model.QueryUserByID(userId)
	if user.Password == oldPwd {
		util.Success(c, "验证成功")
	} else {
		util.Error(c, http.StatusBadRequest, "密码错误")
	}
}

func UnlockUser(c *gin.Context) {
	id := c.Param("id")
	user := model.QueryUserByID(id)
	user.Locked = !user.Locked
	model.UpdateUser(&user)
	msg := "未冻结"
	if user.Locked {
		msg = "冻结"
	}
	util.Success(c, msg)
}

func Dashboard(c *gin.Context) {
	articleCount := model.GetViewsSum() // Using views sum as a proxy
	commentCount := model.CountAllComments()
	views := model.GetViewsSum()
	res := gin.H{
		"articleCount": articleCount,
		"commentCount": commentCount,
		"viewsCount":   views,
	}
	util.Success(c, res)
}
