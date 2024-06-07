package com.example.springboot.demo.utils;

import cn.hutool.core.date.DateUtil;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component

public class TokenUtils {

    private static UserService staticuserService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void setUserService(){
        staticuserService=userService;
    }
    public static String genToken(String userId,String sign){
         return JWT.create().withAudience(userId)//userid作为载荷 保存到token
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))
                .sign(Algorithm.HMAC256(sign));//password作为秘钥
    }
    public static User getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            String aud = JWT.decode(token).getAudience().get(0);
            Integer userId = Integer.valueOf(aud);
            return  staticuserService.getById(userId);
        }
        catch (Exception e){
                return null;
        }
    }
}
