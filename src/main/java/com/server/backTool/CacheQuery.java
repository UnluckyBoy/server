package com.server.backTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public class CacheQuery {
    //在RedisConfig文件中配置了redisTemplate的序列化之后， 客户端也能正确显示键值对了
    @Autowired
    private RedisTemplate redisTemplate;

    public CacheQuery(){
        super();
    }

    public Map GetCache(String mKey){
        BoundHashOperations hashOps = redisTemplate.boundHashOps(mKey);
        return hashOps.entries();
    }
}
