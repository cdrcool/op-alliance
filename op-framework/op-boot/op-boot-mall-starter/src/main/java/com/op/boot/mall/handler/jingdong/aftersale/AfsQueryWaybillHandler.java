package com.op.boot.mall.handler.jingdong.aftersale;

import com.google.common.collect.Lists;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.queryLogicticsInfo.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.queryLogicticsInfo.WayBillInfoOpenResp;
import com.jd.open.api.sdk.request.vopsh.VopAfsQueryLogicticsInfoRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsQueryLogicticsInfoResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsQueryWaybillRequest;
import com.op.boot.mall.request.aftersale.jingdong.JdAfsQueryRequest;
import com.op.boot.mall.response.aftersale.AfsWaybillResponse;
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
public class AfsQueryWaybillHandler extends JdMallRequestHandler<AfsQueryWaybillRequest<JdAfsQueryRequest>,
        JdAfsQueryRequest, AfsWaybillResponse> {

    public AfsQueryWaybillHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsWaybillResponse handle(AfsQueryWaybillRequest<JdAfsQueryRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        JdAfsQueryRequest requestObj = request.getRequestObj();
        List<AfsWaybillResponse.AfsWaybillItemResponse> afsWaybillItemResultList = Lists.newArrayList();
        int pageIndex = 1;

        while (true) {
            List<AfsWaybillResponse.AfsWaybillItemResponse> afsWaybillItemResponseList = queryAfsWayBill(authentication, requestObj.getOrderId(),
                    requestObj.getThirdApplyId(), pageIndex);

            if (CollectionUtils.isEmpty(afsWaybillItemResponseList)) {
                break;
            }
            afsWaybillItemResultList.addAll(afsWaybillItemResponseList);
            pageIndex++;
        }
        AfsWaybillResponse afsWaybillResponse = new AfsWaybillResponse();
        afsWaybillResponse.setAfsWaybillItemResponseList(afsWaybillItemResultList);
        return afsWaybillResponse;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_QUERY_WAYBILL), this);
    }

    private List<AfsWaybillResponse.AfsWaybillItemResponse> queryAfsWayBill(JdMallAuthentication authentication, long orderId, String thirdApplyId, int pageIndex) {

        VopAfsQueryLogicticsInfoRequest vopAfsQueryLogicticsInfoRequest = new VopAfsQueryLogicticsInfoRequest();
        vopAfsQueryLogicticsInfoRequest.setThirdApplyId(thirdApplyId);
        vopAfsQueryLogicticsInfoRequest.setOrderId(orderId);
        vopAfsQueryLogicticsInfoRequest.setOriginalOrderId(orderId);
        vopAfsQueryLogicticsInfoRequest.setPageNo(pageIndex);
        vopAfsQueryLogicticsInfoRequest.setPageSize(100);

        JdMallRequest<VopAfsQueryLogicticsInfoResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsQueryLogicticsInfoRequest);
        VopAfsQueryLogicticsInfoResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult openRpcResult = response.getOpenRpcResult();
        if (openRpcResult.getSuccess()) {
            List<WayBillInfoOpenResp> result = openRpcResult.getResult();
            if (CollectionUtils.isEmpty(result)) {
                return null;
            }
            return null; // JSONArray.parseArray(JSON.toJSONString(result), AfsWaybillResponse.AfsWaybillItemResponse.class);
        } else {
            String message = MessageFormat.format("京东查询售后单物流信息失败，错误码：{0}，错误消息：{1}",
                    openRpcResult.getResultCode(), openRpcResult.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }
}
