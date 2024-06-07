package com.example.springboot.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.springboot.demo.exception.ServiceException;
import com.example.springboot.demo.mapper.RoleMapper;
import com.example.springboot.demo.mapper.RoleMenuMapper;
import com.example.springboot.demo.mapper.UserMapper;
import com.example.springboot.demo.pojo.Menu;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.pojo.dto.UserDTO;
import com.example.springboot.demo.service.MenuService;
import com.example.springboot.demo.service.RoleService;
import com.example.springboot.demo.service.UserService;

import com.example.springboot.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Log LOG =Log.get();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Override
    public List<UserDTO> addressCount() {
        List<UserDTO> users = userMapper.addressCount();
        return users;
    }

    @Override
    public List<User> queryAll() {
        List<User> allUser = userMapper.findAll();
        return allUser;
    }

//    @Override
//    public Integer saveUser(User user) {
//
//        if (user.getId()==null){
//            Integer i = userMapper.insertUser(user);
//            return i;
//        }else {
//            Integer i = userMapper.updateUser(user);
//            return i;
//        }
//    }

    @Override
    public Boolean saveUser(User user) {

        if (user.getId()==null){
          return  save(user);
        }else {

             return saveOrUpdate(user);
        }
    }


    @Override
    public Integer deleteById(Integer id) {
        Integer i = userMapper.deleteById(id);
        return i;
    }


    @Override
    public List<User> selectPages(Integer pageNum, Integer pageSize,String username) {
        pageNum=(pageNum-1)*pageSize;
        List<User> pageInfo = userMapper.selectPages(pageNum, pageSize,username);

        return pageInfo;
    }

    @Override
    public Integer selectTotal(String username) {
        int i = userMapper.selectTotal(username);

        return i;
    }

    @Override
    public UserDTO login(UserDTO userDTO) {//登录
        User userInfo = getUserInfo(userDTO);
        if (userInfo !=null){
            BeanUtil.copyProperties(userInfo,userDTO,true);
            String token = TokenUtils.genToken(userInfo.getId().toString(), userInfo.getPassword());
            userDTO.setToken(token);
            String role = userInfo.getRole();
            //通过flag查找橘色表里面的数据
            Integer roleId = roleMapper.selectByFlag(role);
            //通过角色id查找角色菜单关联表里面的菜单数据
            List<Integer> roleMenuIds = roleService.getRoleMenu(roleId);
            //查出系统所有的菜单，
            List<Menu> menus = menuService.findMenu("");
            List<Menu> roleMeus = new ArrayList<>();

            //再去筛选当前用户的菜单
            for (Menu menu : menus) {
                if (roleMenuIds.contains(menu.getId())){
                    roleMeus.add(menu);
                }
                List<Menu> children = menu.getChildren();
                //移除二级菜单里面不在roleMenuIds集合中的元素
                children.removeIf(child ->!roleMenuIds.contains(child.getId()));
            }
            userDTO.setMenuList(roleMeus);
            return userDTO;
        }else {
            throw new ServiceException(600,"用户名或者密码错误");
        }

    }

    @Override
    public User register(UserDTO userDTO) {
        User userInfo = getUserInfo(userDTO);
        if ( userInfo  == null){
            User user = new User();
            BeanUtil.copyProperties(userDTO,user,true);
             save(user);
            return user;
        }else {
            throw new ServiceException(600,"用户已存在");
        }

    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("username", username);
        User user = getOne(queryWrapper);

        return user;
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User user;
        try{
            user = getOne(queryWrapper);//查询登录用户数据
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(400,"系统错误");
        }
        return user;
    }
}
