package com.op.sdk.client.account.controller;

import com.op.sdk.client.account.service.ThirdAccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 京东帐号 Controller
 *
 * @author cdrcool
 */
@Api(tags = "京东帐号")
@RequestMapping("/jd-account")
@RestController
public class JdAccountController extends ThirdAccountController {

    public JdAccountController(@Qualifier("jdAccountServiceImpl") ThirdAccountService thirdAccountService) {
        super(thirdAccountService);
    }
}
