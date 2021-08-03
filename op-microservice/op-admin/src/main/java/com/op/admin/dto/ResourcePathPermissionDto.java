package com.op.admin.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 资源路径权限 DTO
 *
 * @author cdrcool
 */
@ApiModel(description = "资源路径权限 DTO")
@Data
public class ResourcePathPermissionDto {
    /**
     * 资源路径
     */
    private String resourcePath;

    /**
     * 动作路径路径
     */
    private String actionPath;

    /**
     * 权限标识
     */
    private String permission;
}
