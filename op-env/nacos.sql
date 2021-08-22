/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 22/08/2021 14:00:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'op-admin-dev.yaml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8090/xxl-job-admin\n    executor:\n      appname: op-admin\n      logpath: D:/Workspace/IdeaProjects/onepiece/op-alliance/xxl-job-logs/applogs/op-admin/jobhandler\n      logretentiondays: 30', '38f31ba5ea50212eccad7a1f77feff60', '2021-06-28 08:50:56', '2021-08-22 06:00:17', 'nacos', '172.18.0.1', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (4, 'op-gateway-dev.yaml', 'DEFAULT_GROUP', 'spring:\n    redis:\n      host: host.docker.internal\n      port: 6379\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}', 'ecd1a51d5b28c268fabea42a43ada991', '2021-06-28 09:50:19', '2021-06-29 10:16:38', 'nacos', '172.18.0.1', '', '', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (5, 'op-auth-dev.yaml', 'DEFAULT_GROUP', 'management:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \"*\"\r\n  endpoint:\r\n    health:\r\n      show-details: always\r\n    logfile:\r\n      enabled: true\r\n  metrics:\r\n    tags:\r\n      application: ${spring.application.name}', 'e8f885065d268ac618841c722ae8d564', '2021-06-28 09:56:49', '2021-06-28 09:56:49', NULL, '172.18.0.1', '', '', NULL, NULL, NULL, 'yaml', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (1, 10, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}', 'affac352db11e8bf87e3ea6e36f65631', '2010-05-05 00:00:00', '2021-08-15 13:35:46', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 11, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ncron: \"*/5 * * * * ?\"', 'a7c806217e54a1700f4dcafbc0131884', '2010-05-05 00:00:00', '2021-08-15 13:41:26', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 12, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ncron: \"0/6 * * * * ?\"', '2cdb1620842a51680c50c192ebed9e18', '2010-05-05 00:00:00', '2021-08-15 13:43:36', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 13, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ncron: \"0/8 * * * * ?\"', '15c8dfb21845e9e33941c4a032bdd6f7', '2010-05-05 00:00:00', '2021-08-15 13:50:28', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 14, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ncron: \"0/6 * * * * ?\"', '2cdb1620842a51680c50c192ebed9e18', '2010-05-05 00:00:00', '2021-08-15 14:00:58', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 15, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ntask:\n  cron: \n    xxTask: \"0/6 * * * * ?\"', '9550b2d80b27118ff336ae908675fbcd', '2010-05-05 00:00:00', '2021-08-15 14:01:25', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 16, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ntask:\n  cron: \n    xx-task: \"0/6 * * * * ?\"', '650f3940a9bd71c1bb83f6ee751ba616', '2010-05-05 00:00:00', '2021-08-15 14:06:16', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 17, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ntask:\n  cron: \n    xx-task: \"0/10 * * * * ?\"', 'e68da85ea148809adc0b9f4de4e841ff', '2010-05-05 00:00:00', '2021-08-15 14:13:51', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 18, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ntask:\n  cron: \n    xx-task: \"0/8 * * * * ?\"', '7c886f6594c9ff2869377384c3e21e3f', '2010-05-05 00:00:00', '2021-08-15 14:20:05', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 19, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\ntask:\n  cron: \n    xx-task: \"0/10 * * * * ?\"', 'e68da85ea148809adc0b9f4de4e841ff', '2010-05-05 00:00:00', '2021-08-22 03:38:51', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 20, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8090/xxl-job-admin\n    executor:\n      appname:  xxl-job-executor-admin\n      logpath: /data/applogs/xxl-job/jobhandler\n      logretentiondays: 30', 'bb2512197a5babc6cc2346a5df6d2241', '2010-05-05 00:00:00', '2021-08-22 04:43:27', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (1, 21, 'op-admin-dev.yaml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://host.docker.internal:3306/onepiece?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=true&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\n    hikari:\n      # 最大连接数，缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)\n      maximum-pool-size: 30\n      # 最小连接数\n      minimum-idle: 10\n      # 等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒\n      connection-timeout: 30000\n      # 一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like \'%timeout%\';）\n      max-lifetime: 1800000\n      # 一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟\n      idle-timeout: 600000\n  sql:\n    init:\n      mode: never\n  redis:\n    host: host.docker.internal\n    port: 6379\n    lettuce:\n      pool:\n        # 最大连接数\n        max-active: 8\n        # 最大空闲连接\n        max-idle: 8\n        # 最小空闲连接\n        min-idle: 0\n        # 最大等待时间\n        max-wait: -1\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n  endpoint:\n    health:\n      show-details: always\n    logfile:\n      enabled: true\n  metrics:\n    tags:\n      application: ${spring.application.name}\n\nswagger:\n  app-name: Sdk Client Web Demo\n  app-desc: Sdk Client Web Demo\n  app-version: 1.0\n  try-host: http://host.docker.internal:${server.port}\n\nxxl:\n  job:\n    admin:\n      addresses: http://127.0.0.1:8090/xxl-job-admin\n    executor:\n      appname: op-admin\n      logpath: /data/applogs/xxl-job/jobhandler\n      logretentiondays: 30', 'a88e9e58f26d2cbe3dc2d0d3150b395d', '2010-05-05 00:00:00', '2021-08-22 06:00:17', 'nacos', '172.18.0.1', 'U', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE INDEX `uk_username_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
