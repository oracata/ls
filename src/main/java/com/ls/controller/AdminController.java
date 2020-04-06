/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: AdminController
 * Author:   Administrator
 * Date:     2017/12/5 14:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  管理员
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index.do")
    public ModelAndView page(){
        return new ModelAndView("sys/admin/index");
    }

}