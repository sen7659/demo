package com.personal.demo.controller;

import com.personal.demo.bean.Daily;
import com.personal.demo.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class WebPageController {
    @Resource
    DailyService dailyService;

    @RequestMapping("/index")
    public String blogIndex(){
        log.debug("跳转到blogIndex");
        return "blog/index";
    }

    @RequestMapping("/do/login")
    public String login(){
        log.debug("跳转到login");
        return "login";
    }

    @RequestMapping("/do/unAuthor")
    public String  unAuthor(){
        log.debug("跳转到unAuthor");
        return "unAuthor";
    }

    @RequestMapping("/go/admin/index")
    public String adminIndex(){
        log.debug("跳转到index");
        return "admin/index";
    }

    @RequestMapping("/go/admin/test")
    public String adminTest(){
        log.debug("跳转到index");
        return "admin/test";
    }

    @RequestMapping("/go/admin/form")
    public String form(){
        log.debug("跳转到form");
        return "admin/form";
    }

    @RequestMapping("/go/admin/table")
    public String btable(){
        log.debug("跳转到table");
        return "admin/table";
    }

    @RequestMapping("/go/admin/editTable")
    public String editTable(){
        log.debug("跳转到editTable");
        return "admin/editTable";
    }

    @RequestMapping("/go/admin/picture")
    public String picture(){
        log.debug("跳转到picture");
        return "admin/picture";
    }

    @RequestMapping("/blog/read")
    public String blogRead(Model model,Daily daily){
        log.debug("跳转到read");
        model.addAttribute("orderType", daily.getId());
        return "blog/read";
    }

    @RequestMapping("/blog/article")
    public String blogArt(){
        log.debug("跳转到read");
        return "blog/article";
    }
}
