package com.op.framework.boot.sdk.client.api.factrory;

import com.op.framework.boot.sdk.client.api.AreaApi;
import com.op.framework.boot.sdk.client.api.InvoiceApi;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import com.op.framework.web.common.api.response.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 第三方 API 接口工厂
 *
 * @author cdrcool
 */
@Component
public class ThirdApiFactory {
    private final Map<String, AreaApi> areaApiMap;
    private final Map<String, InvoiceApi> invoiceApiMap;

    public ThirdApiFactory(Map<String, AreaApi> areaApiMap,
                           Map<String, InvoiceApi> invoiceApiMap) {
        this.areaApiMap = areaApiMap;
        this.invoiceApiMap = invoiceApiMap;
    }

    /**
     * 获取区域 API
     *
     * @param thirdType 第三方 SDK 类型
     * @return 区域 API
     */
    public AreaApi getAreaApi(String thirdType) {
        ThirdSdkType thirdSdkType = ThirdSdkType.getByValue(thirdType);
        switch (thirdSdkType) {
            case JD:
                return areaApiMap.get("jdAreaApi");
            case SN:
                return areaApiMap.get("snAreaApi");
            default:
                throw new BusinessException("不支持的第三方 SDK 类型：" + thirdType);
        }
    }

    /**
     * 获取发票 API
     *
     * @param thirdType 第三方 SDK 类型
     * @return 发票 API
     */
    public InvoiceApi getInvoiceApi(String thirdType) {
        ThirdSdkType thirdSdkType = ThirdSdkType.getByValue(thirdType);
        switch (thirdSdkType) {
            case JD:
                return invoiceApiMap.get("jdInvoiceApi");
            case SN:
                return invoiceApiMap.get("snInvoiceApi");
            default:
                throw new BusinessException("不支持的第三方 SDK 类型：" + thirdType);
        }
    }
}
