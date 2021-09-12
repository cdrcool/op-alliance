package com.op.mall.client;

import com.op.mall.constans.MallType;
import com.op.mall.exception.MallException;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * 电商身份认证凭证凭据管理类
 *
 * @author cdrcool
 */
@Slf4j
public class MallAuthenticationManager {
    private final List<MallAuthenticationProvider> providers;

    public MallAuthenticationManager(List<MallAuthenticationProvider> providers) {
        this.providers = providers;
    }

    /**
     * 获取电商身份认证凭据
     *
     * @param mallType   电商类型
     * @param taxpayerId 纳税人识别号
     * @return 电商身份认证凭据
     */
    public MallAuthentication loadAuthentication(MallType mallType, String taxpayerId) {
        Optional<MallAuthenticationProvider> optional = providers.stream()
                .filter(provider -> provider.supports(mallType))
                .findAny();
        if (optional.isPresent()) {
            return optional.get().loadAuthentication(taxpayerId);
        }

        String message = MessageFormat.format("未定义电商：【{0}】身份认证凭据提供者", mallType.getName());
        log.error(message);
        throw new MallException(message);
    }
}
