/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: AclModuleLevelDto
 * Author:   Administrator
 * Date:     2017/12/5 19:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.dto;

import com.google.common.collect.Lists;
import com.ls.model.SysAclModule;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *  权限模块适配类，树形结构需要
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
public class AclModuleLevelDto extends SysAclModule{

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }

    public List<AclModuleLevelDto> getAclModuleList() {
        return aclModuleList;
    }

    public void setAclModuleList(List<AclModuleLevelDto> aclModuleList) {
        this.aclModuleList = aclModuleList;
    }

    public List<AclDto> getAclList() {
        return aclList;
    }

    public void setAclList(List<AclDto> aclList) {
        this.aclList = aclList;
    }

    @Override
    public String toString() {
        return "AclModuleLevelDto{" +
                "aclModuleList=" + aclModuleList +
                ", aclList=" + aclList +
                '}';
    }
}