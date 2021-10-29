package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.querySkuFreight.FreightQueryOpenResp;
import com.jd.open.api.sdk.domain.vopdd.QueryOrderOpenProvider.response.querySkuFreight.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdd.VopOrderQuerySkuFreightRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderQuerySkuFreightResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderQueryFreightRequest;
import com.op.boot.mall.response.order.OrderFreightResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 订单查询商品运费处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderQueryFreightHandler extends JdMallRequestHandler<OrderQueryFreightRequest<VopOrderQuerySkuFreightRequest>,
        VopOrderQuerySkuFreightRequest, OrderFreightResponse> {

    public JdOrderQueryFreightHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderFreightResponse handle(OrderQueryFreightRequest<VopOrderQuerySkuFreightRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderQuerySkuFreightRequest vopOrderQuerySkuFreightRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderQuerySkuFreightResponse> jdMallRequest = new JdMallRequest<>(authentication, vopOrderQuerySkuFreightRequest);
        VopOrderQuerySkuFreightResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东商品运费失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东商品运费失败：" + result.getResultMessage());

        }
        FreightQueryOpenResp freightQueryOpenResp = result.getResult();

        // 4. 转换为标准请求响应
        OrderFreightResponse response = new OrderFreightResponse();
        response.setBaseFreight(freightQueryOpenResp.getBaseFreight());
        response.setFreight(freightQueryOpenResp.getTotalFreight());
        response.setRemoteRegionFreight(freightQueryOpenResp.getRemoteRegionFreight());
        String remoteSku = StringUtils.join(freightQueryOpenResp.getRemoteRegionSkuIdList(), ",");
        response.setRemoteSku(remoteSku);

        return response;
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_QUERY_FREIGHT), this);
    }
}
