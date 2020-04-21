/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysRoleController
 * Author:   Administrator
 * Date:     2017/12/7 21:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ls.common.JsonData;
import com.ls.dto.AclModuleLevelDto;
import com.ls.model.SysRole;
import com.ls.model.SysUser;
import com.ls.param.RoleParam;
import com.ls.service.*;
import com.ls.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  角色
 * @author Administrator
 * @create 2017/12/7
 * @since 1.0.0
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/role.do")
    @ResponseBody
    private ModelAndView page(){
        return new ModelAndView("role");
    }

    @RequestMapping("/save.php")
    @ResponseBody
    private JsonData save(RoleParam param){
        sysRoleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.php")
    @ResponseBody
    private JsonData update(RoleParam param){
        sysRoleService.update(param);
        return JsonData.success();
    }


    @RequestMapping("/test.do")
    @ResponseBody
    private ModelAndView test(){
        return new ModelAndView("active/active");
    }

    @RequestMapping("/list.php")
    @ResponseBody
    private JsonData list(RoleParam param){
        List<SysRole> roleList = sysRoleService.getAll();
        return JsonData.success(roleList);
    }

    @RequestMapping("roleTree.php")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId){
        List<AclModuleLevelDto> aclModuleLevelDtos = sysTreeService.roleTree(roleId);
       return JsonData.success(aclModuleLevelDtos);
    }

    @RequestMapping("changeAcls.php")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId,@RequestParam("aclIds") String aclIds){
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    @RequestMapping("users.php")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId){
        List<SysUser> seclestdUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unSeclestdUserList = Lists.newArrayList();

        //获取ids
        Set<Integer> seclestdUserIdSet = seclestdUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for(SysUser user : allUserList){
            if(user.getStatus() == 1 && !seclestdUserIdSet.contains(user.getId())){
                //未选中列表
                unSeclestdUserList.add(user);
            }
        }
        System.out.println("seclestdUserList: "+seclestdUserList.toString());
        //过滤
//        seclestdUserList = seclestdUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        System.out.println("seclestdUserList: "+seclestdUserList.toString());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected",seclestdUserList);
        map.put("unSeclected", unSeclestdUserList);
        return JsonData.success(map);
    }

    @RequestMapping("changeUsers.php")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId,@RequestParam(value = "userIds", required = false,defaultValue = "") String userIds){
        List<Integer> aclIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, aclIdList);
        return JsonData.success();
    }
}