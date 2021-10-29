package com.op.boot.mall.handler.jingdong.aftersale;

import com.jd.open.api.sdk.domain.vopsh.OperaAfterSaleOpenProvider.response.cancelAfsApply.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsh.VopAfsCancelAfsApplyRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsCancelAfsApplyResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsCancelRequest;
import com.op.boot.mall.response.aftersale.AfsCancelResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class AfsCancelHandler extends JdMallRequestHandler<AfsCancelRequest<VopAfsCancelAfsApplyRequest>,
        VopAfsCancelAfsApplyRequest, AfsCancelResponse> {

    public AfsCancelHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsCancelResponse handle(AfsCancelRequest<VopAfsCancelAfsApplyRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopAfsCancelAfsApplyRequest vopAfsCancelAfsApplyRequest = request.getRequestObj();

        JdMallRequest<VopAfsCancelAfsApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsCancelAfsApplyRequest);

        VopAfsCancelAfsApplyResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            AfsCancelResponse afsCancelResponse = new AfsCancelResponse();
            afsCancelResponse.setResult(result.getResult());
            return afsCancelResponse;

        } else {
            String message = MessageFormat.format("京东售后申请单取消失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_CANCEL), this);
    }
}
