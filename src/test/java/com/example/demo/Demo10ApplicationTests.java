package com.example.demo;

import com.example.demo.service.ICommodityService;
import com.example.demo.service.IUserOrderService;
import com.example.demo.service.impl.CommodityServiceImpl;
import com.example.demo.service.impl.UserServicelmpl;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Demo10ApplicationTests {
    @Autowired
    IUserOrderService
    userOrderService;

    @Test
    public void testGenJwt(){
        System.out.println(userOrderService.Test());

    }
    @Test
    public void testParseJwt(){
        String secretKey = "sb"; // 你的秘钥字符串
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // 将字符串转换为字节数组

        Claims claims = Jwts.parser()
                .setSigningKey(secretKeyBytes)
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcxODI1MDg1M30.BsZj2UjNf28s57hNk-rC8R1AcbmXep-AqbVVDFXWUKM")
                .getBody();
        System.out.println(claims);
    }


}
