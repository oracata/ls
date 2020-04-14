/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysUserServiceIpml
 * Author:   Administrator
 * Date:     2017/12/5 13:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service.Impl;

import com.google.common.base.Preconditions;
import com.ls.beans.PageQuery;
import com.ls.beans.PageResult;
import com.ls.common.RequestHolder;
import com.ls.dao.SysUserMapper;
import com.ls.exception.ParamException;
import com.ls.model.SysUser;
import com.ls.param.UserParam;
import com.ls.service.SysUserService;
import com.ls.util.BeanValidator;
import com.ls.util.DynamicDataSourceHolder;
import com.ls.util.MD5Util;
import com.ls.util.PassWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *  用户管理实现
 * @author Administrator
 * @create 2017/12/5
 * @since 1.0.0
 */
@Service
public class SysUserServiceIpml implements SysUserService{

    private static Logger log = LoggerFactory.getLogger(SysUserServiceIpml.class);
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 保存新增用户
     */
    public void save(UserParam userParam){
        //检测对象
        BeanValidator.check(userParam);
        if(checkTelephoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("手机号已被占用");
        }
        if(checkEmailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已被占用");
        }
        //TODO 随机生成密码，然后MD5加密
        String password = MD5Util.encrypt(PassWordUtil.randomPassword());

        //组装对象
        SysUser sysUser = new SysUser();
        sysUser.setUsername(userParam.getUsername());
        sysUser.setPassword(password);
        sysUser.setDeptId(userParam.getDeptId());
        sysUser.setTelephone(userParam.getTelephone());
        sysUser.setStatus(userParam.getStatus());
        sysUser.setMail(userParam.getMail());
        sysUser.setRemark(userParam.getRemark());
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
//        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());

        //TODO: sendEmail

        sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 通过用户名获取用户信息
     * @param keyword
     * @return
     */
    public SysUser findByKeyWord(String keyword) {
        DynamicDataSourceHolder.setDataSource("mysql");
        return sysUserMapper.findByKeyword(keyword);
    }

    /**
     * 更新用户
     */
    public void update(UserParam userParam){
        //检测对象
        BeanValidator.check(userParam);
        if(checkTelephoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("手机号已被占用");
        }
        if(checkEmailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已被占用");
        }
        //检测对象是否存在
        SysUser before = sysUserMapper.selectByPrimaryKey(userParam.getId());
        Preconditions.checkNotNull(before,"待更新的用户不存在");

        //组装对象
        SysUser after = new SysUser();
        after.setId(userParam.getId());
        after.setUsername(userParam.getUsername());
        after.setDeptId(userParam.getDeptId());
        after.setTelephone(userParam.getTelephone());
        after.setStatus(userParam.getStatus());
        after.setMail(userParam.getMail());
        after.setRemark(userParam.getRemark());
        sysUserMapper.updateByPrimaryKeySelective(after);

    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }


    /**
     * 检测邮箱是否存在
     * @return
     */
    public boolean checkEmailExist(String mail, Integer userId){
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    /**
     * 检测手机号码是否存在
     * @return
     */
    public boolean checkTelephoneExist(String phone, Integer userId){
        return sysUserMapper.countByMail(phone, userId) > 0;
    }

    /**
     * 分页获取用户信息
     * @param deptId
     * @param pageQuery
     * @return
     */
    public PageResult<SysUser> getPageByDeptId(Integer deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId);
        log.info("getPageByDeptIdcount:"+count);
        if( count > 0 ) {
            List<SysUser> userList = sysUserMapper.getPageByDeptId(deptId,pageQuery);
            log.info("userList:"+userList.toString());
            PageResult pageResult = new PageResult();
            pageResult.setData(userList);
            pageResult.setTotal(count);
            return pageResult;
        }
        return new PageResult();
    }

}