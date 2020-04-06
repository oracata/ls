package com.ls.service.Impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.ls.dao.SysAclMapper;
import com.ls.dao.SysAclModuleMapper;
import com.ls.dao.SysDeptMapper;
import com.ls.dto.AclDto;
import com.ls.dto.AclModuleLevelDto;
import com.ls.dto.DeptLevelDto;
import com.ls.model.SysAcl;
import com.ls.model.SysAclModule;
import com.ls.model.SysDept;
import com.ls.service.SysTreeService;
import com.ls.service.SysCoreService;
import com.ls.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈树逻辑实现类〉
 *
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
@Service
public class SysTreeServiceImpl implements SysTreeService{

    private static Logger log = LoggerFactory.getLogger(SysTreeServiceImpl.class);

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysCoreService SysCoreService;
    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 单位树
     * @return
     */
    public List<DeptLevelDto> deptTree(){
        //获取所有单位
        List<SysDept> deptList = sysDeptMapper.getAllDept();

        List<DeptLevelDto> dtoList = Lists.newArrayList();
        for (SysDept dept : deptList){
            //把dept相同key的value拷贝到dto中
            DeptLevelDto dto = DeptLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptLevelDtoListToTree(dtoList);
    }

    /**
     *  把单位List转换成树结构
     * @param deptLevelDtoList
     * @return
     */
    public List<DeptLevelDto> deptLevelDtoListToTree( List<DeptLevelDto> deptLevelDtoList){
        if(CollectionUtils.isEmpty(deptLevelDtoList)){
            //返回空的List
            return Lists.newArrayList();
        }
        /**
         * multimap允许重复元素，map不允许有重复，相当于Map<String, List<Object>>
         */
        //key -> value
        // level -> [dept1, dept2, dept3, ...]
        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();

        List<DeptLevelDto> rootList = Lists.newArrayList();
        //
        for(DeptLevelDto dto : deptLevelDtoList){
            levelDeptMap.put(dto.getLevel(),dto);
            if(LevelUtil.ROOT.equals(dto.getLevel())){
                //顶级根层级
                rootList.add(dto);
            }
        }

        //根层级rootList 按照seq从小到大排序
        //Comparator 比较器
        Collections.sort(rootList, deptLevelDtoComparator);
//        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
//            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
//                //优先
//                return o1.getSeq() - o2.getSeq();
//            }
//        });
        log.info("transforDeptTree before:" + rootList.toString());
        //递归生成树------所有单位，根层级，Multimap  --对deptLevelDtoList进行排序
        transforDeptTree(deptLevelDtoList, LevelUtil.ROOT, levelDeptMap);
        log.info("transforDeptTree after:" + rootList.toString());
        return rootList;

    }

    //单位递归排序生成树
    public void transforDeptTree( List<DeptLevelDto> deptLevelDtoList, String level, Multimap<String, DeptLevelDto> levelDeptMap){
        log.info("Multimap : "+ levelDeptMap.toString());
        for (int i = 0; i < deptLevelDtoList.size(); i++) {
            //遍历该层级的每个元素
            DeptLevelDto deptLevelDto = deptLevelDtoList.get(i);
            //处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level,deptLevelDto.getId());
            //处理下一层
            List<DeptLevelDto> tempdeptLevelDtoList = (List<DeptLevelDto>) levelDeptMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(tempdeptLevelDtoList)){
                //排序
                Collections.sort(tempdeptLevelDtoList, deptLevelDtoComparator);
                //设置下一个单位
                deptLevelDto.setDeptList(tempdeptLevelDtoList);
                //进入到下一层处理
                transforDeptTree(tempdeptLevelDtoList,nextLevel,levelDeptMap);
            }
        }
    }

    /**
     * DeptLevelDto比较器
     */
    public Comparator<DeptLevelDto> deptLevelDtoComparator = new Comparator<DeptLevelDto>() {
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


    /**
     * 权限模块
     * @param dtoList
     * @return
     */
    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        Collections.sort(rootList, aclModuleSeqComparator);
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    /**
     *  权限模块排序
     * @param dtoList
     * @param level
     * @param levelAclModuleMap
     */
    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList, aclModuleSeqComparator);
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }


    /**
     * 权限模块比较器
     */
    public Comparator<AclModuleLevelDto> aclModuleSeqComparator = new Comparator<AclModuleLevelDto>() {
        public int compare(AclModuleLevelDto o1, AclModuleLevelDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    /**
     *
     * @return
     */
    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    /**
     * 权限点
     * @return
     */
    public List<AclModuleLevelDto> roleTree(int roleId) {
        // 1、当前用户已分配的权限点
        List<SysAcl> userAclList = SysCoreService.getCurrentUserAclList();
        // 2、当前角色分配的权限点
        List<SysAcl> roleAclList = SysCoreService.getRoleAclList(roleId);
        // 3、当前系统所有权限点
        List<AclDto> aclDtoList = Lists.newArrayList();

        //jdk1.8
        Set<Integer> userAclIdSet = userAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());
//        Set<Integer> userAclIdSet = new HashSet<Integer>();
//        Set<Integer> roleAclIdSet = new HashSet<Integer>();
//        for (SysAcl sysAcl : userAclList){
//            userAclIdSet.add(sysAcl.getId());
//        }
//        for (SysAcl sysAcl : roleAclList){
//            roleAclIdSet.add(sysAcl.getId());
//        }


        List<SysAcl> allAclList = sysAclMapper.getAll();
        for (SysAcl acl : allAclList) {
            AclDto dto = AclDto.adapt(acl);
            if (userAclIdSet.contains(acl.getId())) {
                dto.setHasAcl(true);
            }
            if (roleAclIdSet.contains(acl.getId())) {
                dto.setChecked(true);
            }
            aclDtoList.add(dto);
        }
        return aclListToTree(aclDtoList);
    }

    /**
     * List 2 Tree
     * @param aclDtoList
     * @return
     */
    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for(AclDto acl : aclDtoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    /**
     * 权限点递归排序
     * @param aclModuleLevelList
     * @param moduleIdAclMap
     */
    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclDto> aclDtoList = (List<AclDto>)moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, aclSeqComparator);
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    public Comparator<AclDto> aclSeqComparator = new Comparator<AclDto>() {
        public int compare(AclDto o1, AclDto o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };


}