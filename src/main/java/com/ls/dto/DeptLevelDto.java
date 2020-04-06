package com.ls.dto;

import com.google.common.collect.Lists;
import com.ls.model.SysDept;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 *  单位
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
public class DeptLevelDto extends SysDept{

    //包含自己，组成属性结构
    private List<DeptLevelDto> deptList = Lists.newArrayList();

    //适配方法
    public static DeptLevelDto adapt(SysDept dept){
        DeptLevelDto dto = new DeptLevelDto();
        //复制相同的属性值
        BeanUtils.copyProperties(dept,dto);
        return dto;
    }

    public List<DeptLevelDto> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptLevelDto> deptList) {
        this.deptList = deptList;
    }
}