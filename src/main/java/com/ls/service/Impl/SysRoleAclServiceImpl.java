package com.ls.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ls.common.RequestHolder;
import com.ls.dao.SysRoleAclMapper;
import com.ls.model.SysRoleAcl;
import com.ls.service.SysRoleAclService;
import com.ls.util.IpUtil;
import com.ls.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Administrator
 * @create 2017/12/8
 * @since 1.0.0
 */
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService{

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    /**
     * 判断保存的数据，是不是没有改变
     * @param roleId
     * @param aclIdList
     */
    @Override
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> originAclList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if(originAclList.size() == aclIdList.size()){
            Set<Integer> originAclSet = Sets.newHashSet(originAclList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclSet.remove(aclIdSet);
            if(CollectionUtils.isEmpty(aclIdSet)){
                return;
            }
        }

        updateRoleAcls(roleId, aclIdList);
    }

    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList){
        sysRoleAclMapper.deleteByRoleId(roleId);
        if(CollectionUtils.isEmpty(aclIdList)){
            return;
        }

        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList){
            SysRoleAcl roleAcl = new SysRoleAcl();
            roleAcl.setRoleId(roleId);
            roleAcl.setAclId(aclId);
            roleAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
            roleAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            roleAcl.setOperateTime(new Date());
            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }


}