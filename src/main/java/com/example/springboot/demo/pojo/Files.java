package com.example.springboot.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName sys_file
 */
@TableName(value ="sys_file")
@Data
public class Files implements Serializable {
    @TableId( value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private String type;

    private Long size;

    private String url;

    private Boolean isDelete;

    private Boolean enable;
    private String md5;

    private static final long serialVersionUID = 1L;
}