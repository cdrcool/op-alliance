package com.op.mall.handler.jingdong;

import com.op.mall.constans.MallType;
import com.op.mall.handler.MallRequestHandler;
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
    public boolean supports(MallType mallType) {
        return true;
    }
}
