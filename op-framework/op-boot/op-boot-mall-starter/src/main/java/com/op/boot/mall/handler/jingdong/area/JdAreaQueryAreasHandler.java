package com.op.boot.mall.handler.jingdong.area;

import com.jd.open.api.sdk.domain.vopdz.QueryAddressOpenProvider.response.queryJdAreaIdList.OpenRpcResult;
import com.jd.open.api.sdk.request.vopdz.VopAddressQueryJdAreaIdListRequest;
import com.jd.open.api.sdk.response.vopdz.VopAddressQueryJdAreaIdListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.area.AreaQueryAreasRequest;
import com.op.boot.mall.response.area.AreaQueryAreasResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 京东地址查询下级地址处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdAreaQueryAreasHandler extends JdMallRequestHandler<AreaQueryAreasRequest<VopAddressQueryJdAreaIdListRequest>,
        VopAddressQueryJdAreaIdListRequest, AreaQueryAreasResponse> {

    public JdAreaQueryAreasHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public AreaQueryAreasResponse handle(AreaQueryAreasRequest<VopAddressQueryJdAreaIdListRequest> request) {
        // 1. 获取京东请求凭证
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 2. 获取京东请求对象
        VopAddressQueryJdAreaIdListRequest jdRequest = request.getRequestObj();

        // 3. 执行京东请求
        JdMallRequest<VopAddressQueryJdAreaIdListResponse> jdMallRequest = new JdMallRequest<>(authentication, jdRequest);
        VopAddressQueryJdAreaIdListResponse jdResponse = getJdMallClient().execute(jdMallRequest);
        OpenRpcResult result = jdResponse.getOpenRpcResult();
        if (!result.getSuccess()) {
            log.error("查询京东下级地址失败，错误码：【{}】，错误消息：【{}】", result.getResultCode(), result.getResultMessage());
            throw new JdMallException(result.getResultCode(), "查询京东下级地址失败：" + result.getResultMessage());
        }

        // 4. 转换为标准请求响应
        AreaQueryAreasResponse response = new AreaQueryAreasResponse();
        response.setItems(Optional.ofNullable(result.getResult().getAreaInfoList()).orElse(new ArrayList<>()).stream()
                .map(areaInfo -> {
                    AreaQueryAreasResponse.AreaQueryAreasItem item = new AreaQueryAreasResponse.AreaQueryAreasItem();
                    item.setAreaCode(String.valueOf(areaInfo.getAreaId()));
                    item.setAreaName(areaInfo.getAreaName());

                    return item;
                }).collect(Collectors.toList()));

        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.AREA_QUERY_AREAS), this);
    }
}
