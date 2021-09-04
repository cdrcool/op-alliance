package com.op.authorization.controller;

import com.op.framework.web.common.api.response.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份认证 controller
 *
 * @author cdrcool
 */
@Api(tags = "身份认证 Controller")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private static final String TOKEN_TYPE = "Bearer";
    private static final String CLIENT_ID = "password";
    private static final String CLIENT_SECRET = "secret";
    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String GRANT_TYPE_REFRESH = "refresh_token";
    private static final String SCOPE = "scope";
    private final TokenEndpoint tokenEndpoint;

    public AuthenticationController(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    @ApiOperation("获取 Oauth2 Token")
    @PostMapping("/getToken")
    public OauthTokenResponse getAccessToken(@Valid @RequestBody OauthTokenRequest request)
            throws HttpRequestMethodNotSupportedException {
        // 构造请求参数
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("username", request.getUsername());
        parameters.put("password", request.getPassword());
        parameters.put("grant_type", GRANT_TYPE_PASSWORD);
        parameters.put("scope", SCOPE);

        // 请求 Oauth2 Token
        Principal principal = new UsernamePasswordAuthenticationToken(CLIENT_ID, null, new ArrayList<>());
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        if (accessToken == null) {
            throw new BusinessException("获取 Oauth2 访问令牌失败，值为空");
        }

        // 构造请求响应
        return OauthTokenResponse.builder()
                .accessToken(accessToken.getValue())
                .tokenType(TOKEN_TYPE)
                .refreshToken(accessToken.getRefreshToken().getValue())
                .expiresIn(accessToken.getExpiresIn())
                .build();
    }

    @ApiOperation("刷新 Oauth2 Token")
    @PostMapping("/refreshToken")
    public OauthTokenResponse refreshAccessToken(@Valid @RequestParam String refreshToken)
            throws HttpRequestMethodNotSupportedException {
        // 构造请求参数
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("client_secret", CLIENT_SECRET);
        parameters.put("grant_type", GRANT_TYPE_REFRESH);
        parameters.put("refresh_token", refreshToken);
        parameters.put("scope", SCOPE);

        // 刷新 Oauth2 Token
        Principal principal = new UsernamePasswordAuthenticationToken(CLIENT_ID, null, new ArrayList<>());
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        if (accessToken == null) {
            throw new BusinessException("刷新 Oauth2 访问令牌失败，值为空");
        }

        // 构造请求响应
        return OauthTokenResponse.builder()
                .accessToken(accessToken.getValue())
                .tokenType(TOKEN_TYPE)
                .refreshToken(accessToken.getRefreshToken().getValue())
                .expiresIn(accessToken.getExpiresIn())
                .build();
    }
}
