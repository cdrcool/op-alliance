package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.jd.open.api.sdk.response.vopfp.VopInvoiceSubmitInvoiceApplyResponse;
import com.op.mall.request.InvoiceApplySubmitRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.response.InvoiceApplySubmitResponse;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * 京东发票申请提交处理类
 *
 * @author chengdr01
 */
@Slf4j
public class JdInvoiceApplySubmitHandler extends JdMallRequestHandler {

    public JdInvoiceApplySubmitHandler(JdClient jdClient) {
        super(jdClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        // 1. 转换为京东电商请求对象
        InvoiceApplySubmitRequest concreteRequest = (InvoiceApplySubmitRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = (VopInvoiceSubmitInvoiceApplyRequest) requestObj;

        // 2. 执行京东电商请求
        try {
            VopInvoiceSubmitInvoiceApplyResponse jdResponse = getJdClient().execute(jdRequest);
        } catch (Exception e) {
            String appJsonParams;
            try {
                appJsonParams = jdRequest.getAppJsonParams();
            } catch (IOException ioException) {
                log.error("获取京东电商请求参数异常", e);
                appJsonParams = "";
            }
            String message = MessageFormat.format("执行京东电商请求失败，请求方法：{0}，请求参数：{1}",
                    jdRequest.getApiMethod(), appJsonParams);
            return new MallResponse.ErrorBuilder().errorMsg(message).build(request.getResponseClass());
        }

        // 3. 解析为标准电商请求响应
        // 模拟京东电商请求响应解析
        InvoiceApplySubmitResponse response = new InvoiceApplySubmitResponse();
        return (R) response;
    }
}
