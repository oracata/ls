package com.ls.common;

import com.ls.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *  Http工具类
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
//要被spring管理，要在spring-servlet.xml中配置
public class HttpInterceptor extends HandlerInterceptorAdapter{

    private static final Logger log = LoggerFactory.getLogger(HandlerInterceptor.class);

    private static final String START_TIME = "requestStartTime";
    /**
     * 请求前，处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        log.info("request start. url:{},param:{}",url, JsonMapper.objToString(parameterMap));
        //请求开始时间
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME,start);
        return true;
    }

    /**
     * 正常处理完后，处理
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        //请求开始时间
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request finished. url:{},param:{},cost:{}",url, JsonMapper.objToString(parameterMap),end-start);

        removeThreadLoaclhostInfo();
    }

    /**
     * 正常、异常处理完后，都处理（与postHandle的不同点）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        //请求开始时间
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request exception. url:{},,param:{},cost:{}",url, JsonMapper.objToString(parameterMap),end-start);

        removeThreadLoaclhostInfo();
    }

    public void removeThreadLoaclhostInfo(){
        RequestHolder.remove();
    }
}