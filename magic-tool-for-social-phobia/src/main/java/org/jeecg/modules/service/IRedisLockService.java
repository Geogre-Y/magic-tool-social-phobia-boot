package org.jeecg.modules.service;

import java.util.concurrent.TimeUnit;

/**
 * @author 余吉钊
 * @date 2024/3/7 15:21
 */
public interface IRedisLockService {
    boolean tryLock(String key, long expireTime, long timeout, TimeUnit timeoutUnit);

    default boolean tryLock(String key, long timeout, TimeUnit timeoutUnit) {
        return this.tryLock(key, 3600000L, timeout, timeoutUnit);
    }

    void unLock(String key);

    boolean isLock(String key);
}
