package com.personal.demo.config.shiro;

import com.personal.demo.Utils.Md5Util;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * <p>
 * shiro的配置类
 * </p>
 *
 * @author sen
 * @since 2018-09-29
 */
@Configuration
public class ShiroConfig {

    @Bean
    public RetryLimitCredentialsMatcher getRetryLimitCredentialsMatcher() {
        //密码采用MD5加密，并且一分钟内限制登录失败次数
        RetryLimitCredentialsMatcher rm = new RetryLimitCredentialsMatcher(getCacheManager());
        rm.setHashAlgorithmName("md5");
        rm.setHashIterations(Md5Util.HASHITERATIONS);
        return rm;
    }

    @Bean
    public EhCacheManager getCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache.xml");
        return ehCacheManager;
    }

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager securityManager ) {
        ShiroFilterFactoryBean filterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        /**
         * shiro内置过滤器，可以实现权限相关拦截
         *  常用的过滤器：
         *  anon:无需认证
         *  authc:必须认证
         *  user:如果使用rememberme的功能可以直接访问
         *  perms:该资源必须必须得到资源权限才能访问
         *  role：该资源必须得到角色权限才可以访问
         */


        //        默认跳转页面
        filterFactoryBean.setLoginUrl("/do/login");

        Map filterMap=new LinkedHashMap();
        //登录页放行
        filterMap.put("/do/login", "anon");

        //设置account为test的放行页面
//        filterMap.put("/go/index", "perms[test:index]");
//        设置pers为admin的放行页面
//        filterMap.put("/go/index", "roles[admin]");

//        //初始页拦截
//        filterMap.put("/", "authc");
        //go下面的连接拦截
        filterMap.put("/go/**", "authc");
        //退出
        filterMap.put("/logout", "logout");

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //设置无权限页面
        filterFactoryBean.setUnauthorizedUrl("/do/unAuthor");

        return filterFactoryBean;
    }


    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("getShiroRealm") ShiroRealm shiroRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联realam
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    /**
     * 创建realm
     */
    @Bean(name = "getShiroRealm")
    public ShiroRealm getShiroRealm(){
        ShiroRealm realm=new ShiroRealm();
        realm.setCredentialsMatcher(getRetryLimitCredentialsMatcher());
        return realm;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean //加入注解的使用，不加入这个注解不生效
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("defaultWebSecurityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor as = new AuthorizationAttributeSourceAdvisor();
        as.setSecurityManager(securityManager);
        return as;
    }

}

