package com.op.framework.boot.sdk.client.account.service;

import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 第三方 Token Factory
 *
 * @author chengdr01
 */
@Component
public class ThirdAccountServiceFactory {
    private final Map<String, ThirdAccountService> thirdAccountServiceMap;

    public ThirdAccountServiceFactory(Map<String, ThirdAccountService> thirdAccountServiceMap) {
        this.thirdAccountServiceMap = thirdAccountServiceMap;
    }

    public ThirdAccountService getThirdTokenService(String thirdType) {
        ThirdSdkType thirdSdkType = ThirdSdkType.getByValue(thirdType);
        switch (thirdSdkType) {
            case JD:
                return thirdAccountServiceMap.get("jdTokenServiceImpl");
            case SN:
                return thirdAccountServiceMap.get("snTokenServiceImpl");
            default:
                throw new BusinessException("不支持的第三方 SDK 类型：" + thirdType);
        }
    }
}
