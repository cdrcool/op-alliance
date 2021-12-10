package com.op.boot.mall.request;

import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.response.MallResponse;

import java.io.Serializable;

/**
 * 电商请求
 *
 * @author chengdr01
 */
public abstract class MallRequest<P, R extends MallResponse> implements Serializable {
    /**
     * 电商类型枚举
     */
    private final MallType mallType;

    /**
     * 电商账号名
     */
    private final String accountName;

    /**
     * 请求对象
     */
    private final P requestObj;

    /**
     * 电商账号名
     */
    private MallAuthentication authentication;

    protected MallRequest(MallType mallType, String accountName, P requestObj) {
        this.mallType = mallType;
        this.accountName = accountName;
        this.requestObj = requestObj;
    }

    public MallType getMallType() {
        return mallType;
    }

    public String getAccountName() {
        return accountName;
    }

    public P getRequestObj() {
        return requestObj;
    }

    public MallAuthentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(MallAuthentication authentication) {
        this.authentication = authentication;
    }

    /**
     * 返回电商方法
     *
     * @return 电商方法
     */
    public abstract String getRequestMethod();

    /**
     * 返回请求响应的 class
     *
     * @return 请求响应的 class
     */
    public abstract Class<R> getResponseClass();
}
