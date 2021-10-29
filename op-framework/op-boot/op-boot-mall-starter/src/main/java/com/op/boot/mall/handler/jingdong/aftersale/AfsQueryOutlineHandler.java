package com.op.boot.mall.handler.jingdong.aftersale;

import com.google.common.collect.Lists;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.getAfsOutline.AfsOutLineOpenResp;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.getAfsOutline.ApplyInfoOpenResp;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.getAfsOutline.OpenPagingResult;
import com.jd.open.api.sdk.domain.vopsh.QueryAfterSaleOpenProvider.response.getAfsOutline.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsh.VopAfsGetAfsOutlineRequest;
import com.jd.open.api.sdk.response.vopsh.VopAfsGetAfsOutlineResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.aftersale.AfsQueryOutlineRequest;
import com.op.boot.mall.request.aftersale.jingdong.JdAfsQueryRequest;
import com.op.boot.mall.response.aftersale.AfsOutlineResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class AfsQueryOutlineHandler extends JdMallRequestHandler<AfsQueryOutlineRequest<JdAfsQueryRequest>,
        JdAfsQueryRequest, AfsOutlineResponse> {

    public AfsQueryOutlineHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AfsOutlineResponse handle(AfsQueryOutlineRequest<JdAfsQueryRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        JdAfsQueryRequest requestObj = request.getRequestObj();
        List<AfsOutLineOpenResp> afsOutLineOpenRespList = Lists.newArrayList();
        int pageIndex = 1;

        while (true) {
            OpenPagingResult openPagingResult = queryAfsOutline(authentication, requestObj.getOrderId(), requestObj.getThirdApplyId(), pageIndex);

            int totalPageIndex = openPagingResult.getPageIndex();
            List<AfsOutLineOpenResp> itemList = openPagingResult.getItems();

            if (CollectionUtils.isEmpty(itemList)) {
                break;
            }
            afsOutLineOpenRespList.addAll(itemList);
            if (pageIndex >= totalPageIndex) {
                break;
            }
            pageIndex++;
        }
        try {
            List<AfsOutlineResponse.AfsOutlineItemResponse> afsOutlineItemResultList = build(afsOutLineOpenRespList);

            AfsOutlineResponse afsOutlineResponse = new AfsOutlineResponse();
            afsOutlineResponse.setAfsOutlineItemResponseList(afsOutlineItemResultList);
            return afsOutlineResponse;
        } catch (InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AFS_QUERY_OUTLINE), this);
    }

    private OpenPagingResult queryAfsOutline(JdMallAuthentication authentication, long orderId, String thirdApplyId, int pageIndex) {

        VopAfsGetAfsOutlineRequest vopAfsGetAfsOutlineRequest = new VopAfsGetAfsOutlineRequest();
        vopAfsGetAfsOutlineRequest.setOrderId(orderId);
        vopAfsGetAfsOutlineRequest.setThirdApplyId(thirdApplyId);
        vopAfsGetAfsOutlineRequest.setWareId(null);
        vopAfsGetAfsOutlineRequest.setPageIndex(pageIndex);
        vopAfsGetAfsOutlineRequest.setPageSize(100);

        JdMallRequest<VopAfsGetAfsOutlineResponse> jdMallRequest = new JdMallRequest<>(authentication, vopAfsGetAfsOutlineRequest);
        VopAfsGetAfsOutlineResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult openRpcResult = response.getOpenRpcResult();
        if (openRpcResult.getSuccess()) {
            return openRpcResult.getResult();
        } else {
            String message = MessageFormat.format("京东查询售后服务概要错误，错误码：{0}，错误消息：{1}",
                    openRpcResult.getResultCode(), openRpcResult.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    private List<AfsOutlineResponse.AfsOutlineItemResponse> build(List<AfsOutLineOpenResp> itemList) throws InvocationTargetException, IllegalAccessException {

        List<AfsOutlineResponse.AfsOutlineItemResponse> afsOutlineItemResponseList = Lists.newArrayList();

        for (AfsOutLineOpenResp afsOutLineOpenResp : itemList) {

            AfsOutlineResponse.AfsOutlineItemResponse afsOutlineItemResponse = new AfsOutlineResponse.AfsOutlineItemResponse();

            ApplyInfoOpenResp applyInfo = afsOutLineOpenResp.getApplyInfo();
            // BeanUtils.copyProperties(afsOutlineItemResponse, applyInfo);
            afsOutlineItemResponseList.add(afsOutlineItemResponse);
        }
        return afsOutlineItemResponseList;
    }
}
