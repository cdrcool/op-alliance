package com.op.authorization.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 认证服务器不支持用户参数自定义名称，因此我们需要扩展默认值。
 * 默认情况下，它使用{@code user_name}属性，不过更好的做法是遵循
 * <a target="_blank" href="https://tools.ietf.org/html/rfc7519">JWT规范</a>中定义的{@code sub}属性
 *
 * @author cdrcool
 */
public class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        response.put("sub", authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }
}
