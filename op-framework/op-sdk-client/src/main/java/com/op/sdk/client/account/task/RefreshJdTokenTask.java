package com.op.sdk.client.account.task;

import com.op.sdk.client.account.service.JdAccountService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 刷新京东token定时任务
 *
 * @author cdrcool
 */
@Component
public class RefreshJdTokenTask {
    private final JdAccountService jdAccountService;

    public RefreshJdTokenTask(JdAccountService jdAccountService) {
        this.jdAccountService = jdAccountService;
    }

    @Scheduled(cron = "${jd.account.refresh-token-cron}")
    public void execute() {
        jdAccountService.refreshAllToken();
    }
}
