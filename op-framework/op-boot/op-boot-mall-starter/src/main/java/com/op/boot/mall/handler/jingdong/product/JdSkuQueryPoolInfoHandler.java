package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopsp.SkuPoolGoodsProvider.response.getSkuPoolInfo.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetSkuPoolInfoRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetSkuPoolInfoResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryPoolInfoRequest;
import com.op.boot.mall.response.product.SkuPoolInfoResponse;
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
public class JdSkuQueryPoolInfoHandler extends JdMallRequestHandler<SkuQueryPoolInfoRequest<VopGoodsGetSkuPoolInfoRequest>,
        VopGoodsGetSkuPoolInfoRequest, SkuPoolInfoResponse> {


    public JdSkuQueryPoolInfoHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuPoolInfoResponse handle(SkuQueryPoolInfoRequest<VopGoodsGetSkuPoolInfoRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetSkuPoolInfoRequest vopGoodsGetSkuPoolInfoRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetSkuPoolInfoResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSkuPoolInfoRequest);

        VopGoodsGetSkuPoolInfoResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {

            SkuPoolInfoResponse skuPoolInfoResponse = new SkuPoolInfoResponse();
            List<SkuPoolInfoResponse.SkuPoolInfoItemResponse> skuPoolInfoItemResponseList = Optional.ofNullable(result.getResult().getSkuPoolList()).orElse(new ArrayList<>()).stream().map(
                    getSkuPoolInfoGoodsResp -> {
                        SkuPoolInfoResponse.SkuPoolInfoItemResponse skuPoolInfoItemResponse = new SkuPoolInfoResponse.SkuPoolInfoItemResponse();

                        skuPoolInfoItemResponse.setBizPoolId(getSkuPoolInfoGoodsResp.getBizPoolId());
                        skuPoolInfoItemResponse.setPoolName(getSkuPoolInfoGoodsResp.getName());

                        return skuPoolInfoItemResponse;
                    }
            ).collect(Collectors.toList());
            skuPoolInfoResponse.setSkuPoolInfoItemResponseList(skuPoolInfoItemResponseList);
            return skuPoolInfoResponse;
        } else {
            String message = MessageFormat.format("查询京东商品池失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_POOL_INFO), this);
    }
}
