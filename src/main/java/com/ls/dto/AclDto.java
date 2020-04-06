/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: AclDto
 * Author:   Administrator
 * Date:     2017/12/5 19:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.dto;

import com.ls.model.SysAcl;
import org.springframework.beans.BeanUtils;

/**
 *  权限
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
public class AclDto extends SysAcl{

    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHasAcl() {
        return hasAcl;
    }

    public void setHasAcl(boolean hasAcl) {
        this.hasAcl = hasAcl;
    }

    @Override
    public String toString() {
        return "AclDto{" +
                "checked=" + checked +
                ", hasAcl=" + hasAcl +
                '}';
    }
}