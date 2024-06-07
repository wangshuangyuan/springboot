package com.example.springboot.demo.service;

import com.example.springboot.demo.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.demo.pojo.RoleMenu;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.pojo.dto.UserDTO;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2024-06-02 23:30:28
*/
public interface RoleService extends IService<Role> {
    List<Role> queryAll();
    //Integer saveUser(User user);
    Boolean saveRole(Role role);

    Integer deleteById(Integer id);

    List<Role> selectPages(Integer pageNum, Integer pageSize,String name);

    Integer selectTotal(String name);



    Role findByRolename(String name);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);
}
