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

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     * @param <R>         电商响应泛型
     * @return 电商响应
     */
    public <T extends MallRequest<?, R>, R extends MallResponse> R invoke(T mallRequest) {
        MallAuthentication authentication = mallAuthenticationProviderProxy
                .loadAuthentication(mallRequest.getMallType(), mallRequest.getAccountName());
        mallRequest.setAuthentication(authentication);
        log.info("成功获取到电商账号【{}-{}】的身份认证凭据", mallRequest.getMallType().getDesc(), mallRequest.getAccountName());

        log.info("准备指定电商请求，电商类型【{}】，方法名【{}】， 请求参数【{}】", mallRequest.getMallType().getDesc(),
                mallRequest.getRequestMethod(), JsonUtils.toString(mallRequest.getRequestObj()));

        MallCommand<T, R> command = mallCommandFactory.getCommand(mallRequest.getMallType(), mallRequest.getRequestMethod());
        R mallResponse = command.execute(mallRequest);

        log.info("执行电商请求成功，，电商类型【{}】，方法名【{}】， 请求参数【{}】，请求响应【{}】", mallRequest.getMallType().getDesc(),
                mallRequest.getRequestMethod(), mallRequest.getRequestObj(), JsonUtils.toString(mallResponse));

        return mallResponse;
    }

    /**
     * 批量调用电商命令
     *
     * @param mallRequests 电商请求列表
     * @param <T>          电商请求泛型
     * @param <R>          电商响应泛型
     * @return 电商响应列表
     */
    public <T extends MallRequest<?, R>, R extends MallResponse> List<R> invoke(List<T> mallRequests, Executor executor) {
        List<FutureTask<R>> futureTasks = mallRequests.stream()
                .map(mallRequest -> new FutureTask<>(() -> invoke(mallRequest))).collect(Collectors.toList());

        futureTasks.forEach(executor::execute);

        return IntStream.range(0, futureTasks.size())
                .mapToObj(index -> {
                    FutureTask<R> futureTask = futureTasks.get(index);
                    Class<R> clazz = mallRequests.get(index).getResponseClass();

                    try {
                        return futureTask.get();
                    } catch (InterruptedException e) {
                        log.error(MessageFormat.format("线程中断异常，错误信息【{0}】", e.getMessage()), e);
                        return new MallResponse.ErrorBuilder().errorMsg(e.getMessage()).build(clazz);
                    } catch (ExecutionException e) {
                        log.error(MessageFormat.format("线程执行异常，错误信息【{0}】", e.getMessage()), e);
                        return new MallResponse.ErrorBuilder().errorMsg(e.getMessage()).build(clazz);
                    }
                })
                .collect(Collectors.toList());
    }
}
