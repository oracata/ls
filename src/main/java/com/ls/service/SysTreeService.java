package com.ls.service;

import com.ls.dto.AclModuleLevelDto;
import com.ls.dto.DeptLevelDto;

import java.util.List;

/**
 *  树结构逻辑类
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
public interface SysTreeService {

    public List<DeptLevelDto> deptTree();

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList);

    public List<AclModuleLevelDto> aclModuleTree();

    public List<AclModuleLevelDto> roleTree(int roleId);

}