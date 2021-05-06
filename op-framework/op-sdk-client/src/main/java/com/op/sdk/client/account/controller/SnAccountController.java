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
 * 苏宁帐号 Controller
 *
 * @author cdrcool
 */
@Api(tags = "苏宁帐号")
@RequestMapping("/sn-account")
@RestController
public class SnAccountController extends ThirdAccountController{

    public SnAccountController(@Qualifier("snAccountServiceImpl") ThirdAccountService thirdAccountService) {
        super(thirdAccountService);
    }
}
