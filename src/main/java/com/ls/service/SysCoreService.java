/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysCoreService
 * Author:   Administrator
 * Date:     2017/12/8 15:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import com.ls.model.SysAcl;

import java.util.List;

/**
 *
 * @author Administrator
 * @create 2017/12/8
 * @since 1.0.0
 */
public interface SysCoreService {

    List<SysAcl> getCurrentUserAclList();

    List<SysAcl> getRoleAclList(int roleId);

    boolean hasUrlAcl(String url);

    List<SysAcl> getCurrentUserAclListFromCache();

}