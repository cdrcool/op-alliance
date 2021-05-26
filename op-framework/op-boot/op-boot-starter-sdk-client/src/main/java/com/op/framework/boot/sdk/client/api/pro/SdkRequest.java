package com.op.framework.boot.sdk.client.api.pro;

import com.op.framework.boot.sdk.client.api.pro.response.BaseSdkResponse;
import com.op.framework.boot.sdk.client.base.ThirdSdkType;
import lombok.Data;

/**
 * SDK 请求对象
 *
 * @author cdrcool
 */
@Data
public class SdkRequest<T extends BaseSdkResponse> {
    /**
     * sdk 类型
     */
    private ThirdSdkType sdkType;

    /**
     * 动作名
     */
    private String actionName;

    /**
     * 单据id
     */
    private String billId;

    /**
     * 访问令牌
     */
    private String token;
}
