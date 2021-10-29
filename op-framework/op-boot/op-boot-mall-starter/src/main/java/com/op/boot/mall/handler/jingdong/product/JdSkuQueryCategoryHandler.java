package com.op.boot.mall.handler.jingdong.product;

import com.jd.open.api.sdk.domain.vopsp.CategoryInfoGoodsProvider.response.getCategoryInfoList.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetCategoryInfoListRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetCategoryInfoListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryCategoryRequest;
import com.op.boot.mall.response.product.SkuCategoryResponse;
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
public class JdSkuQueryCategoryHandler extends JdMallRequestHandler<SkuQueryCategoryRequest<VopGoodsGetCategoryInfoListRequest>,
        VopGoodsGetCategoryInfoListRequest, SkuCategoryResponse> {

    public JdSkuQueryCategoryHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public SkuCategoryResponse handle(SkuQueryCategoryRequest<VopGoodsGetCategoryInfoListRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetCategoryInfoListRequest vopGoodsGetCategoryInfoListRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetCategoryInfoListResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetCategoryInfoListRequest);

        VopGoodsGetCategoryInfoListResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            SkuCategoryResponse skuCategoryResponse = new SkuCategoryResponse();

            List<SkuCategoryResponse.SkuCategoryItemResponse> skuCategoryItemResponseList = Optional.ofNullable(result.getResult()).orElse(new ArrayList<>()).stream().map(
                    getCategoryInfoGoodsResp -> {

                        SkuCategoryResponse.SkuCategoryItemResponse skuCategoryItemResponse = new SkuCategoryResponse.SkuCategoryItemResponse();
                        skuCategoryItemResponse.setCatId(String.valueOf(getCategoryInfoGoodsResp.getCategoryId()));
                        long parentId = getCategoryInfoGoodsResp.getParentId();
                        String parentCode = parentId == 0 ? "" : String.valueOf(parentId);
                        skuCategoryItemResponse.setParentId(parentCode);
                        skuCategoryItemResponse.setCatName(getCategoryInfoGoodsResp.getCategoryName());
                        int mallCatClass = getCategoryInfoGoodsResp.getCategoryLevel() + 1;
                        skuCategoryItemResponse.setCatClass(mallCatClass);
                        skuCategoryItemResponse.setState(getCategoryInfoGoodsResp.getNeedShow());
                        return skuCategoryItemResponse;
                    }
            ).collect(Collectors.toList());
            skuCategoryResponse.setSkuCategoryItemResponseList(skuCategoryItemResponseList);
            return skuCategoryResponse;
        } else {
            String message = MessageFormat.format("查询京东商品分类失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_CATEGORY), this);
    }


}
