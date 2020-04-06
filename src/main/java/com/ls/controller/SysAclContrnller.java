/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysAclContrnller
 * Author:   Administrator
 * Date:     2017/12/5 21:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.controller;

import com.google.common.collect.Maps;
import com.ls.beans.PageQuery;
import com.ls.common.JsonData;
import com.ls.param.AclParam;
import com.ls.service.SysAclService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 *  权限点管理
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclContrnller {

    @Resource
    private SysAclService sysAclService;
//    @Resource
//    private SysRoleService sysRoleService;

    @RequestMapping("/save.php")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.php")
    @ResponseBody
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.php")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

    @RequestMapping("acls.php")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId) {
        Map<String, Object> map = Maps.newHashMap();
//        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
//        map.put("roles", roleList);
//        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return JsonData.success(map);
    }
}