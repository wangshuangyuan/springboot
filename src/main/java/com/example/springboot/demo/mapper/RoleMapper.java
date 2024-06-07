package com.example.springboot.demo.mapper;

import com.example.springboot.demo.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2024-06-02 23:30:27
* @Entity com.example.springboot.demo.pojo.Role
*/
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findAll();

    int insertRole(Role role);


    int updateRole(Role role);

    int deleteById(Integer id);
    List<Role> selectPages(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("name") String name);

    int selectTotal(String name);

    Integer selectByFlag(String role);
}




