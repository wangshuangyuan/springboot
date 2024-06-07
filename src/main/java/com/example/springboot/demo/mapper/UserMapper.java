package com.example.springboot.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.pojo.dto.UserDTO;
import org.apache.ibatis.annotations.*;



import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    List<UserDTO> addressCount();

    List<User> findAll();

    int insertUser(User user);


    int updateUser(User user);

    int deleteById(Integer id);
    List<User> selectPages(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize,@Param("username") String username);

    int selectTotal(String username);
}
