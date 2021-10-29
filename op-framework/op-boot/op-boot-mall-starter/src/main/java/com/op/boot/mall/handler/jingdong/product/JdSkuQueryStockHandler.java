package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopkc.SkuInfoGoodsProvider.response.getNewStockById.OpenRpcResult;
import com.jd.open.api.sdk.request.vopkc.VopGoodsGetNewStockByIdRequest;
import com.jd.open.api.sdk.response.vopkc.VopGoodsGetNewStockByIdResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryStockRequest;
import com.op.boot.mall.response.product.SkuStockResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdSkuQueryStockHandler extends JdMallRequestHandler<SkuQueryStockRequest<VopGoodsGetNewStockByIdRequest>,
        VopGoodsGetNewStockByIdRequest, SkuStockResponse> {


    public JdSkuQueryStockHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuStockResponse handle(SkuQueryStockRequest<VopGoodsGetNewStockByIdRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetNewStockByIdRequest vopGoodsGetNewStockByIdRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetNewStockByIdResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetNewStockByIdRequest);

        VopGoodsGetNewStockByIdResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            SkuStockResponse skuStockResponse = new SkuStockResponse();
            List<SkuStockResponse.SkuStockItemResponse> skuStockItemResponseList = Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream().map(
                    getStockByIdGoodsResp -> {
                        SkuStockResponse.SkuStockItemResponse skuStockItemResponse = new SkuStockResponse.SkuStockItemResponse();
                        skuStockItemResponse.setSkuId(getStockByIdGoodsResp.getSkuId());
                        skuStockItemResponse.setStockStateId(getStockByIdGoodsResp.getStockStateType());
                        skuStockItemResponse.setStockStateDesc(getStockByIdGoodsResp.getStockStateDesc());
                        skuStockItemResponse.setRemainNum(getStockByIdGoodsResp.getRemainNum());
                        return skuStockItemResponse;
                    }
            ).collect(Collectors.toList());
            skuStockResponse.setSkuStockItemResponseList(skuStockItemResponseList);
            return skuStockResponse;
        } else {
            String message = MessageFormat.format("查询京东商品库存失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_STOCK), this);
    }
}
