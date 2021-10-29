package com.op.boot.mall.handler.jingdong.product;

import com.google.common.collect.Lists;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuDetailInfo.GetSkuPoolInfoGoodsResp;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuDetailInfo.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuDetailInfo.ParamAttributeResp;
import com.jd.open.api.sdk.domain.vopsp.SkuInfoGoodsProvider.response.getSkuDetailInfo.ParamGroupAttributeGoodsResp;
import com.jd.open.api.sdk.request.vopsp.VopGoodsGetSkuDetailInfoRequest;
import com.jd.open.api.sdk.response.vopsp.VopGoodsGetSkuDetailInfoResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.properties.JdMallProperties;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.product.SkuQueryDetailRequest;
import com.op.boot.mall.response.product.SkuAttribute;
import com.op.boot.mall.response.product.SkuAttributeGroup;
import com.op.boot.mall.response.product.SkuDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdSkuQueryDetailHandler extends JdMallRequestHandler<SkuQueryDetailRequest<VopGoodsGetSkuDetailInfoRequest>,
        VopGoodsGetSkuDetailInfoRequest, SkuDetailResponse> {

    private final JdMallProperties jdMallProperties;

    public JdSkuQueryDetailHandler(JdMallClient jdMallClient, JdMallProperties jdMallProperties) {
        super(jdMallClient);
        this.jdMallProperties = jdMallProperties;
    }

    @Override
    public SkuDetailResponse handle(SkuQueryDetailRequest<VopGoodsGetSkuDetailInfoRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        VopGoodsGetSkuDetailInfoRequest vopGoodsGetSkuDetailInfoRequest = request.getRequestObj();

        JdMallRequest<VopGoodsGetSkuDetailInfoResponse> jdMallRequest = new JdMallRequest<>(authentication, vopGoodsGetSkuDetailInfoRequest);

        VopGoodsGetSkuDetailInfoResponse response = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = response.getOpenRpcResult();

        if (result.getSuccess()) {
            GetSkuPoolInfoGoodsResp getSkuPoolInfoGoodsResp = result.getResult();
            SkuDetailResponse skuDetailResponse = new SkuDetailResponse();
            skuDetailResponse.setSkuId(String.valueOf(getSkuPoolInfoGoodsResp.getSkuId()));
            skuDetailResponse.setState(String.valueOf(getSkuPoolInfoGoodsResp.getSkuState()));
            skuDetailResponse.setBrandName(getSkuPoolInfoGoodsResp.getBrandName());
            skuDetailResponse.setName(getSkuPoolInfoGoodsResp.getSkuName());
            skuDetailResponse.setSaleUnit(getSkuPoolInfoGoodsResp.getSaleUnit());
            skuDetailResponse.setWeight(getSkuPoolInfoGoodsResp.getWeight());
            skuDetailResponse.setProductArea(getSkuPoolInfoGoodsResp.getProductArea());
            skuDetailResponse.setWareQD(getSkuPoolInfoGoodsResp.getWareInfo());
            skuDetailResponse.setImagePath(getSkuPoolInfoGoodsResp.getImagePath());
            skuDetailResponse.setParam(getSkuPoolInfoGoodsResp.getSpecificParam());

            skuDetailResponse.setCapacity(getSkuPoolInfoGoodsResp.getCapacity());
            skuDetailResponse.setSeoModel(getSkuPoolInfoGoodsResp.getSeoModel());
            skuDetailResponse.setNintroduction(getSkuPoolInfoGoodsResp.getIntroducePc());

            skuDetailResponse.setUpc(getSkuPoolInfoGoodsResp.getUpcCode());
            skuDetailResponse.setWserve(getSkuPoolInfoGoodsResp.getWarrantDesc());

            Integer lowestBuy = getSkuPoolInfoGoodsResp.getLowestBuy();
            skuDetailResponse.setLowestBuy(0);
            if (lowestBuy != null) {
                skuDetailResponse.setLowestBuy(lowestBuy);
            }
//            skuProductDetailResponse.setTaxInfo(getSkuPoolInfoGoodsResp.getTaxRatePercentage());
//            productDetailResponse.setTaxCode(getSkuPoolInfoGoodsResp.getTaxCode());

            skuDetailResponse.setCategory(getSkuPoolInfoGoodsResp.getCategory());
            skuDetailResponse.setIntroduction(getSkuPoolInfoGoodsResp.getIntroduce());

            //属性
            List<SkuAttributeGroup> paramDetailJson = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(getSkuPoolInfoGoodsResp.getParamGroupAttrList())) {
                for (ParamGroupAttributeGoodsResp paramGroupAttributeGoodsResp : getSkuPoolInfoGoodsResp.getParamGroupAttrList()) {
                    SkuAttributeGroup skuAttributeGroup = new SkuAttributeGroup();
                    skuAttributeGroup.setGroupName(paramGroupAttributeGoodsResp.getParamGroupName());

                    List<SkuAttribute> atts = Lists.newArrayList();

                    for (ParamAttributeResp paramAttributeResp : paramGroupAttributeGoodsResp.getParamAttributeList()) {
                        SkuAttribute jdSkuAttribute = new SkuAttribute();

                        jdSkuAttribute.setAttName(paramAttributeResp.getParamAttrName());

                        List<String[]> paramAttrValList = paramAttributeResp.getParamAttrValList();
                        List<String> vals = Lists.newArrayList();
                        paramAttrValList.forEach(strArr -> {
                            vals.addAll(Arrays.asList(strArr));
                        });

                        jdSkuAttribute.setVals(vals);
                        atts.add(jdSkuAttribute);
                    }
                    skuAttributeGroup.setAtts(atts);
                    paramDetailJson.add(skuAttributeGroup);
                }
            }
            skuDetailResponse.setParamDetailJson(paramDetailJson);

            //配送类型，0：未知，1：京东配送，2：厂直配送
            int logisticsType = getSkuPoolInfoGoodsResp.getLogisticsType();
            skuDetailResponse.setIsJDLogistics(logisticsType);
            String isFactory = logisticsType == 1 ? "2" : "1";
            skuDetailResponse.setIsFactoryShip(isFactory);
            skuDetailResponse.setIsSelf(getSkuPoolInfoGoodsResp.getSelfSellType());//是否自营，1自营，0其他

            String imagePath = Optional.ofNullable(skuDetailResponse.getImagePath())
                    .filter(StringUtils::isNotBlank)
                    .map(s -> jdMallProperties.getProperties().get("img-prefix") + s)
                    .orElse("");
            skuDetailResponse.setImagePath(imagePath);
            return skuDetailResponse;
        } else {
            String message = MessageFormat.format("查询京东商品详情失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.PRODUCT_QUERY_DETAIL), this);
    }
}
