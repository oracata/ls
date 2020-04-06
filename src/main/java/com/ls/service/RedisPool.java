/**
 * Copyright (C), 2015-2017, HK有限公司
 * FileName: RedisPool
 * Author:   Administrator
 * Date:     2017/12/9 19:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ls.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 *  redis
 * @author Administrator
 * @create 2017/12/9
 * @since 1.0.0
 */
@Service("redisPool")
public class RedisPool {

    private static final Logger log = LoggerFactory.getLogger(RedisPool.class);
    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis){
        try {
            if(shardedJedis != null){
                shardedJedis.close();
            }
        }catch (Exception e){
            log.info("retuen redis resouce exception",e);
        }
    }
}