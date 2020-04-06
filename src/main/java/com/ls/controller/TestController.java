package com.ls.controller;

import com.ls.common.ApplicationContextHelper;
import com.ls.common.JsonData;
import com.ls.dao.SysAclModuleMapper;
import com.ls.model.SysAclModule;
import com.ls.util.BeanValidator;
import com.ls.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 * @create 2017/12/3
 * @since 1.0.0
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule mouModule = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.objToString(mouModule));
        BeanValidator.check(mouModule);
//        throw new LsException("test exception");
        return JsonData.success("hello,spring!");
    }


}