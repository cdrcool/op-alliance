package com.op.boot.mall.client.jingdong.bill;

import com.op.boot.mall.client.MallTokenGranter;
import com.op.boot.mall.client.MallTokenResponse;
import com.op.boot.mall.client.jingdong.JdBaseResponse;
import com.op.boot.mall.client.jingdong.JdTokenResponse;
import com.op.boot.mall.constans.MallType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

/**
 * 京东电商金采 Token 授予者
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class JdMallBillTokenGranter implements MallTokenGranter {
    private final JdMallBillAuthFeign jdMallBillAuthFeign;

    public JdMallBillTokenGranter(JdMallBillAuthFeign jdMallBillAuthFeign) {
        this.jdMallBillAuthFeign = jdMallBillAuthFeign;
    }

    @Override
    public Optional<MallTokenResponse> requestToken(Map<String, String> params) {
        // 账号名
        String accountName = params.get("accountName");
        // 账号密码
        String password = params.get("password");
        // 应用 key
        String appKey = params.get("appKey");
        // 应用 秘钥
        String appSecret = params.get("appSecret");

        String grantType = "access_token";
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String md5Password = DigestUtils.md5Hex(password.toLowerCase().getBytes(StandardCharsets.UTF_8));
        String sign = DigestUtils.md5Hex((appSecret + timestamp + appKey + accountName + md5Password + grantType + appSecret).getBytes(StandardCharsets.UTF_8)).toUpperCase();

        JdBaseResponse<JdTokenResponse> baseResponse = jdMallBillAuthFeign.getAccessToken(grantType, appKey, timestamp, accountName, md5Password, sign);
        JdTokenResponse tokenResponse = baseResponse.getResult();

        MallTokenResponse response = MallTokenResponse.builder()
                .mallType(MallType.JINGDONG_BILL)
                .accountName(accountName)
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .accessTokenExpiresAt(tokenResponse.getTime() + tokenResponse.getExpiresIn() * 1000)
                .build();

        return Optional.of(response);
    }

    @Override
    public MallTokenResponse refreshToken(Map<String, String> params) {
        // 账号名
        String accountName = params.get("accountName");
        // 应用 key
        String appKey = params.get("appKey");
        // 应用 秘钥
        String appSecret = params.get("appSecret");
        // 刷新 token
        String refreshToken = params.get("refreshToken");

        JdTokenResponse tokenResponse = jdMallBillAuthFeign.refreshToken(refreshToken, appKey, appSecret);

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
        return MallType.JINGDONG_BILL.equals(mallType);
    }
}
