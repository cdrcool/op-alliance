package com.op.boot.mall.handler.jingdong.product;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuImageList.GetSkuImageGoodsResp;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuImageList.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuImageList.SkuImageItemGoodsResp;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetSkuImageListRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetSkuImageListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryImageRequest;
import com.op.boot.mall.response.product.SkuImageResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdSkuQueryImageHandler extends JdMallRequestHandler<SkuQueryImageRequest<VopGoodsGetSkuImageListRequest>,
        VopGoodsGetSkuImageListRequest, SkuImageResponse> {


    public JdSkuQueryImageHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuImageResponse handle(SkuQueryImageRequest<VopGoodsGetSkuImageListRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetSkuImageListRequest vopGoodsGetSkuImageListRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetSkuImageListResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSkuImageListRequest);

        VopGoodsGetSkuImageListResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();

        if (result.getSuccess()) {
            SkuImageResponse skuImageResponse = new SkuImageResponse();
            List<GetSkuImageGoodsResp> getSkuImageGoodsRespList = result.getResult();

            if (CollectionUtils.isEmpty(getSkuImageGoodsRespList)) {
                skuImageResponse.setSkuImageItemResponseMap(Maps.newHashMap());
            } else {
                Map<Long, List<SkuImageResponse.SkuImageItemResponse>> skuImageResponseListMap = Maps.newHashMap();

                for (GetSkuImageGoodsResp getSkuImageGoodsResp : getSkuImageGoodsRespList) {
                    List<SkuImageResponse.SkuImageItemResponse> skuImageItemResponseList = Lists.newArrayList();
                    long skuId = getSkuImageGoodsResp.getSkuId();
                    List<SkuImageItemGoodsResp> skuImageList = getSkuImageGoodsResp.getSkuImageList();

                    for (SkuImageItemGoodsResp skuImageItemGoodsResp : skuImageList) {
                        SkuImageResponse.SkuImageItemResponse skuImageItemResponse = new SkuImageResponse.SkuImageItemResponse();
                        skuImageItemResponse.setId(skuImageItemGoodsResp.getId());
                        skuImageItemResponse.setSkuId(skuImageItemGoodsResp.getSkuId());
                        skuImageItemResponse.setPath(skuImageItemGoodsResp.getShortPath());
                        skuImageItemResponse.setCreated(skuImageItemGoodsResp.getCreated());
                        skuImageItemResponse.setModified(skuImageItemGoodsResp.getModified());
                        skuImageItemResponse.setIsPrimary(skuImageItemGoodsResp.getIsPrimary());
                        skuImageItemResponse.setFeatures(skuImageItemGoodsResp.getFeatures());
                        skuImageItemResponse.setPosition(skuImageItemGoodsResp.getPosition());
                        skuImageItemResponse.setOrderSort(skuImageItemGoodsResp.getOrderSort());
                        skuImageItemResponse.setYn(skuImageItemGoodsResp.getYn());
                        skuImageItemResponse.setType(skuImageItemGoodsResp.getImgType());
                        skuImageItemResponseList.add(skuImageItemResponse);
                    }
                    skuImageResponseListMap.put(skuId, skuImageItemResponseList);
                }
                skuImageResponse.setSkuImageItemResponseMap(skuImageResponseListMap);
            }
            return skuImageResponse;
        } else {
            String message = MessageFormat.format("查询京东商品图片失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_IMAGE), this);
    }
}
