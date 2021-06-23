package com.onepiece.op.authorization.controller;

import com.onepiece.op.authorization.dto.OAuth2TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
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
    private final TokenEndpoint tokenEndpoint;

    public AuthenticationController(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    @ApiOperation("获取 Oauth2 访问令牌")
    @PostMapping("/token")
    public OAuth2TokenDto getAccessToken(@RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException, InterruptedException {
        Thread.sleep(10 * 1000);
        String clientId = parameters.get("client_id");
        Principal principal = new UsernamePasswordAuthenticationToken(clientId, null, new ArrayList<>());
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return OAuth2TokenDto.builder()
                .accessToken(accessToken.getValue())
                .tokenType("bearer")
                .refreshToken(accessToken.getRefreshToken().getValue())
                .expiresIn(accessToken.getExpiresIn())
                .build();
    }
}
