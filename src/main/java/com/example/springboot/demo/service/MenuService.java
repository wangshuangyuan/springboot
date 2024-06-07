package com.example.springboot.demo.service;

import com.example.springboot.demo.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.demo.pojo.Role;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2024-06-03 00:22:07
*/
public interface MenuService extends IService<Menu> {

    List<Menu> findMenu(String name);
    List<Menu> queryAll();
    //Integer saveUser(User user);
    Boolean saveMenu(Menu menu);

    Integer deleteById(Integer id);

    List<Menu> selectPages(Integer pageNum, Integer pageSize,String name);

    Integer selectTotal(String name);



    Menu findByMenuname(String name);
}
