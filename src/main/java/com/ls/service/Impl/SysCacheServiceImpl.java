package com.ls.service.Impl;

import com.google.common.base.Joiner;
import com.ls.beans.CacheKeyConstants;
import com.ls.service.RedisPool;
import com.ls.service.SysCacheService;
import com.ls.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 *
 * @author Administrator
 * @create 2017/12/9
 * @since 1.0.0
 */
@Service
public class SysCacheServiceImpl implements SysCacheService{

    private static final Logger log = LoggerFactory.getLogger(SysCacheServiceImpl.class);

    @Resource(name = "redisPool")
    private RedisPool redisPool;

    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix){
        saveCache(toSaveValue, timeoutSeconds, prefix, null);
    }

    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys){
        if(toSaveValue == null){
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeoutSeconds, JsonMapper.objToString(toSaveValue));
        }catch (Exception e ){
            log.error("save cache exception, prefix:{},keys:{}",prefix.name(), JsonMapper.objToString(toSaveValue));
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    public String getFromCache(CacheKeyConstants prefix, String... keys){
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            log.error("get cache value:{}",value);
            return value;
        }catch (Exception e ){
            log.error("get cache exception, prefix:{},keys:{}",prefix.name(), JsonMapper.objToString(keys));
            return null;
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix, String... keys ){
        String key = prefix.name();
        if(keys != null && keys.length > 0 ){
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }

    public static void main(String[] args) {
        SysCacheServiceImpl sysCacheService = new SysCacheServiceImpl();
        System.out.println(Joiner.on("_").skipNulls().join(new String[]{"11","2","",null,"3"}));
        System.out.println(sysCacheService.generateCacheKey(CacheKeyConstants.USER_ACLS, String.valueOf(1)));
    }
}