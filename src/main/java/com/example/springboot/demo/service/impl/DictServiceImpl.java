package com.example.springboot.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.demo.mapper.RoleMapper;
import com.example.springboot.demo.pojo.Dict;
import com.example.springboot.demo.pojo.Role;
import com.example.springboot.demo.service.DictService;
import com.example.springboot.demo.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author shuangyuan
* @description 针对表【sys_dict】的数据库操作Service实现
* @createDate 2024-06-03 23:49:02
*/
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict>
    implements DictService{
    @Autowired
    private DictMapper dictMapper;
    @Override
    public List<Dict> queryAll() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type","icon");
        List<Dict> allDict = dictMapper.selectList(dictQueryWrapper);
        return allDict;
    }

    @Override
    public Boolean saveDict(Dict dict) {
        if (dict.getName()==null){
            return  save(dict);
        }else {
            return saveOrUpdate(dict);
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        Integer i = dictMapper.deleteById(id);
        return i;
    }

    @Override
    public List<Dict> selectPages(Integer pageNum, Integer pageSize, String name) {
        pageNum=(pageNum-1)*pageSize;
        List<Dict> pageInfo = dictMapper.selectPages(pageNum, pageSize,name);

        return pageInfo;
    }

    @Override
    public Integer selectTotal(String name) {
        int i = dictMapper.selectTotal(name);

        return i;
    }

    @Override
    public Dict findByDictname(String name) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Dict dict = getOne(queryWrapper);

        return dict;
    }
}




