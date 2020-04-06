package com.ls.service.Impl;

import com.google.common.base.Preconditions;
import com.ls.common.RequestHolder;
import com.ls.dao.SysRoleMapper;
import com.ls.dto.AclModuleLevelDto;
import com.ls.exception.ParamException;
import com.ls.model.SysRole;
import com.ls.param.RoleParam;
import com.ls.service.SysRoleService;
import com.ls.util.BeanValidator;
import com.ls.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  角色
 * @author Administrator
 * @create 2017/12/7
 * @since 1.0.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     *
     * @param param
     */
    public void save(RoleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getName(), param.getId())){
            throw new ParamException("角色名称已经存在");
        }

        //组装数据
        SysRole role = new SysRole();
        role.setName(param.getName());
        role.setStatus(param.getStatus());
        role.setType(param.getType());
        role.setRemark(param.getRemark());

        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());

        //入库
        sysRoleMapper.insertSelective(role);
    }

    /**
     * 更新
     * @param param
     */
    public void update(RoleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getName(), param.getId())){
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before,"待更新的角色不存在");

        //组装数据
        SysRole role = new SysRole();
        role.setId(param.getId());
        role.setName(param.getName());
        role.setStatus(param.getStatus());
        role.setType(param.getType());
        role.setRemark(param.getRemark());

        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());

        //入库
        sysRoleMapper.updateByPrimaryKeySelective(role);
    }

    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    public int countByName(String name, String id) {
        return 0;
    }

    /**
     * 树
     * @param roleId
     * @return
     */
    public List<AclModuleLevelDto> roleTree(int roleId) {
        return null;
    }


    /**
     * 检测角色名称是否存在
     * @param name
     * @param id
     * @return
     */
    private boolean checkExist(String name, Integer id){
        return sysRoleMapper.countByName(name, id) > 0;
    }
}