/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: UserController
 * Author:   Administrator
 * Date:     2017/12/5 14:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.controller;

import com.ls.model.SysUser;
import com.ls.service.SysUserService;
import com.ls.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  用户操作
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/login.do")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyWord(username);
        //登录失败的提示信息
        String errorMsg = "登记失败";
        //
        String ret = request.getParameter("ret");

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (sysUser == null) {
            errorMsg = "查询不到指定的用户";
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "用户名或密码错误";
        } else if (sysUser.getStatus() != 1) {
            errorMsg = "用户已被冻结，请联系管理员";
        } else {
            // 登录成功
            //把当前用户的信息存到session
            request.getSession().setAttribute("user", sysUser);
            //如果ret不为空，跳到ret地址
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.do"); //TODO
            }
        }
        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        String path = "signin.jsp";
        //把数据参数带回去
        request.getRequestDispatcher(path).forward(request, response);

    }

    @RequestMapping("logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //session移除
        request.getSession().invalidate();
        String path = "/signin.jsp";
        response.sendRedirect(path);
    }


}