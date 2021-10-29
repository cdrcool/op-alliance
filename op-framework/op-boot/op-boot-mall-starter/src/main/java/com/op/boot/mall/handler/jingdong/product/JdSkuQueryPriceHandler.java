package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSellPrice.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetSellPriceRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetSellPriceResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryPriceRequest;
import com.op.boot.mall.response.product.SkuPriceResponse;
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
public class JdSkuQueryPriceHandler extends JdMallRequestHandler<SkuQueryPriceRequest<VopGoodsGetSellPriceRequest>,
        VopGoodsGetSellPriceRequest, SkuPriceResponse> {

    public JdSkuQueryPriceHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuPriceResponse handle(SkuQueryPriceRequest<VopGoodsGetSellPriceRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetSellPriceRequest vopGoodsGetSellPriceRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetSellPriceResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSellPriceRequest);

        VopGoodsGetSellPriceResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();


        if (result.getSuccess()) {
            List<SkuPriceResponse.SkuPriceItemResponse> skuPriceItemResponseList = Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream().map(
                    getSellPriceGoodsResp -> {
                        SkuPriceResponse.SkuPriceItemResponse skuPriceItemResponse = new SkuPriceResponse.SkuPriceItemResponse();
                        skuPriceItemResponse.setSkuId(getSellPriceGoodsResp.getSkuId());
                        skuPriceItemResponse.setSkuPrice(getSellPriceGoodsResp.getJdPrice());
                        skuPriceItemResponse.setPrice(getSellPriceGoodsResp.getSalePrice());
//            skuPriceResponse.setMarketPrice(getSellPriceGoodsResp.getMarketPrice());
                        skuPriceItemResponse.setNakedPrice(getSellPriceGoodsResp.getNakedPrice());
                        skuPriceItemResponse.setTaxPrice(getSellPriceGoodsResp.getTaxPrice());
                        skuPriceItemResponse.setTax(getSellPriceGoodsResp.getTaxRatePercentage());
                        return skuPriceItemResponse;
                    }
            ).collect(Collectors.toList());
            SkuPriceResponse skuPriceResponse = new SkuPriceResponse();
            skuPriceResponse.setSkuPriceItemResponseList(skuPriceItemResponseList);
            return skuPriceResponse;

        } else {
            String message = MessageFormat.format("查询京东商品价格失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_PRICE), this);
    }
}
