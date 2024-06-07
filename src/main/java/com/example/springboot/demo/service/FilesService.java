package com.example.springboot.demo.service;

import com.example.springboot.demo.pojo.Files;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.demo.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_file】的数据库操作Service
* @createDate 2024-05-30 23:41:04
*/
public interface FilesService extends IService<Files> {

    Integer selectById(Integer id);
    void deleteByIds(List<Integer> ids);

    List<Files> selectPages(Integer pageNum, Integer pageSize, String name);

    String uploadFilesMethods(MultipartFile file) throws IOException;
    String downloadFilesMethods(String fileUUID, HttpServletResponse response) throws IOException;
}
