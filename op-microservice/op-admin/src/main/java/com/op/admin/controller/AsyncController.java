package com.op.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @author chengdr01
 */
@RequestMapping("test")
@RestController
public class AsyncController {

    public AsyncController() {
    }


    @GetMapping("async")
    public Callable<String> async() throws InterruptedException {
        return () -> {
            Thread.sleep(3000);
            return "123";
        };
    }

    @GetMapping("sync")
    public String sync() throws InterruptedException {
        Thread.sleep(3000);
        return "123";
    }
}
