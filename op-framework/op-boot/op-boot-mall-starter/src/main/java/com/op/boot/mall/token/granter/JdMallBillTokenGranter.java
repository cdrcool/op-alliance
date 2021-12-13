package com.op.boot.mall.token.granter;

import com.op.boot.mall.account.AccountManager;
import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.response.JdMallBaseResponse;
import com.op.boot.mall.token.feign.JdMallBillAuthFeign;
import com.op.boot.mall.token.request.TokenAcquireRequest;
import com.op.boot.mall.token.request.TokenRefreshRequest;
import com.op.boot.mall.token.response.JdMallTokenResponse;
import com.op.boot.mall.token.response.MallTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 京东电商金采 Token Granter
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class JdMallBillTokenGranter implements MallTokenGranter {
    private final JdMallBillAuthFeign jdMallBillAuthFeign;
    private final AccountManager accountManager;

    public JdMallBillTokenGranter(JdMallBillAuthFeign jdMallBillAuthFeign, AccountManager accountManager) {
        this.jdMallBillAuthFeign = jdMallBillAuthFeign;
        this.accountManager = accountManager;
    }

    @Override
    public Optional<MallTokenResponse> acquireToken(TokenAcquireRequest tokenAcquireRequest) {
        String accountName = tokenAcquireRequest.getAccountName();
        String password = tokenAcquireRequest.getPassword();
        String appKey = tokenAcquireRequest.getAppKey();
        String appSecret = tokenAcquireRequest.getAppSecret();
        String grantType = "access_token";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String md5Password = DigestUtils.md5Hex(password.toLowerCase().getBytes(StandardCharsets.UTF_8));
        String sign = DigestUtils.md5Hex((appSecret + timestamp + appKey + accountName + md5Password + grantType + appSecret)
                .getBytes(StandardCharsets.UTF_8)).toUpperCase();

        JdMallBaseResponse<JdMallTokenResponse> baseResponse = jdMallBillAuthFeign
                .getAccessToken(grantType, appKey, timestamp, accountName, md5Password, sign);
        JdMallTokenResponse tokenResponse = baseResponse.getResult();

        MallTokenResponse response = MallTokenResponse.builder()
                .mallType(MallType.JD_BILL)
                .accountName(accountName)
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .accessTokenExpiresAt(tokenResponse.getTime() + tokenResponse.getExpiresIn() * 1000)
                .build();

        return Optional.of(response);
    }

    @Override
    public MallTokenResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        JdMallTokenResponse jdMallTokenResponse = jdMallBillAuthFeign.refreshToken(tokenRefreshRequest.getRefreshToken(),
                tokenRefreshRequest.getAppKey(), tokenRefreshRequest.getAppSecret());

        MallTokenResponse tokenResponse = MallTokenResponse.builder()
                .mallType(MallType.JD_BILL)
                .accountName(tokenRefreshRequest.getAccountName())
                .accessToken(jdMallTokenResponse.getAccessToken())
                .refreshToken(jdMallTokenResponse.getRefreshToken())
                .accessTokenExpiresAt(jdMallTokenResponse.getTime() + jdMallTokenResponse.getExpiresIn() * 1000)
                .build();

        accountManager.updateTokenResponse(tokenResponse);

        return tokenResponse;
    }

    @Override
    public boolean supports(MallType mallType) {
        return MallType.JD_BILL.equals(mallType);
    }
}
