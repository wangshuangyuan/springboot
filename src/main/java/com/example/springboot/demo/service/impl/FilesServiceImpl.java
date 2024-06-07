package com.example.springboot.demo.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.multipart.UploadFile;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.demo.pojo.Files;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.service.FilesService;
import com.example.springboot.demo.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_file】的数据库操作Service实现
* @createDate 2024-05-30 23:41:04
*/
@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
    implements FilesService {

    @Autowired
    private FilesMapper filesMapper;
    @Value("${files.upload.path}")
    private String fileUploadPath;


    @Override
    public Integer selectById(Integer id) {
        Files files = filesMapper.selectById(id);
        files.setIsDelete(true);
        int i = filesMapper.updateById(files);
        return i;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        QueryWrapper<Files> filesQueryWrapper = new QueryWrapper<>();
        filesQueryWrapper.in("id",ids);
        List<Files> files = filesMapper.selectList(filesQueryWrapper);
        for (Files file:files ) {
            file.setIsDelete(true);
            int i = filesMapper.updateById(file);
        }

    }

    @Override
    public List<Files> selectPages(Integer pageNum, Integer pageSize, String name) {
        pageNum=(pageNum-1)*pageSize;
        List<Files> pageInfo = filesMapper.selectPages(pageNum, pageSize,name);

        return pageInfo;
    }

    @Override
    public String uploadFilesMethods(MultipartFile file) throws IOException {
        //获取文件相关数据
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //文件存储文件路径

        //定义一个文件的唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid+ StrUtil.DOT+type;
        File uploadFile = new File(fileUploadPath +fileUUID );
        //文件父级目录存储文件路径
       File parentFile= uploadFile.getParentFile();

        if (!parentFile.exists()){//判断文件是否存在
            parentFile.mkdirs();
        }


        String url;
        //当文件存在时获取文件的md5
        file.transferTo(uploadFile);

          String   md5= SecureUtil.md5(uploadFile);
            //查询数据库是否有相同的文件的md5
            QueryWrapper<Files> filesQueryWrapper = new QueryWrapper<>();
            filesQueryWrapper.eq("md5",md5);

            Files dbFiles = filesMapper.selectOne(filesQueryWrapper);

            //获取文件的url

            if (dbFiles!=null){
                url=dbFiles.getUrl();
                //删除已存在的文件
                uploadFile.delete();
            }else {
                url="http://localhost:8080/file/"+fileUUID;
            }
        Files saveFiles = new Files();
        saveFiles.setName(originalFilename);
        saveFiles.setType(type);
        saveFiles.setSize(size/1024);
        saveFiles.setUrl(url);
        saveFiles.setMd5(md5);
        filesMapper.insert(saveFiles);
        return url;
    }



    @Override
    public String downloadFilesMethods(String fileUUID, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字符流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        return fileUUID;
    }


}




