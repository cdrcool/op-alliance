package com.op.boot.mall.authentication;

import com.op.boot.mall.constants.MallType;
import com.op.boot.mall.exception.MallException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 电商身份认证凭据提供者代理类
 *
 * @author cdrcool
 */
@Slf4j
@Component
public class MallAuthenticationProviderProxy implements MallAuthenticationProvider {
    private final List<MallAuthenticationProvider> providers;

    public MallAuthenticationProviderProxy(List<MallAuthenticationProvider> providers) {
        this.providers = providers;
    }

    /**
     * 获取电商身份认证凭据
     *
     * @param mallType    电商类型
     * @param accountName 账号名
     * @return 电商身份认证凭据
     */
    @Override
    public MallAuthentication loadAuthentication(MallType mallType, String accountName) {
        Optional<MallAuthentication> optional = providers.stream()
                .map(provider -> provider.loadAuthentication(mallType, accountName))
                .filter(Objects::nonNull)
                .findAny();

        if (optional.isPresent()) {
            return optional.get();
        }

        throw new MallException(MessageFormat.format("未定义电商【{0}】身份认证凭据提供者", mallType.getDesc()));
    }
}
