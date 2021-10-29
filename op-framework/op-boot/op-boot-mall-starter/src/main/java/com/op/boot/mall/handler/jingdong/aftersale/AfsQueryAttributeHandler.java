package com.op.boot.mall.handler.jingdong.aftersale;

import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.getGoodsAttributes.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.getGoodsAttributes.SupportedInfoOpenResp;
import com.jd.open.api.sdk.request.vopsh.VopAfsGetGoodsAttributesRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsGetGoodsAttributesResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsQueryAttributeRequest;
import com.op.boot.mall.response.aftersale.AfsAttributeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class AfsQueryAttributeHandler extends JdMallRequestHandler<AfsQueryAttributeRequest<VopAfsGetGoodsAttributesRequest>,
        VopAfsGetGoodsAttributesRequest, AfsAttributeResponse> {

    public AfsQueryAttributeHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsAttributeResponse handle(AfsQueryAttributeRequest<VopAfsGetGoodsAttributesRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        VopAfsGetGoodsAttributesRequest vopAfsGetGoodsAttributesRequest = request.getRequestObj();

        // 2. 执行京东电商请求
        JdMallRequest<VopAfsGetGoodsAttributesResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsGetGoodsAttributesRequest);
        VopAfsGetGoodsAttributesResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult openRpcResult = response.getOpenRpcResult();
        if (openRpcResult.getSuccess()) {
            AfsAttributeResponse afsAttributeResponse = new AfsAttributeResponse();
            List<SupportedInfoOpenResp> supportedInfoOpenRespList = openRpcResult.getResult();
            if (!CollectionUtils.isEmpty(supportedInfoOpenRespList)) {
//                List<AfsAttributeResponse.AfsAttributeItemResponse> afsAttributeItemResultList =
//                        JSONArray.parseArray(JSON.toJSONString(supportedInfoOpenRespList), AfsAttributeResponse.AfsAttributeItemResponse.class);
//                afsAttributeResponse.setAfsAttributeItemResponseList(afsAttributeItemResultList);
            }
            return afsAttributeResponse;
        } else {
            String message = MessageFormat.format("京东查询订单下商品售后权益失败，错误码：{0}，错误消息：{1}",
                    openRpcResult.getResultCode(), openRpcResult.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_QUERY_ATTRIBUTES), this);
    }
}
