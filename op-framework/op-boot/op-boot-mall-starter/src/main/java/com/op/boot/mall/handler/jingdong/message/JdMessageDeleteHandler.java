package com.op.boot.mall.handler.jingdong.message;

import com.jd.open.api.sdk.domain.vopxx.MsgRecordProvider.response.deleteClientMsgByIdList.OpenRpcResult;
import com.jd.open.api.sdk.request.vopxx.VopMessageDeleteClientMsgByIdListRequest;
import com.jd.open.api.sdk.response.vopxx.VopMessageDeleteClientMsgByIdListResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.message.MessageDeleteRequest;
import com.op.boot.mall.response.message.MessageDeleteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdMessageDeleteHandler extends JdMallRequestHandler<MessageDeleteRequest<VopMessageDeleteClientMsgByIdListRequest>,
        VopMessageDeleteClientMsgByIdListRequest, MessageDeleteResponse> {

    public JdMessageDeleteHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public MessageDeleteResponse handle(MessageDeleteRequest<VopMessageDeleteClientMsgByIdListRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        VopMessageDeleteClientMsgByIdListRequest vopMessageDeleteClientMsgByIdListRequest = request.getRequestObj();

        // 2. 执行京东电商请求
        JdMallRequest<VopMessageDeleteClientMsgByIdListResponse> jdMallRequest = new JdMallRequest<>(authentication, vopMessageDeleteClientMsgByIdListRequest);
        VopMessageDeleteClientMsgByIdListResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult vopOrderRpcResult = response.getVopOrderRpcResult();
        if (vopOrderRpcResult.getSuccess()) {
            MessageDeleteResponse messageDeleteResponse = new MessageDeleteResponse();
            messageDeleteResponse.setResult(vopOrderRpcResult.getResult());
            return messageDeleteResponse;

        } else {
            String message = MessageFormat.format("京东删除消息失败，错误码：{0}，错误消息：{1}",
                    vopOrderRpcResult.getResultCode(), vopOrderRpcResult.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.MESSAGE_QUERY), this);
    }
}
