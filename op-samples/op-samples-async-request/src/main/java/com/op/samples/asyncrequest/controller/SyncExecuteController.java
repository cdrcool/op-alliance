package com.op.samples.asyncrequest.controller;

import com.op.samples.asyncrequest.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 同步执行 controller
 *
 * @author cdrcool
 */
@Slf4j
@RequestMapping("syncExecute")
@RestController
public class SyncExecuteController {
    private final ExampleService exampleService;

    public SyncExecuteController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/fast")
    public void fast() throws InterruptedException {
        exampleService.executeSync(100);
    }

    @GetMapping("/slow")
    public void show() throws InterruptedException {
        exampleService.executeSync(1000 * 3);
    }
}
