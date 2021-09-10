package com.op.mall.request;

import com.op.mall.response.MallResponse;
import com.op.mall.constans.MallType;

import java.util.Map;

/**
 * 电商请求对象接口
 *
 * @author chengdr01
 */
public abstract class MallRequest<T extends MallResponse> {
    /**
     * 电商类型
     */
    private final String mallType;

    /**
     * 请求参数
     */
    private Map<String, Object> requestParams;

    public MallRequest(String mallType) {
        this.mallType = mallType;
    }

    /**
     * 返回电商类型
     *
     * @return 电商类型
     */
    public final String getMallType() {
        return this.mallType;
    }

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }

    /**
     * 返回请求的方法名
     *
     * @return 请求方法名
     */
    public abstract String getRequestMethod();

    /**
     * 返回请求响应类的 class
     *
     * @return 请求响应类的 class
     */
    public abstract Class<T> getResponseClass();
}
