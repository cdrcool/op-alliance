package com.op.boot.mall.handler.jingdong.aftersale;

import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.findRefundInfo.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsh.VopAfsFindRefundInfoRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsFindRefundInfoResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsQueryDetailRequest;
import com.op.boot.mall.response.aftersale.AfsDetailResponse;
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
public class AfsQueryDetailHandler extends JdMallRequestHandler<AfsQueryDetailRequest<VopAfsFindRefundInfoRequest>,
        VopAfsFindRefundInfoRequest, AfsDetailResponse> {

    public AfsQueryDetailHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsDetailResponse handle(AfsQueryDetailRequest<VopAfsFindRefundInfoRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        VopAfsFindRefundInfoRequest vopAfsFindRefundInfoRequest = request.getRequestObj();
        vopAfsFindRefundInfoRequest.setThirdApplyId("");
        vopAfsFindRefundInfoRequest.setOrderId(0L);
        vopAfsFindRefundInfoRequest.setTimestamp("");
        vopAfsFindRefundInfoRequest.setVersion("");
        vopAfsFindRefundInfoRequest.setSignmethod("");


        // 2. 执行京东电商请求
        JdMallRequest<VopAfsFindRefundInfoResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsFindRefundInfoRequest);
        VopAfsFindRefundInfoResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult returnType = response.getReturnType();
        if (returnType.getSuccess()) {
            return null; // JSONUtil.parse(JSONUtil.toString(returnType.getResult()), AfsDetailResponse.class);
        } else {
            String message = MessageFormat.format("京东查询售后单明细失败，错误码：{0}，错误消息：{1}",
                    returnType.getResultCode(), returnType.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_QUERY_DETAIL), this);
    }
}
