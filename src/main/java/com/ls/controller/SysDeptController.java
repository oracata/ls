package com.ls.controller;

import com.ls.common.JsonData;
import com.ls.dto.DeptLevelDto;
import com.ls.param.DeptParam;
import com.ls.service.SysDeptService;
import com.ls.service.SysTreeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 *
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.do")
    public ModelAndView page(){
        return new ModelAndView("sys/dept");
    }

    @RequestMapping(value = "/save.php",method = RequestMethod.POST)
    @ResponseBody
    public JsonData save(DeptParam deptParam){
        sysDeptService.save(deptParam);
        return JsonData.success();
    }

    @RequestMapping(value = "/tree.php",method = RequestMethod.GET)
    @ResponseBody
    public JsonData tree(DeptParam deptParam){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    @RequestMapping(value = "/update.php",method = RequestMethod.POST)
    @ResponseBody
    public JsonData update(DeptParam deptParam){
        sysDeptService.update(deptParam);
        return JsonData.success();
    }
}