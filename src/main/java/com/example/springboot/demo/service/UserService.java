package com.example.springboot.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;

import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.pojo.dto.UserDTO;

import java.util.List;

public interface UserService extends IService<User> {

    List<UserDTO> addressCount();
    List<User> queryAll();
    //Integer saveUser(User user);
    Boolean saveUser(User user);

    Integer deleteById(Integer id);

    List<User> selectPages(Integer pageNum, Integer pageSize,String username);

    Integer selectTotal(String username);

    UserDTO login(UserDTO userDTO);
    User register(UserDTO userDTO);

    User findByUsername(String username);
}
