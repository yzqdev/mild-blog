package middleware

import (
	"fmt"
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v4"
	"myblog-go/controller"
	"myblog-go/model"
)

func JwtHandler() gin.HandlerFunc {
	return func(context *gin.Context) {
		result := model.Result{
			ResultCode: http.StatusUnauthorized,
			Message:    "无法认证，重新登录",
		}

		auth := context.Request.Header.Get("Authorization")
		if len(auth) == 0 {
			context.Abort()
			context.JSON(http.StatusUnauthorized, result)
			return
		}

		// Remove "Bearer " prefix
		token := strings.TrimPrefix(auth, "Bearer ")

		uid, err := parseToken(token)
		if err != nil {
			context.Abort()
			result.Message = err.Error()
			context.JSON(http.StatusUnauthorized, result)
			return
		}

		context.Set("user", uid)
		context.Next()
	}
}

func parseToken(yourToken string) (uid string, err error) {
	claims := controller.NewJwtClaims{}
	_, err = jwt.ParseWithClaims(yourToken, &claims, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
		}
		return controller.SecretKey, nil
	})
	if err != nil {
		return "", err
	}
	return claims.Uid, nil
}
