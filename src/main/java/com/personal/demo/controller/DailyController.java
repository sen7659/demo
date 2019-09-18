package com.personal.demo.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.personal.demo.bean.Daily;
import com.personal.demo.service.DailyService;
import com.personal.demo.service.impl.DailyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("daily")
@Slf4j
public class DailyController {

    @Resource
    DailyService dailyService;


    @RequestMapping("query")
    @ResponseBody
    public Map<String, Object> query(Daily daily) {
        return dailyService.query(daily);
    }

    @RequestMapping("queryOne")
    @ResponseBody
    public Map<String, Object> queryOne(Daily daily) {
        Map<String, Object> map = dailyService.selectMap(new EntityWrapper<Daily>().eq("id_", daily.getId()));
        return map;
    }

    @RequestMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file, HttpServletRequest request,Daily daily) {
        return dailyService.upload(file,request,daily);
    }

    @RequestMapping("uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(MultipartFile file,Daily daily) {
        return dailyService.uploadImg(file,daily);
    }


    @RequestMapping("dele")
    @ResponseBody
    public Map<String, Object> dele(Daily daily) {
        Map map = new HashMap();
        try {
            dailyService.deleteById(daily.getId());
            map.put("code", 1);
            map.put("msg", "删除完毕！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", "删除失败！！");
        }
        return map;
    }

    @RequestMapping("updata")
    @ResponseBody
    public Map<String, Object> updata(MultipartFile file, HttpServletRequest request,Daily daily) {
        return dailyService.updata(file,request,daily);
    }
}
