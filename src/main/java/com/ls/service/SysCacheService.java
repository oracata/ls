/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: SysCacheService
 * Author:   Administrator
 * Date:     2017/12/9 20:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import com.ls.beans.CacheKeyConstants;

/**
 *
 * @author Administrator
 * @create 2017/12/9
 * @since 1.0.0
 */
public interface SysCacheService {


    String getFromCache(CacheKeyConstants prefix, String... keys);

    void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix);

    void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys);

}