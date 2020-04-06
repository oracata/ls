package com.ls.service;

import com.ls.param.DeptParam;

/**
 *
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
public interface SysDeptService {

    /**
     * 保存
     * @param deptParam
     */
    void save(DeptParam deptParam);

    void update(DeptParam deptParam);
}