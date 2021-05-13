package com.op.framework.boot.sdk.client.account.controller;

import com.op.framework.boot.sdk.client.account.service.ThirdAccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
