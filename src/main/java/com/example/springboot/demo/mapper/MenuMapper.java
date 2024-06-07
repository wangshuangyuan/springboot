package com.example.springboot.demo.mapper;

import com.example.springboot.demo.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.demo.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2024-06-03 00:22:07
* @Entity com.example.springboot.demo.pojo.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findAll();

    int insertMenu(Menu menu);


    int updateMenu(Menu menu);

    int deleteById(Integer id);
    List<Menu> selectPages(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("name") String name);

    int selectTotal(String name);
}




