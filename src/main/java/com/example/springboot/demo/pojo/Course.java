package com.example.springboot.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName sys_course
 */
@TableName(value ="sys_course")
@Data
public class Course implements Serializable {
    private Integer id;

    private String name;

    private Integer score;

    private String times;

    private Boolean state;

    private Integer teacherId;

    private static final long serialVersionUID = 1L;
}