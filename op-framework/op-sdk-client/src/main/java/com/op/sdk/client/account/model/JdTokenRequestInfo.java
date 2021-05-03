package com.op.sdk.client.account.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author cdrcool
 */
@ApiModel(description = "京东token请求信息")
@Data
public class JdTokenRequestInfo {
    /**
     * 由client使用的不透明参数，用于请求阶段和回调阶段之间的状态保持
     */
    private String state;

    /**
     * 京东帐号
     */
    private String account;

    /**
     * {@link DeferredResult}
     */
    private DeferredResult<String> deferredResult;

    public JdTokenRequestInfo(String state, String account) {
        this.state = state;
        this.account = account;
    }
}
