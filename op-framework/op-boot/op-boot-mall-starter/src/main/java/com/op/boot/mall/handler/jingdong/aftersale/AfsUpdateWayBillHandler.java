package com.op.boot.mall.handler.jingdong.aftersale;

import com.jd.open.api.sdk.domain.vopsh.OperaAfterSaleOpenProvider.response.updateSendInfo.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsh.VopAfsUpdateSendInfoRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsUpdateSendInfoResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsUpdateWaybillRequest;
import com.op.boot.mall.response.aftersale.AfsUpdateWaybillResponse;
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
public class AfsUpdateWayBillHandler extends JdMallRequestHandler<AfsUpdateWaybillRequest<VopAfsUpdateSendInfoRequest>,
        VopAfsUpdateSendInfoRequest, AfsUpdateWaybillResponse> {

    public AfsUpdateWayBillHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsUpdateWaybillResponse handle(AfsUpdateWaybillRequest<VopAfsUpdateSendInfoRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopAfsUpdateSendInfoRequest vopAfsUpdateSendInfoRequest = request.getRequestObj();

        JdMallRequest<VopAfsUpdateSendInfoResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsUpdateSendInfoRequest);

        VopAfsUpdateSendInfoResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            AfsUpdateWaybillResponse afsUpdateWaybillResponse = new AfsUpdateWaybillResponse();
            afsUpdateWaybillResponse.setResult(result.getResult());
            return afsUpdateWaybillResponse;

        } else {
            String message = MessageFormat.format("京东填写运单信息失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_UPDATE_WAYBILL), this);
    }
}
