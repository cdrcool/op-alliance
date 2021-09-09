package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.JdClient;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.InvoiceApplySubmitRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 京东发票申请提交处理类
 *
 * @author chengdr01
 */
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
