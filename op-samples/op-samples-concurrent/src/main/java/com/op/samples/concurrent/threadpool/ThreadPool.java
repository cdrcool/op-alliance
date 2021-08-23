package com.op.samples.concurrent.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 *
 * @author chengdr01
 */
public class ThreadPool {
    private static final ThreadPoolExecutor cpuIntensivePool = new ThreadPoolExecutor(11, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());

    private static final ThreadPoolExecutor ioIntensivePool = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
}
