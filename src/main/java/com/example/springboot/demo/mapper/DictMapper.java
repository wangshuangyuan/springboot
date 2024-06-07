package com.example.springboot.demo.mapper;

import com.example.springboot.demo.pojo.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.demo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_dict】的数据库操作Mapper
* @createDate 2024-06-03 23:49:02
* @Entity com.example.springboot.demo.pojo.Dict
*/
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> findAll();

    int insertDict(Dict dict);


    int updateDict(Dict dict);

    int deleteById(Integer id);
    List<Dict> selectPages(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("name") String name);

    int selectTotal(String name);
}




