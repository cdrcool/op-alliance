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
    private final MallType mallType;

    protected MallRequest(MallType mallType) {
        this.mallType = mallType;
    }

    /**
     * 返回电商类型
     *
     * @return 电商类型
     */
    public final MallType getMallType() {
        return this.mallType;
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

    /**
     * 返回请求参数
     *
     * @return 请求参数
     */
    public abstract Map<String, Object> getRequestParams();
}
