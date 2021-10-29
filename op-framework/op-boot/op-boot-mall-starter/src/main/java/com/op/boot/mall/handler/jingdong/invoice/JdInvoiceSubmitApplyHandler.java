package com.op.boot.mall.handler.jingdong.invoice;

import com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.submitInvoiceApply.OpenRpcResult;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.invoice.InvoiceSubmitApplyRequest;
import com.op.boot.mall.response.invoice.InvoiceSubmitApplyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东发票提交申请处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdInvoiceSubmitApplyHandler extends JdMallRequestHandler<InvoiceSubmitApplyRequest<VopInvoiceSubmitInvoiceApplyRequest>,
        VopInvoiceSubmitInvoiceApplyRequest, InvoiceSubmitApplyResponse> {

    public JdInvoiceSubmitApplyHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public InvoiceSubmitApplyResponse handle(InvoiceSubmitApplyRequest<VopInvoiceSubmitInvoiceApplyRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = request.getRequestObj();

        // 3. 执行京东请求
        JdMallRequest<VopInvoiceSubmitInvoiceApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopInvoiceSubmitInvoiceApplyResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("提交京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "提交京东发票申请失败：" + result.getResultMessage());
        }

        // 4. 转换为标准请求响应
        InvoiceSubmitApplyResponse response = new InvoiceSubmitApplyResponse();
        response.setSuccess(result.getSuccess());

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.INVOICE_SUBMIT_APPLY), this);
    }
}
