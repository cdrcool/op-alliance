package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.client.jingdong.JdMallRequest;
import com.op.mall.request.InvoiceApplySubmitRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.response.InvoiceApplySubmitResponse;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 京东发票申请提交处理类
 *
 * @author chengdr01
 */
@Slf4j
public class JdInvoiceApplySubmitHandler extends JdMallRequestHandler {

    public JdInvoiceApplySubmitHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        // 1. 转换为京东电商请求对象
        InvoiceApplySubmitRequest concreteRequest = (InvoiceApplySubmitRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = (VopInvoiceSubmitInvoiceApplyRequest) requestObj;

        // 2. 执行京东电商请求
        JdMallRequest<VopInvoiceSubmitInvoiceApplyResponse> jdMallRequest = new JdMallRequest<>(jdRequest.getEnterpriseTaxpayer(), jdRequest);
        VopInvoiceSubmitInvoiceApplyResponse jdResponse = getJdMallClient().execute(jdMallRequest);

        // 3. 解析为标准电商请求响应
        // 模拟京东电商请求响应解析
        InvoiceApplySubmitResponse response = new InvoiceApplySubmitResponse();
        return (R) response;
    }
}
