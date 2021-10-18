package com.op.mall.handler.jingdong;

import com.jd.open.api.sdk.request.vopdz.VopAddressQueryJdAreaIdListRequest;
import com.jd.open.api.sdk.response.vopdz.VopAddressQueryJdAreaIdListResponse;
import com.op.mall.client.jingdong.JdMallAuthentication;
import com.op.mall.client.jingdong.JdMallClient;
import com.op.mall.client.jingdong.JdMallRequest;
import com.op.mall.constans.MallMethodConstants;
import com.op.mall.constans.MallType;
import com.op.mall.handler.MallRequestHandlerRegistry;
import com.op.mall.request.AreaLowerAreaRequest;
import com.op.mall.request.MallRequest;
import com.op.mall.request.MallRequestAction;
import com.op.mall.response.AreaLowerAreaResponse;
import com.op.mall.response.MallResponse;

/**
 * 京东地址查询下级地址处理类
 *
 * @author cdrcool
 */
public class JdAreaLowerAreaHandler extends JdMallRequestHandler {
    public JdAreaLowerAreaHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public <T extends MallRequest<R>, R extends MallResponse> R handle(T request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求
        AreaLowerAreaRequest concreteRequest = (AreaLowerAreaRequest) request;
        Object requestObj = concreteRequest.getRequestObj();
        VopAddressQueryJdAreaIdListRequest jdRequest = (VopAddressQueryJdAreaIdListRequest) requestObj;

        // 2. 执行京东电商请求
        JdMallRequest<VopAddressQueryJdAreaIdListResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopAddressQueryJdAreaIdListResponse jdResponse = getJdMallClient().execute(jdMallRequest);

        // 3. 解析为标准电商请求响应
        // 模拟京东电商请求响应解析
        AreaLowerAreaResponse response = new AreaLowerAreaResponse();
        return (R) response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AREA_MAPPING), this);
    }
}
