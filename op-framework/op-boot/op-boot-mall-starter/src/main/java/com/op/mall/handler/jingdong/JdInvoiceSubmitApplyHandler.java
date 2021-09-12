package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.mall.client.jingdong.JdMallAuthentication;
import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.client.jingdong.JdMallRequest;
import com.op.mall.request.InvoiceSubmitApplyRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.response.InvoiceSubmitApplyResponse;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 京东发票提交申请处理类
 *
 * @author chengdr01
 */
@Slf4j
public class JdInvoiceSubmitApplyHandler extends JdMallRequestHandler {

    public JdInvoiceSubmitApplyHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        InvoiceSubmitApplyRequest concreteRequest = (InvoiceSubmitApplyRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = (VopInvoiceSubmitInvoiceApplyRequest) requestObj;

        // 2. 执行京东电商请求
        JdMallRequest<VopInvoiceSubmitInvoiceApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopInvoiceSubmitInvoiceApplyResponse jdResponse = getJdMallClient().execute(jdMallRequest);

        // 3. 解析为标准电商请求响应
        // 模拟京东电商请求响应解析
        InvoiceSubmitApplyResponse response = new InvoiceSubmitApplyResponse();
        return (R) response;
    }
}
