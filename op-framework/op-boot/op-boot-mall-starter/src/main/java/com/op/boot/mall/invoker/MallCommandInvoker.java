package com.op.boot.mall.invoker;

import com.op.boot.mall.authentication.MallAuthentication;
import com.op.boot.mall.authentication.MallAuthenticationProviderProxy;
import com.op.boot.mall.command.MallCommand;
import com.op.boot.mall.command.MallCommandFactory;
import com.op.boot.mall.request.MallRequest;
import com.op.boot.mall.response.MallResponse;
import com.op.boot.mall.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 电商命令调用者
 *
 * @author chengdr01
 */
@Slf4j
@Component
public class MallCommandInvoker {
    private final MallAuthenticationProviderProxy mallAuthenticationProviderProxy;
    private final MallCommandFactory mallCommandFactory;

    public MallCommandInvoker(MallAuthenticationProviderProxy mallAuthenticationProviderProxy, MallCommandFactory mallCommandFactory) {
        this.mallAuthenticationProviderProxy = mallAuthenticationProviderProxy;
        this.mallCommandFactory = mallCommandFactory;
    }

    /**
     * 调用电商命令
     *
     * @param mallRequest 电商请求
     * @param <T>         电商请求泛型
     * @param <P>         电商实际请求参数泛型
     * @param <R>         电商响应泛型
     * @return 电商响应
     */
    public <T extends MallRequest<P, R>, P, R extends MallResponse> R invoke(T mallRequest) {
        MallAuthentication authentication = mallAuthenticationProviderProxy
                .loadAuthentication(mallRequest.getMallType(), mallRequest.getAccountName());
        mallRequest.setAuthentication(authentication);
        log.info("成功获取到电商账号【{}-{}】的身份认证凭据", mallRequest.getMallType().getDesc(), mallRequest.getAccountName());

        log.info("准备指定电商请求，电商类型【{}】，方法名【{}】， 请求参数【{}】", mallRequest.getMallType().getDesc(),
                mallRequest.getRequestMethod(), JsonUtils.toString(mallRequest.getRequestObj()));

        MallCommand<T, P, R> command = mallCommandFactory.getCommand(mallRequest.getMallType(), mallRequest.getRequestMethod());
        R mallResponse = command.execute(mallRequest);

        log.info("执行电商请求成功，，电商类型【{}】，方法名【{}】， 请求参数【{}】，请求响应【{}】", mallRequest.getMallType().getDesc(),
                mallRequest.getRequestMethod(), mallRequest.getRequestObj(), JsonUtils.toString(mallResponse));

        return mallResponse;
    }
}
