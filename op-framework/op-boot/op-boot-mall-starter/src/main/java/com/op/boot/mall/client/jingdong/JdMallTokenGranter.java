package com.op.boot.mall.client.jingdong;

import com.op.boot.mall.client.MallOauth2TokenGranter;
import com.op.boot.mall.client.MallTokenResponse;
import com.op.boot.mall.constans.MallType;
import com.op.boot.mall.exception.JdMallException;
import com.op.boot.mall.properties.JdMallProperties;
import com.op.boot.mall.utils.RsaCoderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 京东电商 Token 授予者
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdMallTokenGranter implements MallOauth2TokenGranter {
    private final JdMallProperties jdMallProperties;
    private final JdMallAuthFeign jdMallAuthFeign;

    public JdMallTokenGranter(JdMallProperties jdMallProperties, JdMallAuthFeign jdMallAuthFeign) {
        this.jdMallProperties = jdMallProperties;
        this.jdMallAuthFeign = jdMallAuthFeign;
    }

    @Override
    public Optional<MallTokenResponse> requestToken(Map<String, String> params) {
        authorize(params);
        return Optional.empty();
    }

    @Override
    public void authorize(Map<String, String> params) {
        // 账号名
        String accountName = params.get("accountName");
        // 账号密码
        String password = params.get("password");
        // 标识字符串
        String state = params.get("state");

        String md5Password = DigestUtils.md5Hex(password.getBytes(StandardCharsets.UTF_8));
        String ciphertextPassword;
        try {
            ciphertextPassword = RsaCoderUtils.encryptByPrivateKey(md5Password, jdMallProperties.getPrivateKey());
        } catch (Exception e) {
            log.error("加密密码异常", e);
            throw new JdMallException("加密密码异常", e);
        }

        jdMallAuthFeign.authorizeForVop(jdMallProperties.getAppKey(),
                jdMallProperties.getRedirectUri(),
                accountName,
                ciphertextPassword,
                "code",
                "snsapi_base",
                state);
    }

    @Override
    public void callbackToken(String code, Map<String, String> accountInfo,
                              Consumer<MallTokenResponse> responseConsumer) {
        // 账号名
        String accountName = accountInfo.get("accountName");

        JdTokenResponse tokenResponse = jdMallAuthFeign.accessToken(jdMallProperties.getAppKey(),
                jdMallProperties.getAppSecret(),
                code,
                "authorization_code");
        log.info("根据鉴权码：{}获取到京东电商 token 响应：{}", code, tokenResponse);

        MallTokenResponse response = MallTokenResponse.builder()
                .mallType(MallType.JINGDONG)
                .accountName(accountName)
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .accessTokenExpiresAt(tokenResponse.getTime() + tokenResponse.getExpiresIn() * 1000)
                .build();

        responseConsumer.accept(response);
    }

    @Override
    public MallTokenResponse refreshToken(Map<String, String> params) {
        // 账号名
        String accountName = params.get("accountName");
        // 刷新 token
        String refreshToken = params.get("refreshToken");

        JdTokenResponse tokenResponse = jdMallAuthFeign.refreshToken(jdMallProperties.getAppKey(),
                jdMallProperties.getAppSecret(),
                "refresh_token",
                refreshToken);

        return MallTokenResponse.builder()
                .mallType(MallType.JINGDONG)
                .accountName(accountName)
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .accessTokenExpiresAt(tokenResponse.getTime() + tokenResponse.getExpiresIn() * 1000)
                .build();
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.JINGDONG.equals(mallType);
    }
}
