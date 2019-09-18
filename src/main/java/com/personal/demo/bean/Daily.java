package com.personal.demo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
@TableName("daily_record")
public class Daily {
    @TableId(value = "id_",type = IdType.UUID)
    private String  id;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("date")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @TableField("interest")
    private String interest;

    @TableField("picture_url")
    private String pictureUrl;


    @TableField(exist=false)
    private Integer page;

    @TableField(exist=false)
    private Integer limit;

    @TableField(exist=false)
    private String selectDate;

    @TableField(exist=false)
    private String startDate;

    @TableField(exist=false)
    private String endDate;
}
