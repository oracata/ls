package com.ls.service;

import java.util.List;

/**
 *
 * @author Administrator
 * @create 2017/12/8
 * @since 1.0.0
 */
public interface SysRoleAclService {

    void changeRoleAcls(Integer roleId, List<Integer> aclIdList);
}