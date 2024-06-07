package com.example.springboot.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.dto.UserDTO;
import com.example.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")//登录
    public R login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return  R.failure(400,"参数错误");
        }
        UserDTO dto = userService.login(userDTO);

        return R.success(dto);

    }
}
