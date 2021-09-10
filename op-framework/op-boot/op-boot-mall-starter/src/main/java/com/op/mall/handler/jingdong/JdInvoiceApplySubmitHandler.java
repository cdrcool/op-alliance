package com.op.mall.handler.jingdong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.vopfp.VopInvoiceSubmitInvoiceApplyRequest;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.InvoiceApplySubmitRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * 京东发票申请提交处理类
 *
 * @author chengdr01
 */
@Slf4j
public class JdInvoiceApplySubmitHandler implements JdMallRequestHandler {
    /**
     * 京东 sdk client
     */
    private final JdClient jdClient;

    public JdInvoiceApplySubmitHandler(JdClient jdClient) {
        this.jdClient = jdClient;
    }

    @Override
    public <T extends MallResponse> T handle(MallRequest<T> mallRequest) {
        InvoiceApplySubmitRequest request = (InvoiceApplySubmitRequest) mallRequest;
        Map<String, Object> requestParams = request.getRequestParams();

        ObjectMapper objectMapper = new ObjectMapper();
        VopInvoiceSubmitInvoiceApplyRequest jdRequest = null;
        try {
            jdRequest = objectMapper.readValue(objectMapper.writeValueAsBytes(requestParams), VopInvoiceSubmitInvoiceApplyRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jdClient.execute(jdRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void postConstruct() {
        MallRequestHandlerRegistry.addHandler(mallType(), MallMethodConstants.INVOICE_APPLY_SUBMIT, this);
    }

    @Override
    public void preDestroy() {
        MallRequestHandlerRegistry.removeHandler(mallType(), MallMethodConstants.INVOICE_APPLY_SUBMIT);
    }
}
