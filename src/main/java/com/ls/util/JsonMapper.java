package com.ls.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
@Slf4j
public class JsonMapper {

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //config
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * object转String
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String objToString(T t){
        //判断是否为空
        if(t == null){
            return null;
        }
        //不为空
        try {
            //如果t是String，强转；
            return t instanceof String ? (String)t :objectMapper.writeValueAsString(t);
        }catch (Exception e){
            logger.warn("parse object to String exception,error:{}",e);
            return null;
        }
    }

    /**
     * String转Object
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T StringToObject(String str, TypeReference<T> typeReference){
        //判断是否为空
        if(str == null || typeReference == null){
            return null;
        }
        //不为空
        try {
            //如果str是Obj，强转；
            return (T)(typeReference.getType().equals(String.class) ? str :objectMapper.readValue(str,typeReference));
        }catch (Exception e){
            logger.warn("parse String to Obj exception,String{},typeReference<T>:{},error:{}",str,typeReference.getType(),e.getMessage());
            return null;
        }
    }
}