package com.example.system.demos.web.manageUser.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.exceptions.JWTVerificationException; // 修改了异常类
import java.util.Date;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
/**
 * JWT工具类，用于生成、解析和验证JSON Web Token
 */
@Component
public class JwtUtils {

    // 密钥（建议从配置文件中读取）
    @Value("${login.secret}")
    private String secret;
    // Token过期时间,从配置文件中读取
    @Value("${login.expiration}")
    private long expiration;

    /**
     * 根据用户登录信息生成Token
     * @param login 用户登录名
     * @return 生成的Token字符串
     */
    public String generateToken(String login) {
        return JWT.create()
                .withSubject(login)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 解析Token以获取用户登录信息
     * @param token 用户Token
     * @return 用户登录名
     * @throws RuntimeException 如果Token无效，则抛出运行时异常
     */
    public String parseToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("无效的 Token", e);
        }
    }

    /**
     * 验证Token的有效性
     * @param token 用户Token
     * @return 如果Token有效返回true，否则返回false
     */
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}



