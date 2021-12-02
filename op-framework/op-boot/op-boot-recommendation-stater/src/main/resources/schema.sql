-- 用户口味偏好表
CREATE TABLE `taste_preferences`
(
    `user_id`     bigint NOT NULL,
    `item_id`     bigint NOT NULL,
    `preference`  float  NOT NULL,
    `timestamp`   timestamp NULL DEFAULT NULL,
    `category_id` bigint NOT NULL,
    `bar_code`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`user_id`, `item_id`),
    KEY           `user_id` (`user_id`),
    KEY           `item_id` (`item_id`),
    KEY           `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 用户推荐结果集
CREATE TABLE `user_recommends`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `user_id`          bigint       NOT NULL,
    `category_id`      bigint       NOT NULL,
    `item_ids`         varchar(200) NOT NULL,
    `timestamp`        timestamp    NOT NULL,
    `recommender_type` tinyint      NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY                `user_id` (`user_id`),
    KEY                `category_id` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 物品相似结果集
CREATE TABLE `item_similarities`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `item_id`          bigint(20) NOT NULL,
    `category_id`      bigint(20) NOT NULL,
    `item_ids`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `timestamp`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `recommender_type` tinyint(4) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY                `item_id` (`item_id`),
    KEY                `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70029 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 用户口味偏好表（原始）
CREATE TABLE `taste_preferences_origin`
(
    `user_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `item_id`     bigint                                                       NOT NULL,
    `preference`  float                                                        NOT NULL,
    `timestamp`   timestamp NULL DEFAULT NULL,
    `category_id` bigint                                                       NOT NULL,
    `bar_code`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    PRIMARY KEY (`user_id`, `item_id`) USING BTREE,
    KEY           `user_id` (`user_id`) USING BTREE,
    KEY           `item_id` (`item_id`) USING BTREE,
    KEY           `category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- 用户ID映射表
CREATE TABLE `user_id_mapping`
(
    `origin_id` varchar(32) NOT NULL,
    `new_id`    bigint      NOT NULL,
    PRIMARY KEY (`origin_id`),
    KEY         `origin_id` (`origin_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
