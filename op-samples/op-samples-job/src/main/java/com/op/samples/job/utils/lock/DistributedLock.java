package com.op.samples.job.utils.lock;

import java.util.function.Consumer;

/**
 * 分布式锁接口
 *
 * @author cdrcool
 */
public interface DistributedLock {
    long EXPIRE = 30000L;
    int RETRY_TIMES = 3;
    long WAIT = 500L;

    /**
     * 获取分布式锁
     *
     * @param key key
     * @return 是否成功
     */
    default boolean lock(String key) {
        return this.lock(key, EXPIRE, RETRY_TIMES, WAIT);
    }

    /**
     * 获取分布式锁
     *
     * @param key    key
     * @param expire 锁的过期时间
     * @return 是否成功
     */
    default boolean lock(String key, long expire) {
        return this.lock(key, expire, RETRY_TIMES, WAIT);
    }

    /**
     * 获取分布式锁
     *
     * @param key    key
     * @param expire 锁的过期时间
     * @param sleep  当前线程在下一次尝试获取锁前睡眠的时间
     * @return 是否成功
     */
    default boolean lock(String key, long expire, long sleep) {
        return this.lock(key, expire, RETRY_TIMES, sleep);
    }

    /**
     * 获取分布式锁
     *
     * @param key        key
     * @param expire     锁的过期时间
     * @param retryTimes 获取锁失败时最大重试次数
     * @param sleep      当前线程在下一次尝试获取锁前睡眠的时间
     * @return 是否成功
     */
    boolean lock(String key, long expire, int retryTimes, long sleep);

    /**
     * 释放分布式锁
     *
     * @param key key
     * @return 是否成功
     */
    boolean releaseLock(String key);

    /**
     * 获取分布式锁
     *
     * @param key      key
     * @param consumer 获取锁成功后要执行的操作
     * @return 是否成功
     */
    default boolean lock(String key, Consumer<Void> consumer) {
        return this.lock(key, EXPIRE, RETRY_TIMES, WAIT, consumer);
    }

    /**
     * 获取分布式锁
     *
     * @param key      key
     * @param expire   锁的过期时间
     * @param consumer 获取锁成功后要执行的操作
     * @return 是否成功
     */
    default boolean lock(String key, long expire, Consumer<Void> consumer) {
        return this.lock(key, expire, RETRY_TIMES, WAIT, consumer);
    }

    /**
     * 获取分布式锁
     *
     * @param key      key
     * @param expire   锁的过期时间
     * @param sleep    当前线程在下一次尝试获取锁前睡眠的时间
     * @param consumer 获取锁成功后要执行的操作
     * @return 是否成功
     */
    default boolean lock(String key, long expire, long sleep, Consumer<Void> consumer) {
        return this.lock(key, expire, RETRY_TIMES, sleep, consumer);
    }

    /**
     * 获取分布式锁
     *
     * @param key        key
     * @param expire     锁的过期时间
     * @param retryTimes 获取锁失败时最大重试次数
     * @param sleep      当前线程在下一次尝试获取锁前睡眠的时间
     * @param consumer   获取锁成功后要执行的操作
     * @return 是否成功
     */
    default boolean lock(String key, long expire, int retryTimes, long sleep, Consumer<Void> consumer) {
        boolean success = lock(key, expire, retryTimes, sleep);

        if (!success) {
            return false;
        }

        try {
            consumer.accept(null);
        } finally {
            releaseLock(key);
        }

        return true;
    }
}
