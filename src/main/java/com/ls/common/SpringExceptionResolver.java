package com.ls.common;

import com.ls.exception.LsException;
import com.ls.exception.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  全局异常处理类
 * @author Administrator
 * @create 2017/12/3
 * @since 1.0.0
 */
public class SpringExceptionResolver implements HandlerExceptionResolver{
    Logger logger = LoggerFactory.getLogger(SpringExceptionResolver.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //请求的url
        String url = request.getRequestURI().toString();
        ModelAndView mv;
        String defaultMsg = "System error";

        //.json , .page
        //要求项目中所有请求json数据，都是用.php 结尾
        if(url.endsWith(".php")){
            if(ex instanceof LsException || ex instanceof ParamException){
                JsonData result = JsonData.fail(ex.getMessage());
                mv = new ModelAndView("jsonView",result.toMap());
            }else {
                logger.error("unknow json execption,url:"+url,ex);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView",result.toMap());
            }
        }else if(url.endsWith(".do")){ //要求项目中所有请求page页面，都是用.do 结尾
            logger.error("unknow page execption,url:"+url,ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception",result.toMap());
        }else {
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView",result.toMap());
        }
        return mv;
    }
}