package com.op.admin.listener;

import com.op.admin.service.ResourceActionService;
import com.op.admin.service.WhiteResourceService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 应用缓存初始化
 *
 * @author cdrcool
 */
@Component
public class ApplicationCacheInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final ResourceActionService resourceActionService;
    private final WhiteResourceService whiteResourceService;

    public ApplicationCacheInitializer(ResourceActionService resourceActionService, WhiteResourceService whiteResourceService) {
        this.resourceActionService = resourceActionService;
        this.whiteResourceService = whiteResourceService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        resourceActionService.initResourcePathPermissions();
        whiteResourceService.initWhiteResourcePaths();
    }
}
