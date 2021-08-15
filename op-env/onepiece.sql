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

 Date: 15/08/2021 21:20:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int NOT NULL COMMENT '上级菜单id',
  `menu_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `menu_icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `menu_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `is_show` tinyint(1) NOT NULL COMMENT '是否显示',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `menu_no` int NULL DEFAULT NULL COMMENT '菜单编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_index`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (1, -1, '工作台', 'DesktopOutlined', '/workbench', 1, '', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (2, -1, '管理中心', 'TeamOutlined', '/admin', 1, '', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (3, 2, '组织管理', NULL, '/admin/organization', 1, 'organization_view', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (4, 2, '用户管理', NULL, '/admin/user', 1, 'user_view', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (5, 2, '用户组管理', NULL, '/admin/userGroup', 1, 'user_group_view', 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (6, 2, '角色管理', NULL, '/admin/role', 1, 'role_view', 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (7, 2, '资源分类管理', NULL, '/admin/resourceCategory', 1, 'resource_category_view', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (8, 2, '资源管理', NULL, '/admin/resource', 1, 'resource_view', 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (9, 2, '白名单资源管理', NULL, '/admin/whiteResource', 1, 'white_resource_view', 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (10, 2, '菜单管理', NULL, '/admin/menu', 1, 'menu_view', 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (11, 2, 'OAuth客户端管理', NULL, '/admin/oauthClient', 1, 'oauth_client_view', 9, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (12, -1, '个人中心', '', '/personal/center', 0, '', 98, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_menu` VALUES (13, -1, '个人设置', NULL, '/personal/settings', 0, '', 99, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `admin_oauth_client_details`;
CREATE TABLE `admin_oauth_client_details`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户端标识',
  `client_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端密钥',
  `authorized_grant_types` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权许可类型',
  `scope` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权范围',
  `web_server_redirect_uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限（客户端/隐式模式需要配置）',
  `resource_ids` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源ids',
  `access_token_validity` int NULL DEFAULT NULL COMMENT '访问令牌有效期',
  `refresh_token_validity` int NULL DEFAULT NULL COMMENT '刷新令牌有效期',
  `autoapprove` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否自动授权（只适用于授权码模式，可选值：true|false|read|write）',
  `additional_information` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '其它信息',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id_index`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'oauth2-client' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_oauth_client_details
-- ----------------------------
INSERT INTO `admin_oauth_client_details` VALUES (1, 'client', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'client_credentials,password', NULL, NULL, NULL, NULL, 86400, NULL, NULL, NULL);
INSERT INTO `admin_oauth_client_details` VALUES (2, 'implicit', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'implicit', NULL, 'https://www.baidu.com', NULL, NULL, 86400, NULL, NULL, NULL);
INSERT INTO `admin_oauth_client_details` VALUES (3, 'password', NULL, 'password,refresh_token', NULL, NULL, NULL, NULL, 86400, 604800, NULL, NULL);
INSERT INTO `admin_oauth_client_details` VALUES (4, 'code', '$2a$10$ngadERbCpEPqPCXKf7vbjesqK9p.wVTTs9.X2wmqjdg0LaOlpofAm', 'authorization_code,refresh_token', NULL, 'http://localhost:8080/authorized', NULL, NULL, 86400, 604800, 'true', NULL);

-- ----------------------------
-- Table structure for admin_organization
-- ----------------------------
DROP TABLE IF EXISTS `admin_organization`;
CREATE TABLE `admin_organization`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int NOT NULL COMMENT '上级组织id',
  `org_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组织名称',
  `org_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组织编码',
  `org_code_link` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组织编码链（从根组织到当前组织的编码链，用于快速查询）',
  `org_type` int NOT NULL COMMENT '组织类型（1-集团；2-公司；3-分公司；4-项目部；5-部门）',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid_index`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '组织' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_organization
-- ----------------------------
INSERT INTO `admin_organization` VALUES (1, -1, '保利物业集团', '0001', '0001', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (2, 1, '北京公司', '0001', '0001.0001', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (3, 2, '研发部', '0001', '0001.0001.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (4, 2, '产品部', '0002', '0001.0001.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (5, 2, '销售部', '0003', '0001.0001.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (6, 1, '上海公司', '0002', '0001.0002', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (7, 6, '研发部', '0001', '0001.0002.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (8, 6, '产品部', '0002', '0001.0002.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (9, 6, '质量部', '0003', '0001.0002.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (10, 1, '广州公司', '0003', '0001.0003', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (11, 10, '研发部', '0001', '0001.0003.0001', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (12, 10, '产品部', '0002', '0001.0003.0002', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (13, 10, '质量部', '0003', '0001.0003.0003', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (14, 1, '深圳公司', '0004', '0001.0004', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (15, 14, '研发部', '0001', '0001.0004.0001', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (16, 14, '产品部', '0002', '0001.0004.0002', 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (17, 1, '武汉分公司', '0005', '0001.0005', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (18, 17, '武汉软件新城项目部', '0001', '0001.0005.0001', 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (19, 21, '信息部', '0001', '0001.0005.0001.0003.0001', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (20, 19, '技术部', '0002', '0001.0005.0001.0003.0001.0002', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_organization` VALUES (21, 18, '安全部', '0003', '0001.0005.0001.0003', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);

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
  INDEX `org_id_index`(`org_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
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
  INDEX `org_id_index`(`org_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
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
  `category_id` int NOT NULL COMMENT '资源分类id',
  `resource_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
  `resource_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源路径',
  `resource_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源描述',
  `resource_no` int NULL DEFAULT NULL COMMENT '资源编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id_index`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource
-- ----------------------------
INSERT INTO `admin_resource` VALUES (1, 1, '组织管理', '/organization', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (2, 1, '用户管理', '/user', NULL, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (3, 1, '角色管理', '/role', NULL, 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (4, 1, '资源分类管理', '/resourceCategory', NULL, 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (5, 1, '资源管理', '/resource', NULL, 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (6, 1, '菜单管理', '/menu', NULL, 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (7, 1, '用户组管理', '/userGroup', NULL, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (8, 1, 'OAuth客户端管理', '/oauthClientDetails', NULL, 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource` VALUES (11, 1, '白名单资源管理', '/whiteResource', NULL, 9, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_resource_action
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_action`;
CREATE TABLE `admin_resource_action`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_id` int NOT NULL COMMENT '资源id',
  `action_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '动作名称',
  `action_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '动作路径',
  `action_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '动作描述',
  `action_no` int NULL DEFAULT NULL COMMENT '动作编号',
  `permission` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `resource_id_index`(`resource_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源动作' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource_action
-- ----------------------------
INSERT INTO `admin_resource_action` VALUES (1, 1, '保存', '/save', '', 1, 'organization_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (2, 1, '删除', '/delete,/batchDelete', '', 2, 'organization_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (3, 1, '查看', '/query**,/get**,/find**,/load**', '', 3, 'organization_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (4, 1, '导出', '/export', '', 4, 'organization_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (5, 2, '保存', '/save', NULL, 1, 'user_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (6, 2, '删除', '/delete,/batchDelete', NULL, 2, 'user_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (7, 2, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'user_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (8, 2, '导出', '/export', NULL, 4, 'user_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (9, 3, '保存', '/save', NULL, 1, 'role_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (10, 3, '删除', '/delete,/batchDelete', NULL, 2, 'role_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (11, 3, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'role_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (12, 3, '导出', '/export', NULL, 4, 'role_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (13, 4, '保存', '/save', NULL, 1, 'resource_category_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (14, 4, '删除', '/delete,/batchDelete', NULL, 2, 'resource_category_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (15, 4, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'resource_category_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (16, 4, '导出', '/export', NULL, 4, 'resource_category_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (17, 5, '保存', '/save', NULL, 1, 'resource_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (18, 5, '删除', '/delete,/batchDelete', NULL, 2, 'resource_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (19, 5, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'resource_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (20, 5, '导出', '/export', NULL, 4, 'resource_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (21, 6, '保存', '/save', NULL, 1, 'menu_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (22, 6, '删除', '/delete,/batchDelete', NULL, 2, 'menu_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (23, 6, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'menu_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (24, 6, '导出', '/export', NULL, 4, 'menu_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (25, 1, '授权', '/assignRoles,/assignResourceActions', NULL, 5, 'organization_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (26, 2, '修改密码', '/changePassword', NULL, 5, 'user__change_password', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (27, 2, '启用/禁用用户', '/changeEnabled', NULL, 6, 'user_change_enabled', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (28, 2, '授权', '/assignRoles,/assignResourceActions,/assignOrganizations', NULL, 7, 'user_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (29, 3, '启用/禁用角色', '/changeEnabled', NULL, 5, 'role_change_enabled', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (30, 3, '授权', '/assignResourceActions', NULL, 6, 'role_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (31, 6, '显示/隐藏菜单', '/changeVisibility', NULL, 5, 'menu_change_visibility', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (32, 7, '保存', '/save', NULL, 1, 'user_group_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (33, 7, '删除', '/delete,/batchDelete', NULL, 2, 'user_group_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (34, 7, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'user_group_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (35, 7, '导出', '/export', NULL, 4, 'user_group_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (36, 7, '授权', '/assignRoles,/assignResourceActions', NULL, 5, 'user_group_authorization', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (37, 8, '保存', '/save', NULL, 1, 'oauth_client_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (38, 8, '删除', '/delete,/batchDelete', NULL, 2, 'oauth_client_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (39, 8, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'oauth_client_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (40, 8, '导出', '/export', NULL, 4, 'oauth_client_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (41, 4, '刷新资源权限关联', '/refreshResourcePermissions', NULL, 5, 'resource_permission_refresh', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (42, 11, '保存', '/save', NULL, 1, 'white_resource_save', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (43, 11, '删除', '/delete,/batchDelete', NULL, 2, 'white_resource_delete', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (44, 11, '查看', '/query**,/get**,/find**,/load**', NULL, 3, 'white_resource_view', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (45, 11, '导出', '/export', NULL, 4, 'white_resource_export', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_action` VALUES (46, 11, '启用/禁用白名单资源', '/changeEnabled', NULL, 5, 'white_resource_change_enabled', 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_category`;
CREATE TABLE `admin_resource_category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `category_icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类图标',
  `server_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '服务名称',
  `category_no` int NULL DEFAULT NULL COMMENT '分类编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_resource_category
-- ----------------------------
INSERT INTO `admin_resource_category` VALUES (1, '管理中心', 'TeamOutlined', 'op-admin', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (2, '统计中心', 'AreaChartOutlined', 'op-statistics', 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (3, '日志中心', 'DatabaseOutlined', 'op-logging', 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (4, '监控中心', 'PictureOutlined', 'op-monitor', 4, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (5, '附件中心', 'FileImageOutlined', 'op-attachment', 5, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (6, '文档中心', 'ReadOutlined', 'op-document', 6, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (7, '管理中心2', 'TeamOutlined', 'op-admin2', 7, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (8, '统计中心2', 'AreaChartOutlined', 'op-statistics-2', 8, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (9, '日志中心2', 'DatabaseOutlined', 'op-logging-2', 9, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (10, '监控中心2', 'PictureOutlined', 'op-monitor-2', 10, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (11, '附件中心2', 'FileImageOutlined', 'op-attachment-2', 11, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_resource_category` VALUES (12, '文档中心2', 'ReadOutlined', 'op-document-2', 12, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `role_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` int NOT NULL COMMENT '启用状态（0-禁用；1-启用）',
  `role_no` int NULL DEFAULT NULL COMMENT '角色编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, '管理员', 'admin', '具备所有操作权限', 1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (2, '游客', 'guest', '具备查看权限', 1, 2, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (3, '管理员2', 'admin2', '具备所有操作权限', 1, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (4, '游客2', 'guest2', '具备查看权限', 1, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (5, '管理员3', 'admin3', '具备所有操作权限', 1, 5, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (6, '游客3', 'guest3', '具备查看权限', 1, 6, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (7, '管理员4', 'admin4', '具备所有操作权限', 1, 7, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (8, '游客4', 'guest4', '具备查看权限', 1, 8, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (9, '管理员5', 'admin5', '具备所有操作权限', 1, 9, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (10, '游客5', 'guest5', '具备查看权限', 1, 10, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (11, '管理员6', 'admin6', '具备所有操作权限', 1, 11, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (12, '游客6', 'guest6', '具备查看权限', 1, 12, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (13, '管理员7', 'admin7', '具备所有操作权限', 1, 13, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (14, '游客7', 'guest7', '具备查看权限', 1, 14, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (15, '管理员8', 'admin8', '具备所有操作权限', 1, 15, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (16, '游客8', 'guest8', '具备查看权限', 1, 16, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (17, '管理员9', 'admin9', '具备所有操作权限', 1, 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (18, '游客9', 'guest9', '具备查看权限', 1, 18, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (19, '管理员10', 'admin10', '具备所有操作权限', 1, 19, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (20, '游客10', 'guest10', '具备查看权限', 1, 20, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (21, '管理员11', 'admin11', '具备所有操作权限', 1, 21, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (22, '游客11', 'guest11', '具备查看权限', 1, 22, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (23, '管理员12', 'admin12', '具备所有操作权限', 1, 23, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_role` VALUES (24, '游客12', 'guest12', '具备查看权限', 1, 24, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

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
  INDEX `role_id_index`(`role_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色-资源动作关联' ROW_FORMAT = DYNAMIC;

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
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_id` int NOT NULL COMMENT '组织id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `signature` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` int NULL DEFAULT NULL COMMENT '性别（1-男；2-女）',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `status` int NOT NULL COMMENT '帐号状态（0-禁用；1-启用；2-过期；3-锁定；4-密码过期）',
  `user_no` int NOT NULL DEFAULT 999 COMMENT '用户编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_index`(`username`) USING BTREE,
  INDEX `org_id_index`(`org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 1, 'admin', '$2a$10$Nyv6JowsaRTBpU405amyLu0Un6FalfYzbhmS5AqTlRzZ949wiS5a2', '管理员', NULL, NULL, '13669064460', '13669064460@139.com', 1, '1989-09-15', 1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (2, 1, 'guest', '$2a$10$3JSddK51ItcFc31MLfk42OLSYkEw4PD1Y1tNSX3Xzr1d8O7TkI96a', '游客', NULL, NULL, NULL, NULL, NULL, NULL, 1, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (3, 2, 'lemon', '$2a$10$pT1QtdGPnICYMT3Vy.R8jOcvDMbwOfc7aVmGBVjdJapcsYHS3BMJq', '李萌', NULL, NULL, '13620600060', '13620600060@139.com', 2, '1989-04-22', 1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_group
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_group`;
CREATE TABLE `admin_user_group`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户组名称',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group
-- ----------------------------
INSERT INTO `admin_user_group` VALUES (1, '纪委领导班子', '', 1, 0, 0, NULL, NULL, NULL, NULL, NULL);

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
  INDEX `group_id_index`(`group_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
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
  INDEX `group_id_index`(`group_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
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
  INDEX `group_id_index`(`group_id`) USING BTREE,
  INDEX `user_id_index`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户组-用户关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_group_user_relation
-- ----------------------------
INSERT INTO `admin_user_group_user_relation` VALUES (1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for admin_user_organization_relation
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_organization_relation`;
CREATE TABLE `admin_user_organization_relation`  (
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `org_id` int NULL DEFAULT NULL COMMENT '组织id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `org_id_index`(`org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-授权组织关联' ROW_FORMAT = DYNAMIC;

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
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `action_id` int NULL DEFAULT NULL COMMENT '资源动作id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `action_id_index`(`action_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-资源动作关联' ROW_FORMAT = DYNAMIC;

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
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  INDEX `user_id_index`(`user_id`) USING BTREE,
  INDEX `role_id_index`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-角色关联' ROW_FORMAT = DYNAMIC;

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
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名称',
  `resource_path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源路径',
  `resource_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源描述',
  `remove_authorization` tinyint(1) NOT NULL COMMENT '是否移除身份认证信息',
  `status` int NOT NULL COMMENT '启用状态（0-禁用；1-启用）',
  `resource_no` int NULL DEFAULT NULL COMMENT '资源编号',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否逻辑删除',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_modifier_id` int NULL DEFAULT NULL COMMENT '最后修改人id',
  `last_modify_time` datetime NULL DEFAULT NULL COMMENT '最后修改时间',
  `tenant_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '白名单资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_white_resource
-- ----------------------------
INSERT INTO `admin_white_resource` VALUES (1, '获取访问令牌', '/op-auth/auth/getToken', NULL, 1, 1, 1, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_white_resource` VALUES (2, '刷新访问令牌', '/op-auth/auth/refreshToken', NULL, 1, 1, 2, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_white_resource` VALUES (3, '查询用户菜单树列表', '/op-admin/menu/queryUserTreeList', NULL, 0, 1, 3, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `admin_white_resource` VALUES (4, '查询用户菜单列表', '/op-admin/menu/queryUserList', NULL, 0, 1, 4, 0, 0, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
