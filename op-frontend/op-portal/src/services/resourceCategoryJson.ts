import {ResourceCategory} from "../models/ResourceCategory";

export default [
    {
        categoryName: "管理中心",
        categoryEnName: "management",
        resources: [
            {
                resourceName: "组织管理",
                resourceEnName: "organization",
                actions: [
                    {
                        actionName: "保存",
                        permission: "organization_save",
                    },
                    {
                        actionName: "删除",
                        permission: "organization_delete",
                    },
                    {
                        actionName: "查看",
                        permission: "organization_view",
                    },
                    {
                        actionName: "导出",
                        permission: "organization_export",
                    },
                ]
            },
            {
                resourceName: "用户管理",
                resourceEnName: "user",
                actions: [
                    {
                        actionName: "保存",
                        permission: "organization_save",
                    },
                    {
                        actionName: "删除",
                        permission: "organization_delete",
                    },
                    {
                        actionName: "查看",
                        permission: "organization_view",
                    },
                    {
                        actionName: "导出",
                        permission: "organization_export",
                    },
                ]
            },
            {
                resourceName: "角色管理",
                resourceEnName: "role",
                actions: [
                    {
                        actionName: "保存",
                        permission: "organization_save",
                    },
                    {
                        actionName: "删除",
                        permission: "organization_delete",
                    },
                    {
                        actionName: "查看",
                        permission: "organization_view",
                    },
                    {
                        actionName: "导出",
                        permission: "organization_export",
                    },
                ]
            },
            {
                resourceName: "资源分类管理",
                resourceEnName: "resourceCategory",
                actions: [
                    {
                        actionName: "保存",
                        permission: "organization_save",
                    },
                    {
                        actionName: "删除",
                        permission: "organization_delete",
                    },
                    {
                        actionName: "查看",
                        permission: "organization_view",
                    },
                    {
                        actionName: "导出",
                        permission: "organization_export",
                    },
                ]
            },
            {
                resourceName: "资源管理",
                resourceEnName: "resource",
                actions: [
                    {
                        actionName: "保存",
                        permission: "organization_save",
                    },
                    {
                        actionName: "删除",
                        permission: "organization_delete",
                    },
                    {
                        actionName: "查看",
                        permission: "organization_view",
                    },
                    {
                        actionName: "导出",
                        permission: "organization_export",
                    },
                ]
            },
            {
                resourceName: "菜单管理",
                resourceEnName: "menu",
                actions: [
                    {
                        actionName: "保存",
                        permission: "organization_save",
                    },
                    {
                        actionName: "删除",
                        permission: "organization_delete",
                    },
                    {
                        actionName: "查看",
                        permission: "organization_view",
                    },
                    {
                        actionName: "导出",
                        permission: "organization_export",
                    },
                ]
            },
        ]
    },
    {
        categoryName: "统计中心",
        categoryEnName: "statistics",
    },
    {
        categoryName: "日志中心",
        categoryEnName: "logging",
    },
    {
        categoryName: "监控中心",
        categoryEnName: "monitor",
    },
    {
        categoryName: "附件中心",
        categoryEnName: "attachment",
    },
    {
        categoryName: "文档中心",
        categoryEnName: "document",
    },
] as ResourceCategory[];