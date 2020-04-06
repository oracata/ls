/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: RequestHolder
 * Author:   Administrator
 * Date:     2017/12/5 16:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.common;

import com.ls.model.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 *  线程共享 java8
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
public class RequestHolder {

    //当前线程池
    private static final ThreadLocal<SysUser>  userHolder = new ThreadLocal<SysUser>();

    private static final ThreadLocal<HttpServletRequest>  requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(SysUser sysUser){
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request){
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser(){
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest(){
        return requestHolder.get();
    }

    //防止内存泄露
    //在HttpInterceptor拦截器中使用
    public static void remove(){
        //用完清空一下
        userHolder.remove();
        requestHolder.remove();
    }
}