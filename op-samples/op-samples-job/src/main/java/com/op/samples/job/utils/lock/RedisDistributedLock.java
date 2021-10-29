package com.op.samples.job.utils.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

/**
 * Redis 分布式锁
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class RedisDistributedLock implements DistributedLock {
    public static final String UNLOCK_LUA = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end ";

    private final ThreadLocal<String> uuidHolder = new ThreadLocal<>();
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleep) {
        do {
            String uuid = UUID.randomUUID().toString();
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, uuid, Duration.ofMillis(expire));

            if (Objects.equals(success, true)) {
                log.info("lock key: {} success, uuid: {}", key, uuid);
                uuidHolder.set(uuid);
                return true;
            }

            retryTimes--;

            try {
                log.warn("try lock key: {} failed, retrying...{}", key, retryTimes);
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                return false;
            }
        } while (retryTimes <= 0);

        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        String uuid = uuidHolder.get();
        uuidHolder.remove();

        RedisScript<Long> redisScript = RedisScript.of(UNLOCK_LUA, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(key), uuid);

        boolean success = result != null && result > 0;
        log.info("try unlock key: {} success: {}", key, success);

        return success;
    }
}
