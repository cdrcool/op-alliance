package com.op.sdk.client.account.task;

import com.op.sdk.client.account.service.ThirdAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 第三方token刷新定时任务
 *
 * @author cdrcool
 */
@Component
public class ThirdTokenRefreshTask {
    private final Map<String, ThirdAccountService> thirdAccountServices;

    public ThirdTokenRefreshTask(Map<String, ThirdAccountService> thirdAccountServices) {
        this.thirdAccountServices = thirdAccountServices;
    }

    @Scheduled(cron = "${sdk.accounts.refresh-token-cron}")
    public void execute() {
        thirdAccountServices.values().forEach(ThirdAccountService::refreshAllToken);
    }
}
