package com.op.boot.mall.handler.jingdong.aftersale;

import com.jd.open.api.sdk.domain.vopsh.OperaAfterSaleOpenProvider.response.createAfsApply.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsh.VopAfsCreateAfsApplyRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsCreateAfsApplyResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsCreateApplyRequest;
import com.op.boot.mall.response.aftersale.AfsCreateApplyResponse;
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
public class AfsCreateApplyHandler extends JdMallRequestHandler<AfsCreateApplyRequest<VopAfsCreateAfsApplyRequest>,
        VopAfsCreateAfsApplyRequest, AfsCreateApplyResponse> {

    public AfsCreateApplyHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsCreateApplyResponse handle(AfsCreateApplyRequest<VopAfsCreateAfsApplyRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopAfsCreateAfsApplyRequest vopAfsCreateAfsApplyRequest = request.getRequestObj();

        JdMallRequest<VopAfsCreateAfsApplyResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsCreateAfsApplyRequest);

        VopAfsCreateAfsApplyResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {


            AfsCreateApplyResponse afsCreateApplyResponse = new AfsCreateApplyResponse();
            afsCreateApplyResponse.setResult(result.getResult());
            return afsCreateApplyResponse;

        } else {
            String message = MessageFormat.format("京东创建售后单失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_CREATE_APPLY), this);
    }
}
