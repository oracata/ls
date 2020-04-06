package com.ls.util;

import org.apache.commons.lang3.StringUtils;

/**
 *  层级工具类
 * @author Administrator
 * @create 2017/12/4
 * @since 1.0.0
 */
public class LevelUtil {

    //分隔符
    public final static String SEPARATOR = ".";
    //顶级层级
    public final static String ROOT = "0";

    //层级的计算规则
    public  static String calculateLevel(String parentLevel, int parentId){
        if(StringUtils.isBlank(parentLevel)){
            //首层
            return ROOT;
        }else {
            return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }
    }

}