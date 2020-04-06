/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysAclModuleService
 * Author:   Administrator
 * Date:     2017/12/5 18:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import com.ls.param.AclModuleParam;

/**
 *  权限模块接口
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
public interface SysAclModuleService {

    void save(AclModuleParam aclModuleParam);

    void update(AclModuleParam param);

    void delete(int aclModuleId);
}