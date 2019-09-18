package com.personal.demo.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.personal.demo.bean.User;
import com.personal.demo.dao.LoginDao;
import com.personal.demo.dao.UserDao;
import com.personal.demo.service.LoginService;
import com.personal.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;


@Service
public class LoginServicelmpl extends ServiceImpl<LoginDao,User> implements LoginService {

}
