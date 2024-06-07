package com.example.springboot.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.pojo.dto.UserDTO;
import com.example.springboot.demo.service.UserService;
import com.example.springboot.demo.utils.TokenUtils;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

import java.util.List;



@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {//用户crud以及分页查询
    @Autowired
    private UserService userService;

    @GetMapping("/username/{username}")
    public R findByUsername(@PathVariable String username){

        return R.success(userService.findByUsername(username));
    }




    @GetMapping("/info")//查询所有
    public List<User> info(){
//        List<User> userList = userService.queryAll();
//        return userList;
       return userService.list();
    }

    @PostMapping("/save")//新增和修改
    public R save(@RequestBody User user){

        return R.success(userService.saveUser(user));
    }
    @DeleteMapping("/{id}")//单个删除
    public R delete(@PathVariable Integer id){
//        Integer i = userService.deleteById(id);
//        return i;
        boolean r = userService.removeById(id);

        return R.success(r);
    }

    @PostMapping ("/del/batch")//批量删除
    public R deleteBatch(@RequestBody List<Integer> ids){
        boolean b = userService.removeByIds(ids);
        return R.success(b);
    }

//    @GetMapping("/page")
//    public Map<String, Object> page(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
//                                    @RequestParam String username){
//        Integer userTotal = userService.selectTotal(username);
//        List<User> userData = userService.selectPage(pageNum,pageSize,username);
//        Map<String, Object> res=new HashMap<>();
//        res.put("userTotal",userTotal);
//        res.put("userData",userData);
//        return res;
//    }


    //mybatis-plus进行分页查询
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                            @RequestParam Integer pageSize,
                            @RequestParam(defaultValue = "") String username,
                            @RequestParam(defaultValue = "") String email,
                            @RequestParam(defaultValue = "") String address){
        IPage<User> page =new Page<>(pageNum,pageSize);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!"".equals(username)){
            userQueryWrapper.like("username",username);
        }
        if (!"".equals(email)){
            userQueryWrapper.like("email",email);
        }
        if (!"".equals(address)){
            userQueryWrapper.like("address",address);
        }
        userQueryWrapper.orderByDesc("id");
        IPage<User> userPage = userService.page(page,userQueryWrapper);
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println("currentUser = " + currentUser.getNickname());

        return R.success(userPage);
    }
//文件导出
    @GetMapping("/export")//导出
    public R fileExport(HttpServletResponse response) throws Exception{
    List<User> list=userService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题别名
//        writer.addHeaderAlias("username", "用户名");
//        writer.addHeaderAlias("password", "密码");
//        writer.addHeaderAlias("nickname", "昵称");
//        writer.addHeaderAlias("email", "邮箱");
//        writer.addHeaderAlias("phone", "电话");
//        writer.addHeaderAlias("address", "地址");
//        writer.addHeaderAlias("createtime", "创建时间");
        writer.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream out=response.getOutputStream();

        writer.flush(out, true);
        out.close();
        writer.close();

            return R.success();


    }
    @PostMapping("/import")//导入
    public R fileImport(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //List<User> list = reader.readAll(User.class);
        List<User> list = reader.read(0, 1, User.class);
        System.out.println("list = " + list);
        boolean b = userService.saveBatch(list);
        return R.success(b);
    }
}
