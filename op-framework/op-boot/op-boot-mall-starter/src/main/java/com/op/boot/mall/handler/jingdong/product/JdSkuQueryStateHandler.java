package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuStateList.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetSkuStateListRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetSkuStateListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryStateRequest;
import com.op.boot.mall.response.product.SkuStateResponse;
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
public class JdSkuQueryStateHandler extends JdMallRequestHandler<SkuQueryStateRequest<VopGoodsGetSkuStateListRequest>,
        VopGoodsGetSkuStateListRequest, SkuStateResponse> {


    public JdSkuQueryStateHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuStateResponse handle(SkuQueryStateRequest<VopGoodsGetSkuStateListRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetSkuStateListRequest vopGoodsGetSkuStateListRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetSkuStateListResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSkuStateListRequest);

        VopGoodsGetSkuStateListResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            SkuStateResponse skuStateResponse = new SkuStateResponse();
            List<SkuStateResponse.SkuStateItemResponse> skuStateItemResponseList = Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream().map(
                    getSkuStateGoodsResp -> {
                        SkuStateResponse.SkuStateItemResponse skuStateItemResponse = new SkuStateResponse.SkuStateItemResponse();
                        skuStateItemResponse.setSkuId(getSkuStateGoodsResp.getSkuId());
                        skuStateItemResponse.setState(getSkuStateGoodsResp.getSkuState());
                        return skuStateItemResponse;
                    }
            ).collect(Collectors.toList());
            skuStateResponse.setSkuStateItemResponseList(skuStateItemResponseList);
            return skuStateResponse;
        } else {
            String message = MessageFormat.format("查询京东商品状态失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_STATE), this);
    }


}
