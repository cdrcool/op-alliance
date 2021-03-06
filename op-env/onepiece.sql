/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : onepiece

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 22/08/2021 13:31:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('AdminClusteredScheduler', 'xxJob', 'DEFAULT', '0/5 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_FIRED_TRIGGERS` VALUES ('AdminClusteredScheduler', 'DESKTOP-8G5LND716296074388771629607439411', 'xxJob', 'DEFAULT', 'DESKTOP-8G5LND71629607438877', 1629610260546, 1629610265000, 5, 'ACQUIRED', NULL, NULL, '0', '0');

-- ----------------------------
-- Table structure for QRTZ_JOB
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB`;
CREATE TABLE `QRTZ_JOB`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `job_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `job_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `job_class` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '???????????????',
  `cron_exps` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'cron?????????',
  `status` int NOT NULL COMMENT '???????????????0-????????????1-????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Quratz??????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_JOB
-- ----------------------------
INSERT INTO `QRTZ_JOB` VALUES (2, 'xxJob', 'xx Job', 'com.op.admin.job.XxJob', '0/5 * * * * ?', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('AdminClusteredScheduler', 'xxJob', 'DEFAULT', NULL, 'com.op.admin.job.XxJob', '0', '0', '0', '0', 0x230D0A23467269204175672032302031313A33363A34302043535420323032310D0A);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('AdminClusteredScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('AdminClusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('AdminClusteredScheduler', 'DESKTOP-8G5LND71629607438877', 1629610255789, 20000);

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('AdminClusteredScheduler', 'xxJob', 'DEFAULT', 'xxJob', 'DEFAULT', NULL, 1629610265000, 1629610260000, 5, 'ACQUIRED', 'CRON', 1629430600000, 0, NULL, 2, '');

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `pid` int NOT NULL COMMENT '????????????id',
  `menu_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `menu_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `menu_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `is_show` tinyint(1) NOT NULL COMMENT '???????????????0-?????????1-?????????',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `menu_no` int NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_index`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (1, -1, '?????????', 'DesktopOutlined', '/workbench', 1, '', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (2, -1, '????????????', 'TeamOutlined', '/admin', 1, '', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (3, 2, '????????????', NULL, '/admin/organization', 1, 'organization_view', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (4, 2, '????????????', NULL, '/admin/user', 1, 'user_view', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (5, 2, '???????????????', NULL, '/admin/userGroup', 1, 'user_group_view', 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (6, 2, '????????????', NULL, '/admin/role', 1, 'role_view', 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (7, 2, '??????????????????', NULL, '/admin/resourceCategory', 1, 'resource_category_view', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (8, 2, '????????????', NULL, '/admin/resource', 1, 'resource_view', 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (9, 2, '?????????????????????', NULL, '/admin/whiteResource', 1, 'white_resource_view', 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (10, 2, '????????????', NULL, '/admin/menu', 1, 'menu_view', 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (11, 2, 'OAuth???????????????', NULL, '/admin/oauthClient', 1, 'oauth_client_view', 9, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (12, -1, '????????????', '', '/personal/center', 0, '', 98, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (13, -1, '????????????', NULL, '/personal/settings', 0, '', 99, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (14, -1, '????????????', 'ToolOutlined', '/config', 1, '', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (15, 14, '????????????', NULL, '/config/job', 1, '', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `admin_oauth_client_details`;
CREATE TABLE `admin_oauth_client_details`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `client_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '???????????????',
  `client_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '???????????????',
  `authorized_grant_types` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????????????????',
  `scope` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `web_server_redirect_uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '???????????????',
  `authorities` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????????????????/???????????????????????????',
  `resource_ids` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????ids',
  `access_token_validity` int NULL DEFAULT NULL COMMENT '?????????????????????',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT '?????????????????????',
  `autoapprove` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '???????????????????????????????????????????????????????????????true|false|read|write???',
  `additional_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '????????????',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id_index`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'oauth2-client' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_oauth_client_details
-- ----------------------------
INSERT INTO `admin_oauth_client_details` VALUES (1, 'client', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'client_credentials,refresh_token', NULL, NULL, NULL, NULL, 86400, NULL, NULL, NULL);
INSERT INTO `admin_oauth_client_details` VALUES (2, 'implicit', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'implicit', NULL, 'https://www.baidu.com', NULL, NULL, 86400, NULL, NULL, NULL);
INSERT INTO `admin_oauth_client_details` VALUES (3, 'password', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'password,refresh_token', NULL, NULL, NULL, NULL, 86400, 604800, NULL, NULL);
INSERT INTO `admin_oauth_client_details` VALUES (4, 'code', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'authorization_code,refresh_token', 'all', 'http://localhost:8080/authorized', NULL, NULL, 86400, 604800, 'true', NULL);

-- ----------------------------
-- Table structure for admin_organization
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization`;
CREATE TABLE `admin_organization`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `pid` int NOT NULL COMMENT '????????????id',
  `org_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `org_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `org_code_link` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '?????????????????????????????????????????????????????????????????????????????????',
  `org_type` int NOT NULL COMMENT '???????????????1-?????????2-?????????3-????????????4-????????????5-?????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_index`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization
-- ----------------------------
INSERT INTO `admin_organization` VALUES (1, -1, '??????????????????', '0001', '0001', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (2, 1, '????????????', '0001', '0001.0001', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (3, 2, '?????????', '0001', '0001.0001.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (4, 2, '?????????', '0002', '0001.0001.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (5, 2, '?????????', '0003', '0001.0001.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (6, 1, '????????????', '0002', '0001.0002', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (7, 6, '?????????', '0001', '0001.0002.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (8, 6, '?????????', '0002', '0001.0002.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (9, 6, '?????????', '0003', '0001.0002.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (10, 1, '????????????', '0003', '0001.0003', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (11, 10, '?????????', '0001', '0001.0003.0001', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (12, 10, '?????????', '0002', '0001.0003.0002', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (13, 10, '?????????', '0003', '0001.0003.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (14, 1, '????????????', '0004', '0001.0004', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (15, 14, '?????????', '0001', '0001.0004.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (16, 14, '?????????', '0002', '0001.0004.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (17, 1, '???????????????', '0005', '0001.0005', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (18, 17, '???????????????????????????', '0001', '0001.0005.0001', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (19, 21, '?????????', '0001', '0001.0005.0001.0003.0001', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (20, 19, '?????????', '0002', '0001.0005.0001.0003.0001.0002', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (21, 18, '?????????', '0003', '0001.0005.0001.0003', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_organization_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization_resource_action_relation`;
CREATE TABLE `admin_organization_resource_action_relation`  (
  `org_id` int NULL DEFAULT NULL COMMENT '??????id',
  `action_id` int NULL DEFAULT NULL COMMENT '????????????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `org_id_index`(`org_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????-??????????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization_resource_action_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_organization_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization_role_relation`;
CREATE TABLE `admin_organization_role_relation`  (
  `org_id` int NULL DEFAULT NULL COMMENT '??????id',
  `role_id` int NULL DEFAULT NULL COMMENT '??????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `org_id_index`(`org_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????-????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization_role_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_resource
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `category_id` int NOT NULL COMMENT '????????????id',
  `resource_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `resource_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `resource_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `resource_no` int NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id_index`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource
-- ----------------------------
INSERT INTO `admin_resource` VALUES (1, 1, '????????????', '/organization', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (2, 1, '????????????', '/user', NULL, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (3, 1, '????????????', '/role', NULL, 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (4, 1, '??????????????????', '/resourceCategory', NULL, 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (5, 1, '????????????', '/resource', NULL, 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (6, 1, '????????????', '/menu', NULL, 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (7, 1, '???????????????', '/userGroup', NULL, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (8, 1, 'OAuth???????????????', '/oauthClientDetails', NULL, 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (11, 1, '?????????????????????', '/whiteResource', NULL, 9, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_resource_action
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_action`;
CREATE TABLE `admin_resource_action`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `resource_id` int NOT NULL COMMENT '??????id',
  `action_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `action_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `action_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `action_no` int NULL DEFAULT NULL COMMENT '????????????',
  `permission` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `resource_id_index`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource_action
-- ----------------------------
INSERT INTO `admin_resource_action` VALUES (1, 1, '??????', '/save', '', 1, 'organization_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (2, 1, '??????', '/delete,/batchDelete', '', 2, 'organization_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (3, 1, '??????', '/query**,/get**,/find**,/load**', '', 3, 'organization_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (4, 1, '??????', '/export', '', 4, 'organization_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (5, 2, '??????', '/save', NULL, 1, 'user_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (6, 2, '??????', '/delete,/batchDelete', NULL, 2, 'user_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (7, 2, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'user_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (8, 2, '??????', '/export', NULL, 4, 'user_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (9, 3, '??????', '/save', NULL, 1, 'role_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (10, 3, '??????', '/delete,/batchDelete', NULL, 2, 'role_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (11, 3, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'role_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (12, 3, '??????', '/export', NULL, 4, 'role_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (13, 4, '??????', '/save', NULL, 1, 'resource_category_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (14, 4, '??????', '/delete,/batchDelete', NULL, 2, 'resource_category_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (15, 4, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'resource_category_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (16, 4, '??????', '/export', NULL, 4, 'resource_category_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (17, 5, '??????', '/save', NULL, 1, 'resource_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (18, 5, '??????', '/delete,/batchDelete', NULL, 2, 'resource_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (19, 5, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'resource_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (20, 5, '??????', '/export', NULL, 4, 'resource_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (21, 6, '??????', '/save', NULL, 1, 'menu_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (22, 6, '??????', '/delete,/batchDelete', NULL, 2, 'menu_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (23, 6, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'menu_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (24, 6, '??????', '/export', NULL, 4, 'menu_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (25, 1, '??????', '/assignRoles,/assignResourceActions', NULL, 5, 'organization_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (26, 2, '????????????', '/changePassword', NULL, 5, 'user__change_password', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (27, 2, '??????/????????????', '/changeEnabled', NULL, 6, 'user_change_enabled', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (28, 2, '??????', '/assignRoles,/assignResourceActions,/assignOrganizations', NULL, 7, 'user_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (29, 3, '??????/????????????', '/changeEnabled', NULL, 5, 'role_change_enabled', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (30, 3, '??????', '/assignResourceActions', NULL, 6, 'role_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (31, 6, '??????/????????????', '/changeVisibility', NULL, 5, 'menu_change_visibility', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (32, 7, '??????', '/save', NULL, 1, 'user_group_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (33, 7, '??????', '/delete,/batchDelete', NULL, 2, 'user_group_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (34, 7, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'user_group_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (35, 7, '??????', '/export', NULL, 4, 'user_group_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (36, 7, '??????', '/assignRoles,/assignResourceActions', NULL, 5, 'user_group_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (37, 8, '??????', '/save', NULL, 1, 'oauth_client_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (38, 8, '??????', '/delete,/batchDelete', NULL, 2, 'oauth_client_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (39, 8, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'oauth_client_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (40, 8, '??????', '/export', NULL, 4, 'oauth_client_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (41, 4, '????????????????????????', '/refreshResourcePermissions', NULL, 5, 'resource_permission_refresh', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (42, 11, '??????', '/save', NULL, 1, 'white_resource_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (43, 11, '??????', '/delete,/batchDelete', NULL, 2, 'white_resource_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (44, 11, '??????', '/query**,/get**,/find**,/load**', NULL, 3, 'white_resource_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (45, 11, '??????', '/export', NULL, 4, 'white_resource_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (46, 11, '??????/?????????????????????', '/changeEnabled', NULL, 5, 'white_resource_change_enabled', 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_category`;
CREATE TABLE `admin_resource_category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `category_icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `server_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `category_no` int NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource_category
-- ----------------------------
INSERT INTO `admin_resource_category` VALUES (1, '????????????', 'TeamOutlined', 'op-admin', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (2, '????????????', 'AreaChartOutlined', 'op-statistics', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (3, '????????????', 'DatabaseOutlined', 'op-logging', 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (4, '????????????', 'PictureOutlined', 'op-monitor', 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (5, '????????????', 'FileImageOutlined', 'op-attachment', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (6, '????????????', 'ReadOutlined', 'op-document', 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (7, '????????????2', 'TeamOutlined', 'op-admin2', 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (8, '????????????2', 'AreaChartOutlined', 'op-statistics-2', 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (9, '????????????2', 'DatabaseOutlined', 'op-logging-2', 9, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (10, '????????????2', 'PictureOutlined', 'op-monitor-2', 10, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (11, '????????????2', 'FileImageOutlined', 'op-attachment-2', 11, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (12, '????????????2', 'ReadOutlined', 'op-document-2', 12, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `role_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `status` int NOT NULL COMMENT '???????????????0-?????????1-?????????',
  `role_no` int NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, '?????????', 'admin', '????????????????????????', 1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (2, '??????', 'guest', '??????????????????', 1, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (3, '?????????2', 'admin2', '????????????????????????', 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (4, '??????2', 'guest2', '??????????????????', 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (5, '?????????3', 'admin3', '????????????????????????', 1, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (6, '??????3', 'guest3', '??????????????????', 1, 6, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (7, '?????????4', 'admin4', '????????????????????????', 1, 7, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (8, '??????4', 'guest4', '??????????????????', 1, 8, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (9, '?????????5', 'admin5', '????????????????????????', 1, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (10, '??????5', 'guest5', '??????????????????', 1, 10, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (11, '?????????6', 'admin6', '????????????????????????', 1, 11, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (12, '??????6', 'guest6', '??????????????????', 1, 12, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (13, '?????????7', 'admin7', '????????????????????????', 1, 13, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (14, '??????7', 'guest7', '??????????????????', 1, 14, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (15, '?????????8', 'admin8', '????????????????????????', 1, 15, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (16, '??????8', 'guest8', '??????????????????', 1, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (17, '?????????9', 'admin9', '????????????????????????', 1, 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (18, '??????9', 'guest9', '??????????????????', 1, 18, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (19, '?????????10', 'admin10', '????????????????????????', 1, 19, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (20, '??????10', 'guest10', '??????????????????', 1, 20, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (21, '?????????11', 'admin11', '????????????????????????', 1, 21, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (22, '??????11', 'guest11', '??????????????????', 1, 22, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (23, '?????????12', 'admin12', '????????????????????????', 1, 23, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (24, '??????12', 'guest12', '??????????????????', 1, 24, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_role_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_resource_action_relation`;
CREATE TABLE `admin_role_resource_action_relation`  (
  `role_id` int NULL DEFAULT NULL COMMENT '??????id',
  `action_id` int NULL DEFAULT NULL COMMENT '????????????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `role_id_index`(`role_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????-??????????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_resource_action_relation
-- ----------------------------
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 23, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 9, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 10, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 11, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 12, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 13, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 14, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 15, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 16, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 17, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 18, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 19, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 20, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 21, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 22, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 23, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 24, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 25, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 26, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 27, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 28, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 32, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 33, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 34, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 35, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 36, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 29, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 30, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 31, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 37, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 38, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 39, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 40, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 41, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 42, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 43, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 44, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 45, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (1, 46, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 34, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 11, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 15, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 19, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 39, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role_resource_action_relation` VALUES (2, 44, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `org_id` int NOT NULL COMMENT '??????id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '?????????',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '??????',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '??????',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????',
  `signature` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '?????????',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????',
  `gender` int NULL DEFAULT NULL COMMENT '?????????1-??????2-??????',
  `birthday` date NULL DEFAULT NULL COMMENT '????????????',
  `status` int NOT NULL COMMENT '???????????????0-?????????1-?????????2-?????????3-?????????4-???????????????',
  `user_no` int NOT NULL DEFAULT 999 COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_index`(`username`) USING BTREE,
  INDEX `org_id_index`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 1, 'admin', '$2a$10$Nyv6JowsaRTBpU405amyLu0Un6FalfYzbhmS5AqTlRzZ949wiS5a2', '?????????', NULL, NULL, '13669064460', '13669064460@139.com', 1, '1989-09-15', 1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (2, 1, 'guest', '$2a$10$3JSddK51ItcFc31MLfk42OLSYkEw4PD1Y1tNSX3Xzr1d8O7TkI96a', '??????', NULL, NULL, NULL, NULL, NULL, NULL, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (3, 2, 'lemon', '$2a$10$pT1QtdGPnICYMT3Vy.R8jOcvDMbwOfc7aVmGBVjdJapcsYHS3BMJq', '??????', NULL, NULL, '13620600060', '13620600060@139.com', 2, '1989-04-22', 1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_group
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group`;
CREATE TABLE `admin_user_group`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `group_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '???????????????',
  `group_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '???????????????',
  `group_no` int NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '?????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group
-- ----------------------------
INSERT INTO `admin_user_group` VALUES (1, '??????????????????', '', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_group_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_resource_action_relation`;
CREATE TABLE `admin_user_group_resource_action_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `action_id` int NULL DEFAULT NULL COMMENT '????????????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `group_id_index`(`group_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '?????????-??????????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_resource_action_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_group_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_role_relation`;
CREATE TABLE `admin_user_group_role_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `role_id` int NULL DEFAULT NULL COMMENT '??????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `group_id_index`(`group_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '?????????-????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_role_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_group_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_user_relation`;
CREATE TABLE `admin_user_group_user_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `user_id` int NULL DEFAULT NULL COMMENT '??????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `group_id_index`(`group_id`) USING BTREE,
  INDEX `user_id_index`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '?????????-????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_user_relation
-- ----------------------------
INSERT INTO `admin_user_group_user_relation` VALUES (1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_organization_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_organization_relation`;
CREATE TABLE `admin_user_organization_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '??????id',
  `org_id` int NULL DEFAULT NULL COMMENT '??????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `org_id_index`(`org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????-??????????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_organization_relation
-- ----------------------------
INSERT INTO `admin_user_organization_relation` VALUES (1, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_organization_relation` VALUES (1, 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_organization_relation` VALUES (1, 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_organization_relation` VALUES (1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_resource_action_relation`;
CREATE TABLE `admin_user_resource_action_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '??????id',
  `action_id` int NULL DEFAULT NULL COMMENT '????????????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????-??????????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_resource_action_relation
-- ----------------------------
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 9, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 10, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 11, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 12, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 13, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 14, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 15, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 16, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 17, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 18, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 19, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 20, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 21, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 22, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 23, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_resource_action_relation` VALUES (1, 24, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role_relation`;
CREATE TABLE `admin_user_role_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '??????id',
  `role_id` int NULL DEFAULT NULL COMMENT '??????id',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '??????-????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_role_relation
-- ----------------------------
INSERT INTO `admin_user_role_relation` VALUES (1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_role_relation` VALUES (2, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user_role_relation` VALUES (2, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_white_resource
-- ----------------------------
DROP TABLE IF EXISTS `admin_white_resource`;
CREATE TABLE `admin_white_resource`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '??????',
  `resource_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `resource_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '????????????',
  `resource_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '????????????',
  `remove_authorization` tinyint(1) NOT NULL COMMENT '??????????????????????????????',
  `status` int NOT NULL COMMENT '???????????????0-?????????1-?????????',
  `resource_no` int NULL DEFAULT NULL COMMENT '????????????',
  `version` int NULL DEFAULT NULL COMMENT '?????????',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '??????????????????',
  `creator_id` int NULL DEFAULT NULL COMMENT '?????????id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '????????????',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '???????????????id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '??????????????????',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '??????id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '???????????????' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_white_resource
-- ----------------------------
INSERT INTO `admin_white_resource` VALUES (1, '??????????????????', '/op-auth/auth/getToken', NULL, 1, 1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_white_resource` VALUES (2, '??????????????????', '/op-auth/auth/refreshToken', NULL, 1, 1, 2, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_white_resource` VALUES (3, '???????????????????????????', '/op-admin/menu/queryUserTreeList', NULL, 0, 1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_white_resource` VALUES (4, '????????????????????????', '/op-admin/menu/queryUserList', NULL, 0, 1, 4, 0, 0, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
