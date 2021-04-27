package com.op.sdk.client.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.op.sdk.client.account.model.JdTokenResponse;
import com.op.sdk.client.account.service.JdAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 京东帐号 Controller
 * @author chengdr01
 */
@Api(tags = "京东帐号")
@RequestMapping("/jd-account")
@RestController
public class JdAccountController {
    private final JdAccountService jdAccountService;

    public JdAccountController(JdAccountService jdAccountService) {
        this.jdAccountService = jdAccountService;
    }

    @ApiOperation(value = "请求京东token")
    @GetMapping("/requestAccessToken")
    public void requestAccessToken() {
         jdAccountService.requestAccessToken();
    }

    @ApiOperation("获取京东token回调")
    @GetMapping("/callbackToken")
    public void callbackToken(@RequestParam String code) {
         jdAccountService.callbackToken(code);
    }

    @ApiOperation(value = "获取京东token")
    @GetMapping("/getAccessToken")
    public JdTokenResponse getAccessToken() {
        return jdAccountService.getAccessToken();
    }
}
