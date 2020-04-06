/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysAclServiceImpl
 * Author:   Administrator
 * Date:     2017/12/5 21:04
 * Description: 权限点实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service.Impl;

import com.google.common.base.Preconditions;
import com.ls.beans.PageQuery;
import com.ls.beans.PageResult;
import com.ls.common.RequestHolder;
import com.ls.dao.SysAclMapper;
import com.ls.exception.ParamException;
import com.ls.model.SysAcl;
import com.ls.param.AclParam;
import com.ls.service.SysAclService;
import com.ls.util.BeanValidator;
import com.ls.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 〈权限点实现〉
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Service
public class SysAclServiceImpl implements SysAclService {

    private static Logger log = LoggerFactory.getLogger(SysAclServiceImpl.class);
    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 保存
     * @param aclParam
     */
    public void save(AclParam aclParam) {
        BeanValidator.check(aclParam);
        if(checkExist(aclParam.getAclModuleId(),aclParam.getName(), aclParam.getId())){
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }

        //组装数据
        SysAcl sysAcl = new SysAcl();
        sysAcl.setName(aclParam.getName());
        sysAcl.setAclModuleId(aclParam.getAclModuleId());
        sysAcl.setUrl(aclParam.getUrl());
        sysAcl.setType(aclParam.getType());
        sysAcl.setStatus(aclParam.getStatus());
        sysAcl.setSeq(aclParam.getSeq());
        sysAcl.setRemark(aclParam.getRemark());

        sysAcl.setCode(generateCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperateTime(new Date());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        //入库
        sysAclMapper.insertSelective(sysAcl);
//        sysLogService.saveAclLog(null, acl);
    }

    /**
     * 权限码
     * @return
     */
    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

    /**
     * 更新操作
     * @param aclParam
     */
    public void update(AclParam aclParam) {
        BeanValidator.check(aclParam);
        if(checkExist(aclParam.getAclModuleId(),aclParam.getName(), aclParam.getId())){
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }

        SysAcl before = sysAclMapper.selectByPrimaryKey(aclParam.getId());
        Preconditions.checkNotNull(before,"待更新的权限点不存在");

        //组装数据
        SysAcl after = new SysAcl();
        after.setName(aclParam.getName());
        after.setAclModuleId(aclParam.getAclModuleId());
        after.setUrl(aclParam.getUrl());
        after.setType(aclParam.getType());
        after.setStatus(aclParam.getStatus());
        after.setSeq(aclParam.getSeq());
        after.setRemark(aclParam.getRemark());

//        after.setCode(generateCode());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        //入库
        sysAclMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 分页
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            PageResult<SysAcl> pageResult = new PageResult<SysAcl>();
            pageResult.setTotal(count);
            pageResult.setData(aclList);
            return pageResult;
        }
        return new  PageResult<SysAcl>();
    }

    /**
     * 检测当前权限模块是否存在相同的名称
     * @param aclModuleId
     * @param name
     * @param id
     * @return
     */
    public boolean checkExist(int aclModuleId, String name, Integer id){
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }
}