package com.onepiece.op.authorization.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 遗留授权服务器（spring-security-oauth2）不支持任何授权
 * <a href target="_blank" href="https://tools.ietf.org/html/rfc7517#section-5">JWK Set</a>端点。
 * <p>
 * 这个类添加了特别的支持，以便更好地支持其他使用场景。
 *
 * @author cdrcool
 */
@FrameworkEndpoint
public class JwkSetEndpoint {
    private final KeyPair keyPair;

    public JwkSetEndpoint(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
