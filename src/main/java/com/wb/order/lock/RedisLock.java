package com.wb.order.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 分布式锁
 */

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     * @param key
     * @param value
     * @return
     */
    public boolean lock(String key,String value){
        //加锁成功
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        //解决因未执行解锁操作导致的死锁问题——通过锁超时解决
        String currentValue = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue)&&
                Long.parseLong(currentValue) < System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue = stringRedisTemplate.opsForValue().getAndSet(key,value);
            if (!StringUtils.isEmpty(oldValue)&&oldValue.equals(currentValue)){
                return true;
            }
        }

        //加锁失败
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue)
                    && currentValue.equals(value)){
                //从redis中删除key
                stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error(" [redis分布式锁] 解锁异常，e={}",e);
        }
    }
}
