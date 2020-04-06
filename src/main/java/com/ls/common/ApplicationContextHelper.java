package com.ls.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  上下文帮助类
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
//被spring管理，要在spring-servlet.xml中配置
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware{

    //全局的applicationContext
    private static ApplicationContext applicationContext;

    //系统启动的时候，会把ApplicationContext注入到这里
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        //把系统的ApplicationContext给全局ApplicationContext
        applicationContext = context;
    }

    /**
     *  获取Bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T popBean(Class<T> clazz){
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(clazz);
    }
    public static <T> T popBean(String name,Class<T> clazz){
        if(applicationContext == null){
            return null;
        }
        return applicationContext.getBean(name,clazz);
    }
}