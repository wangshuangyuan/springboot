package com.example.springboot.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.demo.mapper.RoleMenuMapper;
import com.example.springboot.demo.pojo.Menu;
import com.example.springboot.demo.pojo.Role;
import com.example.springboot.demo.pojo.RoleMenu;
import com.example.springboot.demo.service.MenuService;
import com.example.springboot.demo.service.RoleService;
import com.example.springboot.demo.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2024-06-02 23:30:27
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{
    @Autowired
    private RoleMapper roleMapper;


    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<Role> queryAll() {
        List<Role> allRole = roleMapper.findAll();
        return allRole;
    }

    @Override
    public Boolean saveRole(Role role) {
        if (role.getId()==null){
            return  save(role);
        }else {

            return saveOrUpdate(role);
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        Integer i = roleMapper.deleteById(id);
        return i;
    }

    @Override
    public List<Role> selectPages(Integer pageNum, Integer pageSize, String name) {
        pageNum=(pageNum-1)*pageSize;
        List<Role> pageInfo = roleMapper.selectPages(pageNum, pageSize,name);

        return pageInfo;
    }

    @Override
    public Integer selectTotal(String name) {
        int i = roleMapper.selectTotal(name);

        return i;
    }

    @Override
    public Role findByRolename(String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Role role = getOne(queryWrapper);

        return role;
    }

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id", roleId);

        //先删除数据库里面纯在的roleid相关的数据
        roleMenuMapper.delete(roleMenuQueryWrapper);

        for (Integer menuId:menuIds
             ) {
//            Menu menuById = menuService.getById(menuId);
//
//            if (menuById.getPid() !=null && !menuIds.contains(menuById.getPid())){//二级菜单的父级id也存储数据库
//                RoleMenu roleMenu = new RoleMenu();
//                roleMenu.setRoleId(roleId);
//                roleMenu.setMenuId(menuId);
//                roleMenuMapper.insert(roleMenu);
//            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        List<Integer> roleMenuIdList= new ArrayList<>();
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        QueryWrapper<RoleMenu> roleId1 = roleMenuQueryWrapper.eq("role_id", roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleId1);
        for (RoleMenu roleMenuList: roleMenus
             ) {
            roleMenuIdList.add(roleMenuList.getMenuId());
        }
        return roleMenuIdList;
    }
}




