/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysUserService
 * Author:   Administrator
 * Date:     2017/12/5 13:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import com.ls.beans.PageQuery;
import com.ls.beans.PageResult;
import com.ls.model.SysUser;
import com.ls.param.UserParam;

import java.util.List;

/**
 *  用户接口
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
public interface SysUserService {

    void save(UserParam userParam);

    SysUser findByKeyWord(String keyword);

    boolean checkEmailExist(String mail, Integer userId);

    boolean checkTelephoneExist(String telephone,Integer userId);

    PageResult<SysUser> getPageByDeptId(Integer deptId, PageQuery pageQuery);

    void update(UserParam userParam);

    List<SysUser> getAll();
}