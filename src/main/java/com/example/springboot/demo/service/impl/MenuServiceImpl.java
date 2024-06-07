package com.example.springboot.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.demo.pojo.Menu;
import com.example.springboot.demo.pojo.Role;
import com.example.springboot.demo.service.MenuService;
import com.example.springboot.demo.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author shuangyuan
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2024-06-03 00:22:07
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<Menu> findMenu(String name) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)){
            menuQueryWrapper.like("name",name);
        }
        // menuQueryWrapper.orderByDesc("id");


        List<Menu> list = list(menuQueryWrapper);
        //找出pid为null 的一级菜单
        List<Menu> parentNodes = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu :parentNodes) {
            //筛选所有数据中pid=父id的数据就是二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));//找出一级菜单的子菜单
        }
        return parentNodes;
    }

    @Override
    public List<Menu> queryAll() {

        List<Menu> allMenu = menuMapper.findAll();
        return allMenu;
    }

    @Override
    public Boolean saveMenu(Menu menu) {
        if (menu.getId()==null){
            return  save(menu);
        }else {

            return saveOrUpdate(menu);
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        Integer i = menuMapper.deleteById(id);
        return i;
    }

    @Override
    public List<Menu> selectPages(Integer pageNum, Integer pageSize, String name) {
        pageNum=(pageNum-1)*pageSize;
        List<Menu> pageInfo = menuMapper.selectPages(pageNum, pageSize,name);

        return pageInfo;
    }

    @Override
    public Integer selectTotal(String name) {
        int i = menuMapper.selectTotal(name);

        return i;
    }

    @Override
    public Menu findByMenuname(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Menu menu = getOne(queryWrapper);
        return menu;
    }
}




