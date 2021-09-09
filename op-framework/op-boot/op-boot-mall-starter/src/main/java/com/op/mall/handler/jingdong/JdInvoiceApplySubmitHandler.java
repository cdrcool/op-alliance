package com.op.mall.handler.jingdong;

import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.handler.MallRequestHandler;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.MallRequest;
import com.op.mall.response.MallResponse;

/**
 * 京东发票申请提交处理类
 *
 * @author chengdr01
 */
public class JdInvoiceApplySubmitHandler implements MallRequestHandler {

    @Override
    public <T extends MallResponse> T handle(MallRequest<T> mallRequest) {
        return null;
    }

    @Override
    public void postConstruct() {
        MallRequestHandlerRegistry.addHandler(MallType.JINGDONG, MallMethodConstants.INVOICE_APPLY_SUBMIT, this);
    }

    @Override
    public void preDestroy() {
        MallRequestHandlerRegistry.removeHandler(MallType.JINGDONG, MallMethodConstants.INVOICE_APPLY_SUBMIT);
    }
}
