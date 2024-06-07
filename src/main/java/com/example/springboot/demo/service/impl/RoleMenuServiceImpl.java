package com.example.springboot.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.demo.pojo.RoleMenu;
import com.example.springboot.demo.service.RoleMenuService;
import com.example.springboot.demo.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author shuangyuan
* @description 针对表【sys_role_menu】的数据库操作Service实现
* @createDate 2024-06-04 18:08:24
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

}




