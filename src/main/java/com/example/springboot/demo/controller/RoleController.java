package com.example.springboot.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.Role;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.RoleService;
import com.example.springboot.demo.utils.TokenUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/name/{name}")
    public R findByUsername(@PathVariable String name){

        return R.success(roleService.findByRolename(name));
    }

    @GetMapping("/info")//查询所有
    public R info(){
//        List<User> userList = userService.queryAll();
//        return userList;

        return R.success(roleService.list());
    }

    @PostMapping("/save")//新增和修改
    public R save(@RequestBody Role role){

        return R.success(roleService.saveRole(role));
    }

    @DeleteMapping("/{id}")//单个删除
    public R delete(@PathVariable Integer id){
//        Integer i = userService.deleteById(id);
//        return i;
        boolean r = roleService.removeById(id);

        return R.success(r);
    }

    @PostMapping ("/del/batch")//批量删除
    public R deleteBatch(@RequestBody List<Integer> ids){
        boolean b = roleService.removeByIds(ids);
        return R.success(b);
    }

    //mybatis-plus进行分页查询
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                  @RequestParam Integer pageSize,
                  @RequestParam(defaultValue = "") String name,
                  @RequestParam(defaultValue = "") String description){
        IPage<Role> page =new Page<>(pageNum,pageSize);

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)){
            roleQueryWrapper.like("name",name);
        }
        if (!"".equals(description)){
            roleQueryWrapper.like("description",description);
        }
        roleQueryWrapper.orderByDesc("id");
        IPage<Role> userPage = roleService.page(page,roleQueryWrapper);
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("currentUser = " + currentUser.getNickname());

        return R.success(userPage);
    }


    @PostMapping("/roleMenu/{roleId}")
    public R roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId, menuIds);
        return R.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public R getRoleMenu(@PathVariable Integer roleId){

        return R.success(roleService.getRoleMenu(roleId));
    }
}
