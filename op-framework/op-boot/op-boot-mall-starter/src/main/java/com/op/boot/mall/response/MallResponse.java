package com.op.boot.mall.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 电商响应
 *
 * @author chengdr01
 */
@Data
public class MallResponse implements Serializable {
    /**
     * 是否成功
     */
    private Boolean success;
}
