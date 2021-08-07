package com.op.framework.web.common.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 身份认证过滤器
 *
 * @author chengdr01
 */
public class AuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String header = request.getHeader("security-context");
        if (StringUtils.isNotBlank(header)) {
            Map<String, Object> map = objectMapper.readValue(header, new TypeReference<Map<String, Object>>() {
            });
            String userName = (String) map.get("user_name");
            List<String> authorities = (List<String>) map.get("authorities");

            SecurityContext securityContext = SecurityContext.builder()
                    .userName(userName)
                    .authorities(Optional.ofNullable(authorities).orElse(new ArrayList<>()))
                    .build();
            SecurityContextHolder.setContext(securityContext);
        }
        filterChain.doFilter(request, response);
    }
}
