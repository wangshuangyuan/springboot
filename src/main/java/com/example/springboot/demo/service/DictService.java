package com.example.springboot.demo.service;

import com.example.springboot.demo.pojo.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.demo.pojo.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_dict】的数据库操作Service
* @createDate 2024-06-03 23:49:02
*/

public interface DictService extends IService<Dict> {
    List<Dict> queryAll();
    //Integer saveUser(User user);
    Boolean saveDict(Dict dict);

    Integer deleteById(Integer id);

    List<Dict> selectPages(Integer pageNum, Integer pageSize,String name);

    Integer selectTotal(String name);



    Dict findByDictname(String name);
}
