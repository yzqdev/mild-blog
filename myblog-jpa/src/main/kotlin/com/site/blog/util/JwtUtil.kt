package com.site.blog.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.Claim
import java.util.*
import kotlin.collections.HashMap


object JwtUtil {
    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private const val EXPIRE_TIME = (24 * 60 * 60 * 1000).toLong()

    /**
     * token私钥
     */
    private const val TOKEN_SECRET = "thisistokensecret2022"

    /**
     * 生成签名,15分钟后过期
     *
     * @param username
     * @param userId
     * @return
     */
    @JvmStatic
    fun sign(username: String?, userId: String?): String {
        //过期时间
        val date = Date(System.currentTimeMillis() + EXPIRE_TIME)
        //私钥及加密算法
        val algorithm = Algorithm.HMAC256(TOKEN_SECRET)
        //设置头信息
        val header = HashMap<String, Any>(2)
        header["typ"] = "JWT"
        header["alg"] = "HS256"
        //附带username和userID生成签名
        return JWT.create().withClaim("username", username)
            .withClaim("userId", userId).withExpiresAt(date).sign(algorithm)
    }

    @JvmStatic
    fun getUserId(token: String?): String? {
        return try {
            val jwt = JWT.decode(token)
            val result: Map<String, Claim>
            result = jwt.claims
            //注意!!!这里用toString会多出来引号!!!只能用asString
            result["userId"]!!.asString()
        } catch (e: JWTDecodeException) {
            null
        }
    }

    fun verifyToken(token: String?): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256(TOKEN_SECRET)
            val verifier = JWT.require(algorithm).build()
            val jwt = verifier.verify(token)
            true
        } catch (e: IllegalArgumentException) {
            false
        } catch (e: JWTVerificationException) {
            false
        }
    }
}