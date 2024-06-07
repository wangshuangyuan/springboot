package com.example.springboot.demo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.demo.exception.ServiceException;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.UserService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        if ((StrUtil.isBlank(token))){
            throw  new ServiceException(401,"无token,请重新登录");
        }
        String userId;
        try {
            userId= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(401,"token验证失败");
        }

        User userById = userService.getById(userId);
        if (userById == null){
            throw new ServiceException(401,"用户不存在，请重新登录");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userById.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        }catch (JWTVerificationException j){
            throw new ServiceException(401,"token验证失败，请重新登录");
        }
        return true;
    }
}
