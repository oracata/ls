/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysAclModuleServiceImpl
 * Author:   Administrator
 * Date:     2017/12/5 18:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service.Impl;

import com.google.common.base.Preconditions;
import com.ls.common.RequestHolder;
import com.ls.dao.SysAclMapper;
import com.ls.dao.SysAclModuleMapper;
import com.ls.exception.ParamException;
import com.ls.model.SysAclModule;
import com.ls.param.AclModuleParam;
import com.ls.service.SysAclModuleService;
import com.ls.util.BeanValidator;
import com.ls.util.IpUtil;
import com.ls.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  权限模块实现
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    private static Logger log = LoggerFactory.getLogger(SysAclModuleServiceImpl.class);
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 保存
     * @param param
     */
    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }

        //组装数据
        SysAclModule aclModule = new SysAclModule();
        aclModule.setName(param.getName());
        aclModule.setParentId(param.getParentId());
        aclModule.setSeq(param.getSeq());
        aclModule.setStatus(param.getStatus());
        aclModule.setRemark(param.getRemark());
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());

        sysAclModuleMapper.insertSelective(aclModule);
//        sysLogService.saveAclModuleLog(null, aclModule);
    }

    /**
     * 更新
     * @param param
     */
    public void update(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        //组装数据
        SysAclModule after = new SysAclModule();
        after.setId(param.getId());
        after.setName(param.getName());
        after.setParentId(param.getParentId());
        after.setSeq(param.getSeq());
        after.setStatus(param.getStatus());
        after.setRemark(param.getRemark());

        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
//        sysLogService.saveAclModuleLog(before, after);
    }

    /**
     * 删除
     * @param aclModuleId
     */
    public void delete(int aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            throw new ParamException("当前模块下面有子模块，无法删除");
        }
        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
            throw new ParamException("当前模块下面有用户，无法删除");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }


    /**
     *  更新相关子权限
     * @param before
     * @param after
     */
    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
        log.info("sysAclModuleMapper updateWithChild:"+after.toString());
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    /**
     * 检查同一层级下是否存在相同名称的权限模块
     * @param parentId
     * @param aclModuleName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
    }

    /**
     * 获取层级
     * @param aclModuleId
     * @return
     */
    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }
}