package com.op.boot.mall.response.aftersale;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AfsConfirmResponse extends MallResponse {
    /**
     * 是否成功
     */
    private Boolean result;
}
