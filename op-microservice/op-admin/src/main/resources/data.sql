# 组织
INSERT INTO `admin_organization` (`id`,
                    `pid`,
                    `org_name`,
                    `org_code`,
                    `org_code_link`,
                    `org_type`)
values (1, -1, 'X集团', '0001', '0001', 1),
       (2, 1, '一公司', '0001', '0001.0001', 2);

# 用户
INSERT INTO `admin_user` (`id`,
                    `org_id`,
                    `username`,
                    `password`,
                    `status`,
                    `user_no`)
values (1, 1, 'admin', '$2a$10$j9u8iaRZPzu2iPPm4Tp55uQRo8lnhpNOk.x/tl9Cmz0Pd8b7IX322', 1, 1),
       (2, 2, 'guest', '$2a$10$7eKWmT.uG4Qvh5F.QY8p0.DHOMLUZNZYW9YZltzGlFa2.R87Zihwq', 1, 2);

# 角色
INSERT INTO `admin_role` (`id`,
                    `role_name`,
                    `status`,
                    `role_no`)
values (1, 'admin', 1, 1),
       (2, 'guest', 1, 2);

# 用户-角色关联
INSERT INTO `admin_user_role_relation` (`user_id`, `role_id`)
values (1, 1), (2, 2);

# 资源分类
INSERT INTO `admin_resource_category` (`id`,
                    `category_name`,
                    `server_name`,
                    `category_no`)
values (1, '管理中心', 'op-admin', 1),
       (2, '统计中心', 'op-statistics', 2);

# 资源
INSERT INTO `admin_resource` (`id`,
                    `category_id`,
                    `resource_name`,
                    `resource_path`,
                    `resource_no`)
values (1, 1, '组织管理', 'organization', 1),
       (2, 2, '用户管理', 'user', 2);

# 资源动作
INSERT INTO `admin_resource_action` (`id`,
                    `resource_id`,
                    `action_name`,
                    `action_path`,
                    `action_no`,
                    `permission_name`)
values (1, 1, '分页查询', 'page', 1, 'view'),
       (2, 1, '保存', 'list', 2, 'save');

# 用户-资源动作关联
INSERT INTO `admin_user_resource_action_relation` (`user_id`,
                    `action_id`)
values (1, 1),
       (1, 2);

# oauth2-client
INSERT INTO `admin_oauth_client_details` (`id`,
                    `client_id`,
                    `client_secret`,
                    `authorized_grant_types`,
                    `scope`,
                    `web_server_redirect_uri`,
                    `authorities`,
                    `resource_ids`,
                    `access_token_validity`,
                    `refresh_token_validity`,
                    `autoapprove`,
                    `additional_information`)
values (1, 'client', '$2a$10$kf.8Hx8PuBjpewOMnZ9z3uIcQuG5E1988NYNx3gHrEwrVyklphkLS', 'client_credentials', null, null, null, null, '86400', null, null, null),
       (2, 'implicit', '$2a$10$kf.8Hx8PuBjpewOMnZ9z3uIcQuG5E1988NYNx3gHrEwrVyklphkLS', 'implicit', null, 'https://www.baidu.com', null, null, '86400', null, null, null),
       (3, 'password', '$2a$10$kf.8Hx8PuBjpewOMnZ9z3uIcQuG5E1988NYNx3gHrEwrVyklphkLS', 'password,refresh_token', null, null, null, null, '86400', '604800', null, null),
       (4, 'code', '$2a$10$kf.8Hx8PuBjpewOMnZ9z3uIcQuG5E1988NYNx3gHrEwrVyklphkLS', 'authorization_code,refresh_token', null, 'http://localhost:8080/authorized', null, null, '86400', '604800', 'true', null);