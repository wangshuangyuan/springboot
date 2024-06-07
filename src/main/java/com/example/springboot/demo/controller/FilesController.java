package com.example.springboot.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.Files;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.FilesService;
import com.example.springboot.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Autowired
    private FilesService filesService;
    @PostMapping("/upload")
    public R upload(@RequestParam MultipartFile file) throws IOException {

        return R.success(filesService.uploadFilesMethods(file));

    }

    @GetMapping("/{fileUUID}")//文件下载
    public R download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        filesService.downloadFilesMethods(fileUUID, response);
        return null;
    }

    //mybatis-plus进行分页查询
    @GetMapping("/page")
    public R page(@RequestParam Integer pageNum,
                  @RequestParam Integer pageSize,
                  @RequestParam(defaultValue = "") String name  ){
        IPage<Files> page =new Page<>(pageNum,pageSize);

        QueryWrapper<Files> filesQueryWrapper = new QueryWrapper<>();
        //查询逻辑未删除的记录
        filesQueryWrapper.eq("is_delete",false);
        if (!"".equals(name)){
            filesQueryWrapper.like("name",name);
        }
        filesQueryWrapper.orderByDesc("id");
        IPage<Files> filesPage = filesService.page(page,filesQueryWrapper);
        return R.success(filesPage);
    }

    @DeleteMapping("/{id}")//单个删除
    public R delete(@PathVariable Integer id){
//        Integer i = userService.deleteById(id);
//        return i;

        Integer i = filesService.selectById(id);

        return R.success(i);
    }

    @PostMapping ("/del/batch")//批量删除
    public R deleteBatch(@RequestBody List<Integer> ids){
        filesService.deleteByIds(ids);
        return R.success();
    }

    @PostMapping("/import")//导入
    public R fileImport(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //List<User> list = reader.readAll(User.class);
        List<Files> list = reader.read(0, 1, Files.class);
        System.out.println("list = " + list);
        boolean b = filesService.saveBatch(list);
        return R.success(b);
    }

    @PostMapping("/update")//新增和修改
    public R save(@RequestBody Files files){
        Boolean aBoolean = filesService.updateById(files);
        return R.success(aBoolean);
    }
}
