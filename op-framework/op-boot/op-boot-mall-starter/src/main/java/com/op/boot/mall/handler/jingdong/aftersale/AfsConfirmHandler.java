package com.op.boot.mall.handler.jingdong.aftersale;

import com.jd.open.api.sdk.domain.vopsh.OperaAfterSaleOpenProvider.response.confirmAfsOrder.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsh.VopAfsConfirmAfsOrderRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsConfirmAfsOrderResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsConfirmRequest;
import com.op.boot.mall.response.aftersale.AfsConfirmResponse;
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
public class AfsConfirmHandler extends JdMallRequestHandler<AfsConfirmRequest<VopAfsConfirmAfsOrderRequest>,
        VopAfsConfirmAfsOrderRequest, AfsConfirmResponse> {

    public AfsConfirmHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsConfirmResponse handle(AfsConfirmRequest<VopAfsConfirmAfsOrderRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopAfsConfirmAfsOrderRequest vopAfsConfirmAfsOrderRequest = request.getRequestObj();

        JdMallRequest<VopAfsConfirmAfsOrderResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsConfirmAfsOrderRequest);

        VopAfsConfirmAfsOrderResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            AfsConfirmResponse afsConfirmResponse = new AfsConfirmResponse();
            afsConfirmResponse.setResult(result.getResult());
            return afsConfirmResponse;

        } else {
            String message = MessageFormat.format("京东确认售后服务单失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_CONFIRM), this);
    }
}
