package com.example.springboot.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.Menu;
import com.example.springboot.demo.pojo.Role;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.MenuService;
import com.example.springboot.demo.service.RoleService;
import com.example.springboot.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @GetMapping("/name/{name}")
    public R findByUsername(@PathVariable String name){

        return R.success(menuService.findByMenuname(name));
    }

    @GetMapping("/info")//查询所有
    public R info(@RequestParam(defaultValue = "") String name){

        return R.success(menuService.findMenu(name));
    }

    @PostMapping("/save")//新增和修改
    public R save(@RequestBody Menu menu){

        return R.success(menuService.saveMenu(menu));
    }

    @DeleteMapping("/{id}")//单个删除
    public R delete(@PathVariable Integer id){
//        Integer i = userService.deleteById(id);
//        return i;
        boolean r = menuService.removeById(id);

        return R.success(r);
    }

    @PostMapping ("/del/batch")//批量删除
    public R deleteBatch(@RequestBody List<Integer> ids){
        boolean b = menuService.removeByIds(ids);
        return R.success(b);
    }

    //mybatis-plus进行分页查询
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                  @RequestParam Integer pageSize,
                  @RequestParam(defaultValue = "") String name){
        IPage<Menu> page =new Page<>(pageNum,pageSize);

        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)){
            menuQueryWrapper.like("name",name);
        }

        menuQueryWrapper.orderByDesc("id");
        IPage<Menu> userPage = menuService.page(page,menuQueryWrapper);
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("currentUser = " + currentUser.getNickname());

        return R.success(userPage);
    }

}
