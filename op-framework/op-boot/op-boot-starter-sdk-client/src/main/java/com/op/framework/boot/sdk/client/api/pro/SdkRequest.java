package com.op.framework.boot.sdk.client.api.pro;

import com.op.framework.boot.sdk.client.api.pro.BaseSdkResponse;
import lombok.Data;
import lombok.Getter;

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
    private SdkType sdkType;

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

    @Getter
    public enum SdkType {
        /**
         * 京东
         */
        JD("jd"),

        /**
         * 苏宁
         */
        SN("sn"),
        ;

        /**
         * 枚举值
         */
        private final String value;

        SdkType(String value) {
            this.value = value;
        }
    }
}
