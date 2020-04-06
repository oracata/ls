package com.ls.service;

import com.ls.model.SysUser;

import java.util.List;

/**
 *
 * @author Administrator
 * @create 2017/12/8
 * @since 1.0.0
 */
public interface SysRoleUserService {

    public List<SysUser> getListByRoleId(int roleId);

    void changeRoleUsers(int roleId, List<Integer> userIds);
}