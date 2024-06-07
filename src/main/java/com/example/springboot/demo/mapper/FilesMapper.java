package com.example.springboot.demo.mapper;

import com.example.springboot.demo.pojo.Files;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_file】的数据库操作Mapper
* @createDate 2024-05-30 23:41:04
* @Entity com.example.springboot.demo.pojo.File
*/
public interface FilesMapper extends BaseMapper<Files> {
    List<Files> selectPages(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("name") String name);
}




