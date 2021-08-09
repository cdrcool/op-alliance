package com.op.admin.controller;

import com.op.admin.service.ResourceActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 资源动作 Controller
 *
 * @author cdrcool
 */
@Api(tags = "资源动作")
@RequestMapping("resourceAction")
@RestController
public class ResourceActionController {
    private final ResourceActionService resourceActionService;

    public ResourceActionController(ResourceActionService resourceActionService) {
        this.resourceActionService = resourceActionService;
    }

    @ApiOperation("初始化资源路径权限关联")
    @PostMapping("initResourcePathPermissions")
    public Map<String, String> initResourcePathPermissions() {
        return resourceActionService.initResourcePathPermissions();
    }
}
