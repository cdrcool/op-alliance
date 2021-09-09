package com.op.mall.response;

import com.op.mall.constans.MallType;
import lombok.Data;

import java.io.Serializable;

/**
 * 电商请求响应抽象类
 *
 * @author chengdr01
 */
@Data
public abstract class MallResponse implements Serializable {
    /**
     * 电商类型
     */
    private MallType mallType;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;
}
