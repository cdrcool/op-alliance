package com.op.boot.mall.response.message;

import com.op.boot.mall.response.MallResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author cdrcool
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageQueryResponse extends MallResponse {
    private List<String> messageList;
}
