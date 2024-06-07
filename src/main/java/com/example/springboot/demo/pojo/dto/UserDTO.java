package com.example.springboot.demo.pojo.dto;

import com.example.springboot.demo.pojo.Menu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {//接受前段登录请求的参数
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;

    private List<Menu> menuList;

    private String role;
    private String address;
    private String addressCount;

}
