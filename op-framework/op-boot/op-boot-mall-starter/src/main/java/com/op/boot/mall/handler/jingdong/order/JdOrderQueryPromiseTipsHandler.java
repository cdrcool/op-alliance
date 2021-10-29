package com.op.boot.mall.handler.jingdong.order;

import com.jd.open.api.sdk.domain.vopdd.QueryPromiseOpenProvider.response.predictSkuPromise.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopdd.QueryPromiseOpenProvider.response.predictSkuPromise.PredictSkuPromiseOpenResp;
import com.jd.open.api.sdk.request.vopdd.VopOrderPredictSkuPromiseRequest;
import com.jd.open.api.sdk.response.vopdd.VopOrderPredictSkuPromiseResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.order.OrderQueryPromiseTipsRequest;
import com.op.boot.mall.response.order.OrderPredictPromiseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 订单查询配送预计送达时间处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdOrderQueryPromiseTipsHandler extends JdMallRequestHandler<OrderQueryPromiseTipsRequest<VopOrderPredictSkuPromiseRequest>,
        VopOrderPredictSkuPromiseRequest, OrderPredictPromiseResponse> {

    public JdOrderQueryPromiseTipsHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public OrderPredictPromiseResponse handle(OrderQueryPromiseTipsRequest<VopOrderPredictSkuPromiseRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopOrderPredictSkuPromiseRequest jdRequest = request.getRequestObj();

        // 3. 执行请求 -> 获取京东响应
        JdMallRequest<VopOrderPredictSkuPromiseResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopOrderPredictSkuPromiseResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        PredictSkuPromiseOpenResp predictSkuPromiseOpenResp = result.getResult();
        if (!result.getSuccess()) {
            log.error("查询京东配送预计送达时间失败，错误码：【{}】，错误消息【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东配送预计送达时间失败：" + result.getResultMessage());
        }

        // 4. 转换为标准请求响应
        OrderPredictPromiseResponse response = new OrderPredictPromiseResponse();
        response.setPredictContent(predictSkuPromiseOpenResp.getPredictContent());

        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.ORDER_QUERY_PREDICT_PROMISE), this);
    }
}
