/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysRoleService
 * Author:   Administrator
 * Date:     2017/12/7 21:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import com.ls.model.SysRole;
import com.ls.param.RoleParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  角色接口
 * @author Administrator
 * @create 2017/12/7
 * @since 1.0.0
 */
public interface SysRoleService {

    void save(RoleParam roleParam);

    void update(RoleParam roleParam);

    List<SysRole> getAll();

    int countByName(@Param("name") String name, @Param("id") String id);

    List roleTree(int roleId);
}