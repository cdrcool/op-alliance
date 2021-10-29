package com.op.boot.mall.response.message;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageDeleteResponse extends MallResponse {
    /**
     * 是否成功
     */
    private Boolean result;
}

