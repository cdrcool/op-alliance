package com.op.authorization.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.op.authorization.feignclient.OauthClientFeignClient;
import com.op.authorization.feignclient.dto.OauthClientDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author cdrcool
 * @see ClientDetailsService
 * <p>
 * 需要加上 {@link Primary} 注解，不然会抛出 StackOverflowError 异常(https://blog.csdn.net/qq_35425070/article/details/108915454)
 */
@Slf4j
@Primary
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OauthClientFeignClient oauthClientFeignClient;

    public ClientDetailsServiceImpl(OauthClientFeignClient oauthClientFeignClient) {
        this.oauthClientFeignClient = oauthClientFeignClient;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetailsDTO oauthClientDetails = oauthClientFeignClient.getByClientId(clientId);

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(oauthClientDetails.getClientId());
        clientDetails.setClientSecret(oauthClientDetails.getClientSecret());
        clientDetails.setAuthorizedGrantTypes(Arrays.stream(Optional.ofNullable(oauthClientDetails.getAuthorizedGrantTypes()).orElse("").split(",")).collect(Collectors.toList()));
        clientDetails.setScope(Arrays.stream(Optional.ofNullable(oauthClientDetails.getScope()).orElse("").split(",")).collect(Collectors.toList()));
        clientDetails.setRegisteredRedirectUri(Arrays.stream(Optional.ofNullable(oauthClientDetails.getWebServerRedirectUri()).orElse("").split(",")).collect(Collectors.toSet()));
        clientDetails.setAuthorities(Arrays.stream(Optional.ofNullable(oauthClientDetails.getAuthorities()).orElse("").split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        clientDetails.setResourceIds(Arrays.stream(Optional.ofNullable(oauthClientDetails.getResourceIds()).orElse("").split(",")).collect(Collectors.toList()));
        clientDetails.setAccessTokenValiditySeconds(oauthClientDetails.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(oauthClientDetails.getRefreshTokenValidity());
        clientDetails.setAutoApproveScopes("true".equals(oauthClientDetails.getAutoapprove()) ? clientDetails.getScope() : new HashSet<>());

        String additionalInformation = oauthClientDetails.getAdditionalInformation();
        try {
            Map<String, ?> additionalInformationMap = objectMapper.readValue(additionalInformation, new TypeReference<Map<String, ?>>() {
            });
            clientDetails.setAdditionalInformation(additionalInformationMap);
        } catch (JsonProcessingException e) {
            log.error("反序列化clientId为【" + clientId + "】的 oauth-client 异常", e);
        }

        return clientDetails;
    }
}
