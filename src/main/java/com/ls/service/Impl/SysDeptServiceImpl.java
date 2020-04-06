package com.ls.service.Impl;

import com.google.common.base.Preconditions;
import com.ls.common.RequestHolder;
import com.ls.dao.SysDeptMapper;
import com.ls.exception.ParamException;
import com.ls.model.SysDept;
import com.ls.param.DeptParam;
import com.ls.service.SysDeptService;
import com.ls.util.BeanValidator;
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
 *  单位业务逻辑类
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private static Logger log = LoggerFactory.getLogger(SysDeptServiceImpl.class);
    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 保存
     * @param deptParam
     */
    public void save(DeptParam deptParam) {
        //校验数据
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的单位");
        }
        //组装数据
        SysDept sysDept = new SysDept();
        sysDept.setName(deptParam.getName());
        sysDept.setParentId(deptParam.getParentId());
        sysDept.setSeq(deptParam.getSeq());
        sysDept.setRemark(deptParam.getRemark());
        //设置层级
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()),deptParam.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());//TODO
        sysDept.setOperateIp("127.0.0.1");//TODO
        sysDept.setOperateTime(new Date());

        //入库
        sysDeptMapper.insertSelective(sysDept);

    }

    /**
     * 更新
     * @param deptParam
     */
    public void update(DeptParam deptParam) {
        //校验数据
        BeanValidator.check(deptParam);
        //需要更改的单位是否已经存在
        if(checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的单位");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        //检测对象是否为空
        Preconditions.checkNotNull(before,"待更新的单位不存在");
        //需要更改的单位是否已经存在
        if(checkExist(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的单位");
        }
        //组装数据
        SysDept after = new SysDept();
        after.setId(deptParam.getId());
        after.setName(deptParam.getName());
        after.setParentId(deptParam.getParentId());
        after.setSeq(deptParam.getSeq());
        after.setRemark(deptParam.getRemark());
        //设置层级
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()),deptParam.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());//TODO
        after.setOperateIp("127.0.0.1");//TODO
        after.setOperateTime(new Date());

        //更新子单位的信息
        updateWithChild(before, after);
    }

    /**
     * 检测上级部门id为parentId，是否存在单位名称为name的单位
     */
    private boolean checkExist(Integer parentId, String name, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, name, deptId) > 0;
    }

    /**
     * 获取单位层级
     */
    private String getLevel(Integer deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(dept == null){
            return null;
        }
        //返回单位层级
        return dept.getLevel();
    }

    /**
     * 更新子单位的信息
     * 事务监听
     */
    @Transactional
    public void updateWithChild(SysDept before, SysDept after){
        //先更新自己
        sysDeptMapper.updateByPrimaryKey(after);

        //是否要更新子单位信息逻辑
        //更新后
        String newLevelPrefix = after.getLevel();
        //更新前
        String oldLevelPrefix = before.getLevel();
        if(!after.getLevel().equals(before.getLevel())){
            //不一致，需要更新子单位
            //获取子单位列表
            log.info("before.getLevel:"+before.getLevel());
            List<SysDept> sysDeptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            log.info("获取子单位列表:"+sysDeptList.toString());
            if(CollectionUtils.isNotEmpty(sysDeptList)){
                for(SysDept dept : sysDeptList){
                    String level = dept.getLevel();
                    //判断level的前缀是否相等
                    if(level.indexOf(oldLevelPrefix) == 0){
                        //设置新的level--level.substring(oldLevelPrefix.length())：（原0.1.2），截取.1.2
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                log.info("批量更新level:"+sysDeptList.toString());
                //批量更新level
                sysDeptMapper.batchUpdateLevel(sysDeptList);
            }
        }

    }
}