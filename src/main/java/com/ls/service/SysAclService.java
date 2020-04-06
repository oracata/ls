/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysAclService
 * Author:   Administrator
 * Date:     2017/12/5 21:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import com.ls.beans.PageQuery;
import com.ls.beans.PageResult;
import com.ls.model.SysAcl;
import com.ls.param.AclParam;

/**
 *  权限点接口
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
public interface SysAclService {

    void save(AclParam aclParam);

    void update(AclParam aclParam);

    PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery);

}