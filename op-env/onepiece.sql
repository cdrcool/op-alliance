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

 Date: 09/07/2021 18:05:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int NULL DEFAULT NULL COMMENT '上级菜单id',
  `parent_ids` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上级菜单ids',
  `menu_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menu_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `menu_route` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单路由',
  `menu_level` int NULL DEFAULT NULL COMMENT '菜单层级',
  `menu_no` int NULL DEFAULT NULL COMMENT '菜单编号',
  `is_directory` tinyint(1) NULL DEFAULT NULL COMMENT '是否目录',
  `is_hidden` tinyint(1) NULL DEFAULT NULL COMMENT '是否隐藏',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------

-- ----------------------------
-- Table structure for admin_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `admin_oauth_client_details`;
CREATE TABLE `admin_oauth_client_details`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端标识',
  `client_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端密钥',
  `authorized_grant_types` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权许可类型',
  `scope` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权范围',
  `web_server_redirect_uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限（客户端/隐式模式需要配置）',
  `resource_ids` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源ids',
  `access_token_validity` int NULL DEFAULT NULL COMMENT '访问令牌有效期',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT '刷新令牌有效期',
  `autoapprove` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否自动授权（只适用于授权码模式，可选值：true|false|read|write）',
  `additional_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '其它信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'oauth2-client' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_oauth_client_details
-- ----------------------------

