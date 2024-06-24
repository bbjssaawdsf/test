package com.example.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
@Slf4j
public class JwtUtils {


    private static Long expire = 43200000L;

    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims){
          String secretKey = "sbb";
        //String secretKey = "sb"; // 你的秘钥字符串
       byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // 将字符串转换为字节数组

        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,secretKeyBytes)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        log.info(jwt);
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
         String secretKey = "sbb";
        //String secretKey = "sb"; // 你的秘钥字符串
         byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // 将字符串转换为字节数组

        Claims claims = Jwts.parser()
                .setSigningKey(secretKeyBytes)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
