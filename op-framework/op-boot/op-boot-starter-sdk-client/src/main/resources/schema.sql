CREATE TABLE `sdk_third_account`
(
    `id`                        varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
    `account_type`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账号类型',
    `account`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账号名',
    `password`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账号密码',
    `access_token`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '访问令牌',
    `refresh_token`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '刷新令牌',
    `access_token_expires_at`   timestamp NULL DEFAULT NULL COMMENT '访问令牌过期时间',
    `refresh_token_expires_at`  timestamp NULL DEFAULT NULL COMMENT '刷新令牌过期时间',
    `access_token_update_time`  datetime                                                     DEFAULT NULL COMMENT '访问令牌更新时间',
    `refresh_token_update_time` datetime                                                     DEFAULT NULL COMMENT '刷新令牌更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='第三方账号';