-- ----------------------------
-- Table structure for admin_organization
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization`;
CREATE TABLE `admin_organization`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int NULL DEFAULT NULL COMMENT '上级组织id',
  `org_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织名称',
  `org_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织编码',
  `org_code_link` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组织编码链（从根组织到当前组织的编码链，用于快速查询）',
  `org_type` int NULL DEFAULT NULL COMMENT '组织类型（1-集团；2-公司；3-分公司；4-项目部；5-部门）',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization
-- ----------------------------
INSERT INTO `admin_organization` VALUES (1, -1, '保利物业集团', '0001', '0001', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (2, 1, '武汉公司', '0001', '0001.0001', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (3, 2, '研发部', '0001', '0001.0001.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (4, 2, '生产部', '0001', '0001.0001.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (5, 2, '销售部', '0001', '0001.0001.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (6, 1, '广州公司', '0002', '0001.0002', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (7, 6, '研发部', '0001', '0001.0002.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (8, 6, '生产部', '0002', '0001.0002.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (9, 6, '销售部', '0003', '0001.0002.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (10, 1, '江苏公司', '0003', '0001.0003', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (11, 10, '研发部', '0001', '0001.0003.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (12, 10, '生产部', '0002', '0001.0003.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (13, 10, '销售部', '0003', '0001.0003.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (14, 1, '成都公司', '0004', '0001.0004', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (15, 14, '研发部', '0001', '0001.0004.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (16, 14, '生产部', '0002', '0001.0004.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (17, 1, '南京公司', '0005', '0001.0005', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (18, 17, '研发部', '0001', '0001.0005.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (19, 17, '生产部', '0002', '0001.0005.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (20, 17, '销售部', '0003', '0001.0005.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_organization_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization_menu_relation`;
CREATE TABLE `admin_organization_menu_relation`  (
  `org_id` int NULL DEFAULT NULL COMMENT '组织id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `org_id_menu_id_index`(`org_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织-菜单关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization_menu_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_organization_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization_resource_action_relation`;
CREATE TABLE `admin_organization_resource_action_relation`  (
  `org_id` int NULL DEFAULT NULL COMMENT '组织id',
  `action_id` int NULL DEFAULT NULL COMMENT '资源动作id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `org_id_action_id_index`(`org_id`, `action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织-资源动作关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization_resource_action_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_organization_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization_role_relation`;
CREATE TABLE `admin_organization_role_relation`  (
  `org_id` int NULL DEFAULT NULL COMMENT '组织id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `org_id_role_id_index`(`org_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织-角色关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization_role_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_resource
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource`;
CREATE TABLE `admin_resource`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` int NULL DEFAULT NULL COMMENT '资源分类id',
  `resource_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源名称',
  `resource_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源路径',
  `resource_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源描述',
  `resource_no` int NULL DEFAULT NULL COMMENT '资源编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource
-- ----------------------------

-- ----------------------------
-- Table structure for admin_resource_action
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_action`;
CREATE TABLE `admin_resource_action`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_id` int NULL DEFAULT NULL COMMENT '资源id',
  `action_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '动作名称',
  `action_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '动作路径',
  `action_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '动作描述',
  `action_no` int NULL DEFAULT NULL COMMENT '动作编号',
  `permission_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限名',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源动作' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource_action
-- ----------------------------

-- ----------------------------
-- Table structure for admin_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_category`;
CREATE TABLE `admin_resource_category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `server_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务名称',
  `category_no` int NULL DEFAULT NULL COMMENT '分类编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource_category
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色编码',
  `role_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` int NULL DEFAULT NULL COMMENT '启用状态（0-禁用；1-启用）',
  `role_no` int NULL DEFAULT NULL COMMENT '角色编号',
  `user_count` int NULL DEFAULT NULL COMMENT '分配了该角色的用户数量',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (2, '33', '33', '33', 1, 11, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (3, '332', '42', '4', 1, 6, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (5, '2', '333', NULL, 1, 2, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (6, '333', '444', NULL, 1, 3, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (8, '66', '77', NULL, 1, 4, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (9, '222', '222', '222', 1, 4, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu_relation`;
CREATE TABLE `admin_role_menu_relation`  (
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `role_id_menu_id_index`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-菜单关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_menu_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_resource_action_relation`;
CREATE TABLE `admin_role_resource_action_relation`  (
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `action_id` int NULL DEFAULT NULL COMMENT '资源动作id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `role_id_action_id_index`(`role_id`, `action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-资源动作关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_resource_action_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_id` int NULL DEFAULT NULL COMMENT '组织id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `signature` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` int NULL DEFAULT NULL COMMENT '性别（1-男；2-女）',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `status` int NULL DEFAULT NULL COMMENT '帐号状态（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）',
  `user_no` int NULL DEFAULT NULL COMMENT '用户编号',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 1, 'admin', 'admin', '管理员', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (2, 2, 'guest', 'guest', '游客', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (3, 3, 'lemon', 'lemon', '李萌', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_group
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group`;
CREATE TABLE `admin_user_group`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户组名称',
  `group_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户组描述',
  `group_no` int NULL DEFAULT NULL COMMENT '用户编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_group_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_menu_relation`;
CREATE TABLE `admin_user_group_menu_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '用户组id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `group_id_menu_id_index`(`group_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组-菜单关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_menu_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_group_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_resource_action_relation`;
CREATE TABLE `admin_user_group_resource_action_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '用户组id',
  `action_id` int NULL DEFAULT NULL COMMENT '资源动作id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `group_id_action_id_index`(`group_id`, `action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组-资源动作关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_resource_action_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_group_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_role_relation`;
CREATE TABLE `admin_user_group_role_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '用户组id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `group_id_role_id_index`(`group_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组-角色关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_role_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_group_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group_user_relation`;
CREATE TABLE `admin_user_group_user_relation`  (
  `group_id` int NULL DEFAULT NULL COMMENT '用户组id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `group_id_user_id_index`(`group_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组-用户关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_user_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_menu_relation`;
CREATE TABLE `admin_user_menu_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `user_id_menu_id_index`(`user_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-菜单关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_menu_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_resource_action_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_resource_action_relation`;
CREATE TABLE `admin_user_resource_action_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `action_id` int NULL DEFAULT NULL COMMENT '资源动作id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `user_id_action_id_index`(`user_id`, `action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-资源动作关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_resource_action_relation
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role_relation`;
CREATE TABLE `admin_user_role_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  UNIQUE INDEX `user_id_role_id_index`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_role_relation
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;