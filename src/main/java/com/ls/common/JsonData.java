package com.ls.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Json返回类
 * @author Administrator
 * @create 2017/12/3
 * @since 1.0.0
 */
@Getter
@Setter
public class JsonData {

    //成功标志
    private boolean ret;
    //异常信息
    private String msg;
    //正常返回的数据
    private Object data;

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    /**
     * 成功：返回的数据及信息
     * @param msg
     * @param obj
     * @return
     */
    public static JsonData success(String msg, Object obj){
       JsonData jsonData = new JsonData(true);
       jsonData.data = obj;
       jsonData.msg = msg;
       return jsonData;
    }

    /**
     * 成功：返回数据
     * @param obj
     * @return
     */
    public static JsonData success(Object obj){
        JsonData jsonData = new JsonData(true);
        jsonData.data = obj;
        return jsonData;
    }

    /**
     * 成功：返回成功标志
     * @return
     */
    public static JsonData success(){
        JsonData jsonData = new JsonData(true);
        return jsonData;
    }

    /**
     * 出错时返回信息
     * @param msg
     * @return
     */
    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.msg = msg;
        return jsonData;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("ret",ret);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }

}