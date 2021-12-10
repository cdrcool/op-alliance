package com.op.boot.mall.token.granter;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.token.feign.JdMallAuthFeign;
import com.op.boot.mall.token.request.TokenAcquireRequest;
import com.op.boot.mall.token.request.TokenAuthorizeRequest;
import com.op.boot.mall.token.request.TokenCallbackRequest;
import com.op.boot.mall.token.request.TokenRefreshRequest;
import com.op.boot.mall.token.response.JdMallTokenResponse;
import com.op.boot.mall.token.response.MallTokenResponse;
import com.op.boot.mall.utils.RsaCoderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 京东电商 Token Granter
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class JdMallTokenGranter implements MallOauth2TokenGranter {
    private final JdMallAuthFeign jdMallAuthFeign;

    public JdMallTokenGranter(JdMallAuthFeign jdMallAuthFeign) {
        this.jdMallAuthFeign = jdMallAuthFeign;
    }

    @Override
    public Optional<MallTokenResponse> acquireToken(TokenAcquireRequest tokenAcquireRequest) {
        TokenAuthorizeRequest tokenAuthorizeRequest = new TokenAuthorizeRequest();
        tokenAuthorizeRequest.setAccountName(tokenAcquireRequest.getAccountName());
        tokenAuthorizeRequest.setPassword(tokenAcquireRequest.getPassword());
        tokenAuthorizeRequest.setAppKey(tokenAcquireRequest.getAppKey());
        tokenAuthorizeRequest.setPrivateKey(tokenAcquireRequest.getPrivateKey());
        tokenAuthorizeRequest.setState(tokenAcquireRequest.getState());

        authorize(tokenAuthorizeRequest);

        return Optional.empty();
    }

    @Override
    public void authorize(TokenAuthorizeRequest tokenAuthorizeRequest) {
        String md5Password = DigestUtils.md5Hex(tokenAuthorizeRequest.getPassword().getBytes(StandardCharsets.UTF_8));
        String ciphertextPassword;
        try {
            ciphertextPassword = RsaCoderUtils.encryptByPrivateKey(md5Password, tokenAuthorizeRequest.getPrivateKey());
        } catch (Exception e) {
            log.error("加密京东密码异常", e);
            throw new JdMallException("加密京东密码异常", e);
        }

        jdMallAuthFeign.authorizeForVop(tokenAuthorizeRequest.getAppKey(),
                "",
                tokenAuthorizeRequest.getAccountName(),
                ciphertextPassword,
                "code",
                "snsapi_base",
                tokenAuthorizeRequest.getState());
    }

    @Override
    public void callback(TokenCallbackRequest tokenCallbackRequest, Consumer<MallTokenResponse> responseConsumer) {
        JdMallTokenResponse tokenResponse = jdMallAuthFeign.accessToken(tokenCallbackRequest.getAppKey(),
                tokenCallbackRequest.getAppSecret(),
                tokenCallbackRequest.getCode(),
                "authorization_code");
        log.info("根据鉴权码【{}】获取到京东电商 Token 响应【{}】", tokenCallbackRequest.getCode(), tokenResponse);

        MallTokenResponse response = MallTokenResponse.builder()
                .mallType(MallType.JD)
                .accountName(tokenCallbackRequest.getAccountName())
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .accessTokenExpiresAt(tokenResponse.getTime() + tokenResponse.getExpiresIn() * 1000)
                .build();

        responseConsumer.accept(response);
    }

    @Override
    public MallTokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        JdMallTokenResponse tokenResponse = jdMallAuthFeign.refreshToken(tokenRefreshRequest.getAppKey(),
                tokenRefreshRequest.getAppSecret(),
                "refresh_token",
                tokenRefreshRequest.getRefreshToken());

        return MallTokenResponse.builder()
                .mallType(MallType.JD)
                .accountName(tokenRefreshRequest.getAccountName())
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .accessTokenExpiresAt(tokenResponse.getTime() + tokenResponse.getExpiresIn() * 1000)
                .build();
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.JD.equals(mallType);
    }
}
