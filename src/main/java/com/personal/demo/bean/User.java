package com.personal.demo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: shadow
 * @Date: 2019/4/17 10:27
 * @Description:
 */
@Data
@ToString
@TableName("user")
public class User extends Model<User>{

    @TableId("id_")
    private String id;
    @TableField("account_")
    private String account;
    @TableField("name_")
    private String name;
    @TableField("password_")
    private String password;
    @TableField("email_")
    private String email;
    @JSONField(format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField("create_time_")
    private Date createTime;
    @TableField("pers_")
    private String pers;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
