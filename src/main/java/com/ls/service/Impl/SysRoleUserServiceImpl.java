package com.ls.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ls.common.RequestHolder;
import com.ls.dao.SysRoleUserMapper;
import com.ls.dao.SysUserMapper;
import com.ls.model.SysRoleUser;
import com.ls.model.SysUser;
import com.ls.service.SysRoleUserService;
import com.ls.util.IpUtil;
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
public class SysRoleUserServiceImpl implements SysRoleUserService{

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if(CollectionUtils.isEmpty(userIdList)){
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    @Override
    public void changeRoleUsers(int roleId, List<Integer> userIds) {
        List<Integer> originUserList = sysRoleUserMapper.getUserIdListByRoleIdList(Lists.newArrayList(roleId));
        if(originUserList.size() == userIds.size()){
            Set<Integer> originUserSet = Sets.newHashSet(originUserList);
            Set<Integer> userIdSet = Sets.newHashSet(userIds);
            originUserSet.remove(userIdSet);
            if(CollectionUtils.isEmpty(originUserSet)){
                return;
            }
        }
        updateRoleUsers(roleId, userIds);
    }

    @Transactional
    public void updateRoleUsers(int roleId, List<Integer> userList ){
        sysRoleUserMapper.deleteByRoleId(roleId);
        if(CollectionUtils.isEmpty(userList)){
            return;
        }
        List<SysRoleUser> sysRoleUserList = Lists.newArrayList();
        for(Integer userId : userList){
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(userId);
            sysRoleUser.setOperator(RequestHolder.getCurrentUser().getUsername());
            sysRoleUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            sysRoleUser.setOperateTime(new Date());
            sysRoleUserList.add(sysRoleUser);
        }

        sysRoleUserMapper.batchInsert(sysRoleUserList);
    }
}