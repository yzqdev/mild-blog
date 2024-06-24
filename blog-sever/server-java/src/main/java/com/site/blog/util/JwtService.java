package com.site.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtService {


    /**
     * 过期时间为一天
     * TODO 正式上线更换为15分钟
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "thisistokensecret2022";

    /**
     * 生成签名,15分钟后过期
     *
     * @param username
     * @param userId
     * @return
     */
    public static String sign(String username, String userId) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        return JWT.create().withClaim("username", username)
                .withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> result;
            result = jwt.getClaims();

            return result.get("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> result;
            result = jwt.getClaims();
            //注意!!!这里用toString会多出来引号!!!只能用asString
            return result.get("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }

    }
}
