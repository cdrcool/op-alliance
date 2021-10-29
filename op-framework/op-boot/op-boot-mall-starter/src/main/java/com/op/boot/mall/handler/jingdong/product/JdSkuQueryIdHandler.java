package com.op.boot.mall.handler.jingdong.product;

import com.google.common.collect.Sets;
import com.jd.open.api.sdk.domain.vopsp.SkuPoolGoodsProvider.response.getSkuListPage.OpenPagingResult;
import com.jd.open.api.sdk.domain.vopsp.SkuPoolGoodsProvider.response.getSkuListPage.OpenRpcResult;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetSkuListPageRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetSkuListPageResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.PoolQuerySkuRequest;
import com.op.boot.mall.request.product.jingdong.JdSkuQueryRequest;
import com.op.boot.mall.response.product.PoolSkuResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */

@Slf4j
@Component
public class JdSkuQueryIdHandler extends JdMallRequestHandler<PoolQuerySkuRequest<JdSkuQueryRequest>,
        JdSkuQueryRequest, PoolSkuResponse> {

    public JdSkuQueryIdHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public PoolSkuResponse handle(PoolQuerySkuRequest<JdSkuQueryRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();
        JdSkuQueryRequest jdSkuQueryRequest = request.getRequestObj();

        HashSet<Long> skuIdList = Sets.newHashSet();
        int pageIndex = 1;
        int pageSize = 100;

        do {
            OpenPagingResult skuListPage = getSkuListPage(authentication, jdSkuQueryRequest.getPoolNum(), pageIndex);
            List<Long> items = skuListPage.getItems();
            if (CollectionUtils.isNotEmpty(items)) {
                skuIdList.addAll(items);
                pageSize = skuListPage.getPageSize();
            }
            if (skuIdList.size() == skuListPage.getPageItemTotal()) {
                break;
            }
            ++pageIndex;

        } while (pageIndex < pageSize);

        PoolSkuResponse poolSkuResponse = new PoolSkuResponse();
        poolSkuResponse.setSkuIdList(new ArrayList<>(skuIdList));
        return poolSkuResponse;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_ID), this);
    }


    private OpenPagingResult getSkuListPage(JdMallAuthentication authentication, String bizPoolId, int pageIndex) {

        VopGoodsGetSkuListPageRequest vopGoodsGetSkuListPageRequest = new VopGoodsGetSkuListPageRequest();
        vopGoodsGetSkuListPageRequest.setBizPoolId(bizPoolId);
        vopGoodsGetSkuListPageRequest.setPageIndex(pageIndex);

        JdMallRequest<VopGoodsGetSkuListPageResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSkuListPageRequest);
        VopGoodsGetSkuListPageResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            return result.getResult();
        } else {
            String message = MessageFormat.format("查询京东池内商品编号失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }
}
