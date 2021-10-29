package com.op.boot.mall.client;

import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.MallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 电商 Token 授予者 proxy
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class MallTokenGranterProxy {
    private final List<MallTokenGranter> granters;

    public MallTokenGranterProxy(List<MallTokenGranter> granters) {
        this.granters = granters;
    }

    /**
     * 申请电商 token
     *
     * @param mallType 电商类型
     * @param params   请求参数
     * @return 电商 token
     */
    public Optional<MallTokenResponse> requestToken(MallType mallType, Map<String, String> params) {
        return getTokenGranter(mallType).requestToken(params);
    }

    /**
     * 刷新电商 token
     *
     * @param mallType 电商类型
     * @param params   请求参数
     * @return 电商token
     */
    public MallTokenResponse refreshToken(MallType mallType, Map<String, String> params) {
        return getTokenGranter(mallType).refreshToken(params);
    }

    /**
     * 申请授权码
     *
     * @param mallType 电商类型
     * @param params   请求参数
     */
    public void authorize(MallType mallType, Map<String, String> params) {
        getOauth2TokenGranter(mallType).authorize(params);

    }

    /**
     * 电商回调接口：使用电商返回的授权码请求并获取电商 token
     *
     * @param mallType         电商类型
     * @param code             授权码
     * @param params           请求参数
     * @param responseConsumer token 响应消费者
     */
    public void callbackToken(MallType mallType, String code, Map<String, String> params, Consumer<MallTokenResponse> responseConsumer) {
        getOauth2TokenGranter(mallType).callbackToken(code, params, responseConsumer);
    }

    /**
     * 获取电商 Token 授予者
     *
     * @param mallType 电商类型
     * @return 电商 Token 授予者
     */
    protected MallTokenGranter getTokenGranter(MallType mallType) {
        Optional<MallTokenGranter> optional = granters.stream()
                .filter(granter -> granter.supports(mallType))
                .findAny();
        String errorMsg = MessageFormat.format("未定义电商：【{0}】 Token 授予者", mallType.getName());
        return optional.orElseThrow(() -> new MallException(errorMsg));
    }

    /**
     * 获取电商 Oauth2 Token 授予者
     *
     * @param mallType 电商类型
     * @return 电商 Oauth2 Token 授予者
     */
    protected MallOauth2TokenGranter getOauth2TokenGranter(MallType mallType) {
        MallTokenGranter granter = getTokenGranter(mallType);
        return (MallOauth2TokenGranter) granter;
    }
}
