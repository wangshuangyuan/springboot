package com.example.springboot.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class Menu implements Serializable {
    @TableId( value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;
    @TableField(exist = false)
    private List<Menu> children;
    private Integer pid;
    private String pagePath;

    private static final long serialVersionUID = 1L;
}