package com.op.sdk.client.account.controller;

import com.op.sdk.client.account.service.ThirdAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 第三方帐号 Controller
 *
 * @author cdrcool
 */
public abstract class ThirdAccountController {
    private final ThirdAccountService thirdAccountService;

    public ThirdAccountController(ThirdAccountService thirdAccountService) {
        this.thirdAccountService = thirdAccountService;
    }

    @ApiOperation("异步请求第三方token（未传递纳税人识别号，则刷新默认第三方token）")
    @GetMapping("/request-access-token")
    public DeferredResult<String> requestAccessToken(@ApiParam("纳税人识别号") String taxpayerId,
                                                     @ApiParam(value = "超时时间（单位：秒）", defaultValue = "3")
                                                     @RequestParam(defaultValue = "3") Integer timeout) {
        DeferredResult<String> deferredResult = new DeferredResult<>(timeout.longValue() * 1000);

        thirdAccountService.requestAccessToken(taxpayerId, deferredResult);

        // 超时回调
        deferredResult.onTimeout(() -> deferredResult.setErrorResult("请求第三方token超时"));
        // 失败回调
        deferredResult.onError(e -> deferredResult.setErrorResult("请求第三方token异常：" + e.getMessage()));

        return deferredResult;
    }

    @ApiOperation("第三方回调地址（调用请求第三方token接口后，由第三方回调该接口）")
    @GetMapping("/callback-token")
    public void callbackToken(@ApiParam("第三方授权码") @RequestParam String code,
                              @ApiParam("第三方回传state（需与传递给第三方的保持一致）") @RequestParam String state) {
        thirdAccountService.callbackToken(code, state);
    }

    @ApiOperation("刷新第三方token（未传递纳税人识别号，则刷新默认第三方token）")
    @GetMapping("/refresh-token")
    public String refreshToken(@ApiParam("纳税人识别号") String taxpayerId) {
        return thirdAccountService.refreshToken(taxpayerId);
    }

    @ApiOperation("获取第三方token（未传递纳税人识别号，则获取默认第三方token）")
    @GetMapping("/get-access-token")
    public DeferredResult<String> getAccessToken(@ApiParam("纳税人识别号") String taxpayerId) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        thirdAccountService.getAccessToken(taxpayerId, deferredResult);
        return deferredResult;
    }
}
