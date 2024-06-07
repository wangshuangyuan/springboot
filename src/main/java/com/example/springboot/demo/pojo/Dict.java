package com.example.springboot.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName sys_dict
 */
@TableName(value ="sys_dict")
@Data
public class Dict implements Serializable {
    private String name;

    private String value;

    private String type;

    private static final long serialVersionUID = 1L;
}