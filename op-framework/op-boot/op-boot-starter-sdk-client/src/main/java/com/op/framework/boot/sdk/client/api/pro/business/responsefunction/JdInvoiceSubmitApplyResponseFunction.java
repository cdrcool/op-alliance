package com.op.framework.boot.sdk.client.api.pro.business.responsefunction;

import com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.submitInvoiceApply.OpenRpcResult;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.framework.boot.sdk.client.api.pro.response.InvoiceApplySubmitResponse;
import com.op.framework.boot.sdk.client.api.pro.function.JdResponseFunction;
import com.op.framework.boot.sdk.client.exception.JdInvokeException;
import lombok.extern.slf4j.Slf4j;

/**
 * 京东发票申请提交响应对象 Function
 *
 * @author cdrcool
 */
@Slf4j
public class JdInvoiceSubmitApplyResponseFunction implements JdResponseFunction<VopInvoiceSubmitInvoiceApplyResponse, InvoiceApplySubmitResponse> {

    @Override
    public InvoiceApplySubmitResponse apply(VopInvoiceSubmitInvoiceApplyResponse vopInvoiceSubmitInvoiceApplyResponse) {
        OpenRpcResult result = vopInvoiceSubmitInvoiceApplyResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("提交京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdInvokeException(result.getResultCode(), "提交京东发票申请失败：" + result.getResultMessage());
        }

        InvoiceApplySubmitResponse response = new InvoiceApplySubmitResponse();
        response.setResult(response.getResult());
        return response;
    }
}
