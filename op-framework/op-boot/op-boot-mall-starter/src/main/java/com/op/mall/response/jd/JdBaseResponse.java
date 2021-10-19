package com.op.mall.response.jd;

import lombok.Data;

/**
 * 京东响应
 *
 * @author chengdr01
 */
@Data
public class JdBaseResponse<T> {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 业务处理结果编码, 详细参见：【错误码】
     */
    private String resultCode;

    /**
     * 对resultCode的简要说明
     */
    private String resultMessage;

    /**
     * 对象
     */
    private T result;
}
