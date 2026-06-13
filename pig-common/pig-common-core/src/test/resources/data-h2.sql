-- H2 测试数据库初始化数据
-- 提供测试所需的基础数据

-- 部门数据
INSERT INTO sys_dept (dept_id, name, sort_order, create_by, update_by, create_time, update_time, del_flag, parent_id) VALUES
(1, '总裁办', 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 0),
(2, '技术部', 2, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 1),
(3, '市场部', 3, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 1),
(4, '销售部', 4, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 1),
(5, '财务部', 5, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', 1);

-- 字典类型数据
INSERT INTO sys_dict (id, dict_type, description, create_by, update_by, create_time, update_time, system_flag, del_flag) VALUES
(1, 'log_type', '日志类型', ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', '0'),
(12, 'dict_type', '字典类型', ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', '0'),
(14, 'grant_types', '授权类型', ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', '0'),
(18, 'lock_flag', '用户状态', 'admin', ' ', CURRENT_TIMESTAMP, NULL, '1', '0');

-- 字典项数据
INSERT INTO sys_dict_item (id, dict_id, item_value, label, dict_type, sort_order, create_by, update_by, create_time, update_time, del_flag) VALUES
(35, 12, '1', '系统类', 'dict_type', 0, ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(36, 12, '0', '业务类', 'dict_type', 0, ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(39, 14, 'password', '密码模式', 'grant_types', 0, ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(40, 14, 'authorization_code', '授权码模式', 'grant_types', 1, ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(41, 14, 'client_credentials', '客户端模式', 'grant_types', 2, ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(42, 14, 'refresh_token', '刷新模式', 'grant_types', 3, ' ', ' ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(57, 14, 'mobile', '移动端登录', 'grant_types', 5, 'admin', ' ', CURRENT_TIMESTAMP, NULL, '0'),
(58, 18, '0', '有效', 'lock_flag', 0, 'admin', ' ', CURRENT_TIMESTAMP, NULL, '0'),
(59, 18, '9', '禁用', 'lock_flag', 1, 'admin', ' ', CURRENT_TIMESTAMP, NULL, '0');

-- 菜单数据（基础菜单）
INSERT INTO sys_menu (menu_id, name, permission, path, component, parent_id, icon, visible, sort_order, keep_alive, menu_type, create_by, create_time, update_by, update_time, del_flag) VALUES
(1000, '权限管理', NULL, '/system', NULL, 2000, 'icon-quanxianguanli', '1', 0, '0', '0', '', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '0'),
(1100, '用户管理', NULL, '/admin/system/user/index', NULL, 1000, 'icon-yonghuguanli', '1', 1, '0', '0', '', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '0'),
(1200, '菜单管理', NULL, '/admin/system/menu/index', NULL, 1000, 'icon-caidan', '1', 2, '0', '0', '', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '0'),
(1300, '角色管理', NULL, '/admin/system/role/index', NULL, 1000, 'icon-jiaoseguanli', '1', 3, '0', NULL, '0', '', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '0'),
(1400, '部门管理', NULL, '/admin/system/dept/index', NULL, 1000, 'icon-bumenguanli', '1', 4, '0', NULL, '0', '', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '0'),
(2000, '系统管理', NULL, '/admin', NULL, -1, 'icon-xitongguanli', '1', 1, '0', NULL, '0', '', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '0');

-- OAuth 客户端数据
INSERT INTO sys_oauth_client_details (id, client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity, additional_information, autoapprove, del_flag, create_by, create_time) VALUES
(1, 'app', '{bcrypt}$2a$10$DjXTOWSLYeRLx9rBQvMLM.8.y7pX6r5X6.8X5X5X5X5X5X5X5X5X5', 'server', 'password,refresh_token,authorization_code,client_credentials,mobile', 43200, 2592001, '{"enc_flag":"1","captcha_flag":"1","online_quantity":"1"}', 'true', '0', '', CURRENT_TIMESTAMP),
(2, 'daemon', '{bcrypt}$2a$10$DjXTOWSLYeRLx9rBQvMLM.8.y7pX6r5X6.8X5X5X5X5X5X5X5X5X5', 'server', 'password,refresh_token', 43200, 2592001, '{"enc_flag":"1","captcha_flag":"1"}', 'true', '0', ' ', ' ', CURRENT_TIMESTAMP),
(5, 'pig', '{bcrypt}$2a$10$DjXTOWSLYeRLx9rBQvMLM.8.y7pX6r5X6.8X5X5X5X5X5X5X5X5X5', 'server', 'password,refresh_token,authorization_code,client_credentials,mobile', 43200, 2592001, '{"enc_flag":"1","captcha_flag":"1","online_quantity":"1"}', 'false', '0', '', CURRENT_TIMESTAMP);

-- 岗位数据
INSERT INTO sys_post (post_id, post_code, post_name, post_sort, remark, del_flag, create_time, create_by) VALUES
(1, 'TEAM_LEADER', '部门负责人', 0, 'LEADER', '0', CURRENT_TIMESTAMP, '');

-- 公共参数数据
INSERT INTO sys_public_param (public_id, public_name, public_key, public_value, status, create_by, update_by, create_time, public_type, system_flag, del_flag) VALUES
(10, 'Clarity 追踪 ID', 'SITE_CLARITY_ID', 'r2uywff1r4', '0', 'admin', 'admin', CURRENT_TIMESTAMP, '2', '1', '0'),
(11, '验证码类型', 'SITE_CAPTCHA_TYPE', 'clickWord', '0', 'admin', 'admin', CURRENT_TIMESTAMP, '2', '1', '0'),
(12, '强制重置密码', 'SITE_FORCE_RESET_PWD', '0', '0', 'admin', 'admin', CURRENT_TIMESTAMP, '2', '1', '0');

-- 角色数据
INSERT INTO sys_role (role_id, role_name, role_code, role_desc, create_by, update_by, create_time, update_time, del_flag) VALUES
(1, '管理员', 'ROLE_ADMIN', '管理员', '', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0'),
(2, '普通用户', 'GENERAL_USER', '普通用户', '', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0');

-- 角色菜单关联数据（管理员角色拥有所有菜单权限）
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(1, 1000),
(1, 1100),
(1, 1200),
(1, 1300),
(1, 1400),
(1, 2000);

-- 用户数据（密码为 lengleng 的 BCrypt 加密值）
INSERT INTO sys_user (user_id, username, password, salt, phone, avatar, nickname, name, email, create_by, update_by, create_time, update_time, lock_flag, password_expire_flag, password_modify_time, del_flag, dept_id) VALUES
(1, 'admin', '$2a$10$c/Ae0pRjJtMZg3BnvVpO.eIK6WYWVbKTzqgdy3afR7w.vd.xi3Mgy', '', '17338122125', '/admin/sys-file/local/avatar.png', '管理员', '管理员', 'admin@pig4cloud.com', ' ', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '0', '0', CURRENT_TIMESTAMP, '0', 1);

-- 用户岗位关联
INSERT INTO sys_user_post (user_id, post_id) VALUES (1, 1);

-- 用户角色关联
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- 用户部门关联
INSERT INTO sys_user_dept (user_id, dept_id) VALUES (1, 1);

-- 租户数据
INSERT INTO sys_tenant (id, code, name, domain, create_time, update_time, create_by, update_by, del_flag) VALUES
(1, 'test_tenant', '测试租户', 'test.example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin', 'admin', '0');