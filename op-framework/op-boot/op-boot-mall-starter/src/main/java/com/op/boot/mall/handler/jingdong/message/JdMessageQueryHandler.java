package com.op.boot.mall.handler.jingdong.message;

import com.jd.open.api.sdk.domain.vopxx.MsgRecordProvider.response.queryTransByVopNormal.OpenRpcResult;
import com.jd.open.api.sdk.domain.vopxx.MsgRecordProvider.response.queryTransByVopNormal.VopBizTransMessage;
import com.jd.open.api.sdk.request.vopxx.VopMessageQueryTransByVopNormalRequest;
import com.jd.open.api.sdk.response.vopxx.VopMessageQueryTransByVopNormalResponse;
import com.op.boot.mall.client.jingdong.JdMallAuthentication;
import com.op.boot.mall.client.jingdong.JdMallClient;
import com.op.boot.mall.client.jingdong.JdMallRequest;
import com.op.boot.mall.constans.MallMethodConstants;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.handler.MallRequestHandlerRegistry;
import com.op.boot.mall.handler.jingdong.JdMallRequestHandler;
import com.op.boot.mall.request.MallRequestAction;
import com.op.boot.mall.request.message.MessageQueryRequest;
import com.op.boot.mall.response.message.MessageQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 京东订单取消处理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdMessageQueryHandler extends JdMallRequestHandler<MessageQueryRequest<VopMessageQueryTransByVopNormalRequest>,
        VopMessageQueryTransByVopNormalRequest, MessageQueryResponse> {

    public JdMessageQueryHandler(JdMallClient jdMallClient) {
        super(jdMallClient);
    }

    @Override
    public MessageQueryResponse handle(MessageQueryRequest<VopMessageQueryTransByVopNormalRequest> request) {
        JdMallAuthentication authentication = (JdMallAuthentication) request.getAuthentication();

        // 1. 转换为京东电商请求对象
        VopMessageQueryTransByVopNormalRequest vopMessageQueryTransByVopNormalRequest = request.getRequestObj();

        // 2. 执行京东电商请求
        JdMallRequest<VopMessageQueryTransByVopNormalResponse> jdMallRequest = new JdMallRequest<>(authentication, vopMessageQueryTransByVopNormalRequest);
        VopMessageQueryTransByVopNormalResponse response = getJdMallClient().execute(jdMallRequest);

        OpenRpcResult result = response.getOpenRpcResult();
        if (result.getSuccess()) {
            MessageQueryResponse messageQueryResponse = new MessageQueryResponse();
            List<VopBizTransMessage> vopBizTransMessageList = result.getResult();
            List<String> messageList = new ArrayList<>(); // ListUtils.emptyIfNull(vopBizTransMessageList).stream().map(JsonUtil::toString).collect(Collectors.toList());
            messageQueryResponse.setMessageList(messageList);
            return messageQueryResponse;

        } else {
            String message = MessageFormat.format("京东查询消息失败，错误码：{0}，错误消息：{1}",
                    result.getResultCode(), result.getResultMessage());
            log.error(message);
            throw new JdMallException(message);
        }
    }

    @Override
    public void afterPropertiesSet() {
        MallRequestHandlerRegistry.addHandler(new MallRequestAction(MallType.JINGDONG, MallMethodConstants.MESSAGE_QUERY), this);
    }
}
