package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.checkSkuSaleList.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsCheckSkuSaleListRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsCheckSkuSaleListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQuerySaleabilityRequest;
import com.op.boot.mall.response.product.SkuSaleabilityResponse;
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
public class JdSkuQuerySaleabilityHandler extends JdMallRequestHandler<SkuQuerySaleabilityRequest<VopGoodsCheckSkuSaleListRequest>,
        VopGoodsCheckSkuSaleListRequest, SkuSaleabilityResponse> {


    public JdSkuQuerySaleabilityHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuSaleabilityResponse handle(SkuQuerySaleabilityRequest<VopGoodsCheckSkuSaleListRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsCheckSkuSaleListRequest vopGoodsGetSkuStateListRequest = request.getRequestObj();

        JdMallRequest<VopGoodsCheckSkuSaleListResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSkuStateListRequest);

        VopGoodsCheckSkuSaleListResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {

            SkuSaleabilityResponse skuSaleabilityResponse = new SkuSaleabilityResponse();

            List<SkuSaleabilityResponse.SkuSaleabilityItemResponse> skuSaleabilityItemResponseList = Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream().map(
                    checkSkuSaleGoodsResp -> {
                        SkuSaleabilityResponse.SkuSaleabilityItemResponse skuSaleabilityItemResponse = new SkuSaleabilityResponse.SkuSaleabilityItemResponse();

                        skuSaleabilityItemResponse.setSkuId(checkSkuSaleGoodsResp.getSkuId());
                        skuSaleabilityItemResponse.setName(checkSkuSaleGoodsResp.getSkuName());
                        skuSaleabilityItemResponse.setIsJDLogistics(checkSkuSaleGoodsResp.getLogisticsType());//是否京配，0第三方配送、1京东配送、2未知类型
                        skuSaleabilityItemResponse.setTaxInfo(checkSkuSaleGoodsResp.getOutputVat());
                        skuSaleabilityItemResponse.setSaleState(checkSkuSaleGoodsResp.getSaleState());
                        skuSaleabilityItemResponse.setNoReasonToReturn(String.valueOf(checkSkuSaleGoodsResp.getNoReasonToReturn()));
                        skuSaleabilityItemResponse.setThwa(String.valueOf(checkSkuSaleGoodsResp.getReturnRuleType()));
                        skuSaleabilityItemResponse.setIsCanVAT(String.valueOf(checkSkuSaleGoodsResp.getIsCanVat()));
                        skuSaleabilityItemResponse.setIsSelf(checkSkuSaleGoodsResp.getSelfType());
                        return skuSaleabilityItemResponse;
                    }
            ).collect(Collectors.toList());

            skuSaleabilityResponse.setSkuSaleabilityItemResponseList(skuSaleabilityItemResponseList);
            return skuSaleabilityResponse;
        } else {
            String message = MessageFormat.format("查询京东商品是否可售失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_SALEABILITY), this);
    }
}
