package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.checkAreaLimitList.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsCheckAreaLimitListRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsCheckAreaLimitListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuCheckAreaLimitRequest;
import com.op.boot.mall.response.product.SkuCheckAreaLimitResponse;
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
public class JdSkuCheckAreaLimitHandler extends JdMallRequestHandler<SkuCheckAreaLimitRequest<VopGoodsCheckAreaLimitListRequest>,
        VopGoodsCheckAreaLimitListRequest, SkuCheckAreaLimitResponse> {


    public JdSkuCheckAreaLimitHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuCheckAreaLimitResponse handle(SkuCheckAreaLimitRequest<VopGoodsCheckAreaLimitListRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsCheckAreaLimitListRequest vopGoodsCheckAreaLimitListRequest = request.getRequestObj();

        JdMallRequest<VopGoodsCheckAreaLimitListResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsCheckAreaLimitListRequest);

        VopGoodsCheckAreaLimitListResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {

            SkuCheckAreaLimitResponse skuCheckAreaLimitResponse = new SkuCheckAreaLimitResponse();
            List<SkuCheckAreaLimitResponse.SkuCheckAreaLimitItemResponse> skuCheckAreaLimitItemResponseList = Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream().map(
                    checkAreaLimitGoodsResp -> {
                        SkuCheckAreaLimitResponse.SkuCheckAreaLimitItemResponse skuCheckAreaLimitItemResponse = new SkuCheckAreaLimitResponse.SkuCheckAreaLimitItemResponse();
                        skuCheckAreaLimitItemResponse.setSkuId(checkAreaLimitGoodsResp.getSkuId());
                        skuCheckAreaLimitItemResponse.setAreaRestrict(checkAreaLimitGoodsResp.getIsAreaRestrict());
                        return skuCheckAreaLimitItemResponse;
                    }
            ).collect(Collectors.toList());

            skuCheckAreaLimitResponse.setSkuCheckAreaLimitItemResponseList(skuCheckAreaLimitItemResponseList);
            return skuCheckAreaLimitResponse;
        } else {
            String message = MessageFormat.format("查询京东商品区域可售失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_CHECK_AREA_LIMIT), this);
    }


}

