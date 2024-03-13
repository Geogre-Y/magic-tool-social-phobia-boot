package org.jeecg.modules.service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.service.IRedisLockService;
import org.jeecg.modules.utils.Assist;
import org.jeecg.modules.utils.CastWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 余吉钊
 * @date 2024/3/7 15:22
 */
@Service
public class IRedisLockServiceImpl implements IRedisLockService {
    private static final Logger log = LoggerFactory.getLogger(IRedisLockServiceImpl.class);
    protected static final String LOCK_VALUE = "VAM_REDIS_LOCK";
    protected RedisOperations<String, String> redisOps;

    public IRedisLockServiceImpl(RedisOperations<?, ?> redisTemplate) {
        this.redisOps = (RedisOperations) CastWrap.cast(redisTemplate);
    }

    public boolean tryLock(String key, long expireTime, long timeout, TimeUnit timeoutUnit) {
        Assist.threw(StringUtils.isBlank(key), "key不能为空");
        Assist.threw(expireTime < 0L, "expireTime不能小于0");
        Assist.threw(timeout < 0L, "timeout不能小于0");
        long start = System.nanoTime();
        timeout = timeoutUnit.toNanos(timeout);
        key = this.getLockKey(key);

        try {
            while(!this.redisOps.opsForValue().setIfAbsent(key, "VAM_REDIS_LOCK", expireTime, TimeUnit.MILLISECONDS)) {
                long passingTime = System.nanoTime() - start;
                if (passingTime >= timeout) {
                    log.info("等待锁[key={}]超时, 总用时: {}, 超时时间: {}", new Object[]{key, passingTime, timeout});
                    return false;
                }
                log.info("等待锁[key={}], 用时: {}", key, passingTime);
                Thread.sleep(10L);
            }

            log.info("获得锁[key={}]", key);
            return true;
        } catch (Exception var11) {
            log.error("获取锁失败: ", var11.getMessage());
            return false;
        }
    }

    public void unLock(String key) {
        key = this.getLockKey(key);
        this.redisOps.delete(key);
        log.info("释放锁[key={}]", key);
    }

    public boolean isLock(String key) {
        return StringUtils.isNotBlank((CharSequence)this.redisOps.opsForValue().get(this.getLockKey(key)));
    }

    protected String getLockKey(String key) {
        return "VAM_REDIS_LOCK/" + key;
    }
}
