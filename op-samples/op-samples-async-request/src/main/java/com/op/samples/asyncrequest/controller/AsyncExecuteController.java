package com.op.samples.asyncrequest.controller;

import com.op.samples.asyncrequest.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异步执行 controller
 *
 * @author cdrcool
 */
@Slf4j
@RequestMapping("asyncExecute")
@RestController
public class AsyncExecuteController {
    private final ExampleService exampleService;

    public AsyncExecuteController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/")
    public void asyncExecute() {
        log.info("Main thread name：{}", Thread.currentThread().getName());
        exampleService.executeAsync();
    }
}
