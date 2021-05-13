package com.op.framework.boot.sdk.client.account.model;

import com.op.framework.boot.sdk.client.account.entity.AccountType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * token请求信息
 *
 * @author cdrcool
 */
@ApiModel(description = "京东token请求信息")
@Data
public class TokenRequestInfo {
    /**
     * 由client使用的不透明参数，用于请求阶段和回调阶段之间的状态保持
     */
    private String state;

    /**
     * 账号类型
     */
    private AccountType accountType;

    /**
     * 帐号
     */
    private String account;

    /**
     * {@link DeferredResult}
     */
    private DeferredResult<String> deferredResult;

    public TokenRequestInfo(String state, AccountType accountType, String account) {
        this.state = state;
        this.accountType = accountType;
        this.account = account;
    }
}
