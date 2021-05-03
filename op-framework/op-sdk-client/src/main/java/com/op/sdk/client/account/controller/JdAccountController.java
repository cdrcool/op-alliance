package com.op.sdk.client.account.controller;

import com.op.sdk.client.account.service.JdAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 京东帐号 Controller
 *
 * @author cdrcool
 */
@Api(tags = "京东帐号")
@RequestMapping("/jd-account")
@RestController
public class JdAccountController {
    private final JdAccountService jdAccountService;

    public JdAccountController(JdAccountService jdAccountService) {
        this.jdAccountService = jdAccountService;
    }

    @ApiOperation("异步请求京东token（未传递纳税人识别号，则刷新默认京东token）")
    @GetMapping("/requestAccessToken")
    public DeferredResult<String> requestAccessToken(@ApiParam("纳税人识别号") String taxpayerId,
                                                     @ApiParam(value = "超时时间", defaultValue = "3s")
                                                     @RequestParam(defaultValue = "3") Integer timeout) {
        DeferredResult<String> deferredResult = new DeferredResult<>(timeout.longValue() * 1000);

        jdAccountService.requestAccessToken(taxpayerId, deferredResult);

        // 超时回调
        deferredResult.onTimeout( () -> {
            throw new RuntimeException("请求京东token超时");
        });
        // 失败回调
        deferredResult.onError(e -> {
            throw new RuntimeException("请求京东token异常", e);
        });

        return deferredResult;
    }

    @ApiOperation("京东回调地址（调用请求京东token接口后，由京东回调该接口）")
    @GetMapping("/callbackToken")
    public void callbackToken(@ApiParam("京东授权码") @RequestParam String code,
                              @ApiParam("京东回传state（需与传递给京东的保持一致）") @RequestParam String state) {
        jdAccountService.callbackToken(code, state);
    }

    @ApiOperation("刷新京东token（未传递纳税人识别号，则刷新默认京东token）")
    @GetMapping("/refreshToken")
    public String refreshToken(@ApiParam("纳税人识别号") String taxpayerId) {
        return jdAccountService.refreshToken(taxpayerId);
    }

    @ApiOperation("获取京东token（未传递纳税人识别号，则获取默认京东token）")
    @GetMapping("/getAccessToken")
    public DeferredResult<String> getAccessToken(@ApiParam("纳税人识别号") String taxpayerId) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        jdAccountService.getAccessToken(taxpayerId, deferredResult);
        return deferredResult;
    }
}
