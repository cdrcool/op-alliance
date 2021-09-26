package com.op.samples.concurrent.demo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author cdrcool
 */
public class TestOutOfMemory {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                9,
                9,
                200L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("task-pool-%d").build());

        List<Future<Long>> futures = new ArrayList<>();
        for(int i = 0; i < 50000; i++) {
            futures.add(threadPoolExecutor.submit(new XXTask()));
        }
        for (Future<Long> future: futures) {
            future.get();
        }
    }

    public static  class  XXTask implements Callable<Long> {

        @Override
        public Long call() {
            Map<String, Long> map = new HashMap<>(1);
            map.put("id", 10000000L);
            return map.get("id");
        }
    }
}
