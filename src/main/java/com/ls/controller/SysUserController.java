package com.ls.controller;

import com.ls.beans.PageQuery;
import com.ls.beans.PageResult;
import com.ls.common.JsonData;
import com.ls.param.UserParam;
import com.ls.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *  用户管理
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/save.do")
    @ResponseBody
    public JsonData saveUser(UserParam userParam){
    sysUserService.save(userParam);
        return JsonData.success();
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public JsonData updateUser(UserParam userParam){
        sysUserService.update(userParam);
        return JsonData.success();
    }

    @RequestMapping("/page.do")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") Integer deptId, PageQuery pageQuery){
        PageResult result = sysUserService.getPageByDeptId(deptId,pageQuery);
        return JsonData.success(result);
    }
}