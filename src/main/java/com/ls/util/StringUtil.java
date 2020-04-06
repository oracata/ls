package com.ls.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Administrator
 * @create 2017/12/8
 * @since 1.0.0
 */
public class StringUtil {

    public static List<Integer> splitToListInt(String str){
        //1,2,3,4,,,  去除空的，留下1,2,3,4
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        return strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
    }
}