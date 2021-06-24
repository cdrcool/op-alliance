package com.op.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

/**
 * 授权服务器通常具有密钥 rotation 策略，并且密钥不会硬编码到应用程序代码中。不过，为了简单起见，这里未使用密钥 rotation。
 *
 * @author cdrcool
 */
@Configuration
public class KeyConfiguration {
    private final KeyStoreProperties keyStoreProperties;

    public KeyConfiguration(KeyStoreProperties keyStoreProperties) {
        this.keyStoreProperties = keyStoreProperties;
    }

    @Bean
    public KeyPair keyPair() {
        ClassPathResource resource = new ClassPathResource(keyStoreProperties.getLocation());
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, keyStoreProperties.getSecret().toCharArray());
        return keyStoreKeyFactory.getKeyPair(keyStoreProperties.getAlias(), keyStoreProperties.getPassword().toCharArray());
    }
}
