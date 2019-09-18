package com.personal.demo.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.personal.demo.bean.User;
import com.personal.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("login")
@Slf4j
public class LoginController {

    @RequestMapping("checking")
    @ResponseBody
    public Map checking(User user) {
        Map retuenMap = new HashMap();
        try {
            //封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword());
            //获取subject
            Subject subject = SecurityUtils.getSubject();
            //执行登录方法
            subject.login(token);

            retuenMap.put("msg", "登录成功");
            retuenMap.put("code", 1);
            retuenMap.put("url", "go/admin/index");


        } catch (ExcessiveAttemptsException e) {
            retuenMap.put("msg", "已超出登录最大错误次数");
            retuenMap.put("code", 0);
            return retuenMap;
        } catch (Exception e) {
            retuenMap.put("msg", "账户或密码不存在");
            retuenMap.put("code", 0);
            return retuenMap;
        }
        return retuenMap;
    }

    @RequestMapping("logout")
    public String logout() {
        log.info("--------用户退出---------");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/do/login";
    }
}
