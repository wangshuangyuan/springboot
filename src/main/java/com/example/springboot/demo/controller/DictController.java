package com.example.springboot.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.Dict;
import com.example.springboot.demo.pojo.Role;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.DictService;
import com.example.springboot.demo.service.RoleService;
import com.example.springboot.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {
    @Resource
    private DictService dictService;
    @GetMapping("/name/{name}")
    public R findByUsername(@PathVariable String name){

        return R.success(dictService.findByDictname(name));
    }

    @GetMapping("/icons")//查询所有
    public R info(){
//        List<User> userList = userService.queryAll();
//        return userList;

        return R.success(dictService.queryAll());
    }

    @PostMapping("/save")//新增和修改
    public R save(@RequestBody Dict dict){

        return R.success(dictService.saveDict(dict));
    }

    @DeleteMapping("/{id}")//单个删除
    public R delete(@PathVariable Integer id){
//        Integer i = userService.deleteById(id);
//        return i;
        boolean r = dictService.removeById(id);

        return R.success(r);
    }

    @PostMapping ("/del/batch")//批量删除
    public R deleteBatch(@RequestBody List<Integer> ids){
        boolean b = dictService.removeByIds(ids);
        return R.success(b);
    }

    //mybatis-plus进行分页查询
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                  @RequestParam Integer pageSize,
                  @RequestParam(defaultValue = "") String name){
        IPage<Dict> page =new Page<>(pageNum,pageSize);

        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        if (!"".equals(name)){
            dictQueryWrapper.like("name",name);
        }

        dictQueryWrapper.orderByDesc("id");
        IPage<Dict> userPage = dictService.page(page,dictQueryWrapper);
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("currentUser = " + currentUser.getNickname());

        return R.success(userPage);
    }
}
