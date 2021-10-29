package com.op.boot.mall.handler.jingdong.invoice;

import com.jd.open.api.sdk.domain.vopfp.OperaInvoiceOpenProvider.response.cancelInvoiceApply.OpenRpcResult;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceCancelInvoiceApplyRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceCancelInvoiceApplyResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.invoice.InvoiceCancelApplyRequest;
import com.op.boot.mall.response.invoice.InvoiceCancelApplyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东发票提交申请处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdInvoiceCancelApplyHandler extends JdMallRequestHandler<InvoiceCancelApplyRequest<VopInvoiceCancelInvoiceApplyRequest>,
        VopInvoiceCancelInvoiceApplyRequest, InvoiceCancelApplyResponse> {

    public JdInvoiceCancelApplyHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public InvoiceCancelApplyResponse handle(InvoiceCancelApplyRequest<VopInvoiceCancelInvoiceApplyRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopInvoiceCancelInvoiceApplyRequest jdRequest = request.getRequestObj();

        // 3. 执行京东请求
        JdMallRequest<VopInvoiceCancelInvoiceApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopInvoiceCancelInvoiceApplyResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("取消京东发票申请失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "取消京东发票申请失败：" + result.getResultMessage());
        }

        // 4. 转换为标准请求响应
        InvoiceCancelApplyResponse response = new InvoiceCancelApplyResponse();
        response.setSuccess(result.getSuccess());

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.INVOICE_CANCEL_APPLY), this);
    }
}
