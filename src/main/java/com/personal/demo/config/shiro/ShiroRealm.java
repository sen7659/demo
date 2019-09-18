package com.personal.demo.config.shiro;

import com.personal.demo.Utils.Md5Util;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.personal.demo.bean.User;
import com.personal.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("------授权验证---------");
        String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
        User user =userService.selectById(userId);
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        sai.addRole(user.getPers());
        return sai;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("------登录验证---------");
        //获取token
        String account = (String) authenticationToken.getPrincipal();

        //根据token中的account查询表
        //  SELECT * from user WHERE account_=#{account}
        User user = userService.selectOne(new EntityWrapper<User>().eq("account_", account));

        if (user == null) {
            //用户不存在抛出异常
            throw new UnknownAccountException("用户不存在");
        }
        try {
            //获取登录信息保存至session
            SecurityUtils.getSubject().getSession().setAttribute("userId",user.getId());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //         密码MD5加盐加密
        ByteSource byteSource=ByteSource.Util.bytes(account);//取盐值
        SimpleAuthenticationInfo si = new SimpleAuthenticationInfo(account, Md5Util.getMD5(user.getPassword(),account),byteSource,getName());
        return si;
    }
}
