package com.op.sdk.client.account.service;

import com.op.sdk.client.account.model.JdTokenResponse;
import com.op.sdk.client.account.utils.RsaCoderUtils;
import com.op.sdk.client.config.JdAccountProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 京东帐号 Service Impl
 *
 * @author chengdr01
 */
@Slf4j
@Service
public class JdAccountServiceImpl implements JdAccountService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final JdAccountProperties jdAccountProperties;

    public JdAccountServiceImpl(JdAccountProperties jdAccountProperties) {
        this.jdAccountProperties = jdAccountProperties;
    }

    @Override
    public void requestAccessToken() {
        try {
            String encodeUsername = URLEncoder.encode(jdAccountProperties.getUsername(), StandardCharsets.UTF_8.toString());
            String md5Password = DigestUtils.md5Hex(jdAccountProperties.getPassword());
            String ciphertextPassword = RsaCoderUtils.encryptByPrivateKey(md5Password, jdAccountProperties.getRsaKey());
            String encodePassword = URLEncoder.encode(ciphertextPassword, StandardCharsets.UTF_8.toString());
            String encodeRedirectUri = URLEncoder.encode(jdAccountProperties.getRedirectUri(), StandardCharsets.UTF_8.toString());

            String codeUrl = jdAccountProperties.getServerUrl() + "/oauth2/authorizeForVOP?"
                    + "app_key=" + jdAccountProperties.getAppKey()
                    + "&redirect_uri=" + encodeRedirectUri
                    + "&username=" + encodeUsername
                    + "&password=" + encodePassword
                    + "&response_type=code"
                    + "&scope=snsapi_base";

            String codeResp = restTemplate.getForObject(codeUrl, String.class);
            log.info("请求京东token成功，授权码：{}", codeResp);
        }catch (Exception e) {
            log.error("请求京东token异常", e);
        }
    }

    @Override
    public void callbackToken(String code) {
        String accessTokenUrl = jdAccountProperties.getServerUrl() + "/oauth2/access_token?"
                + "app_key=" + jdAccountProperties.getAppKey()
                + "&app_secret=" + jdAccountProperties.getAppSecret()
                + "&code=" + code
                + "&grant_type=authorization_code";

        JdTokenResponse response = restTemplate.getForObject(accessTokenUrl, JdTokenResponse.class);
        log.info("请求京东token成功，token响应：{}", response);
    }

    @Override
    public JdTokenResponse getAccessToken() {
        return null;
    }
}
