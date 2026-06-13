-- H2 测试数据库 Schema
-- 用于单元测试和集成测试

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    name VARCHAR(50) COMMENT '部门名称',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '修改时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志',
    parent_id BIGINT COMMENT '父级部门ID',
    tenant_id BIGINT COMMENT '租户ID',
    PRIMARY KEY (dept_id)
);

-- 字典表
CREATE TABLE IF NOT EXISTS sys_dict (
    id BIGINT NOT NULL COMMENT '编号',
    dict_type VARCHAR(100) COMMENT '字典类型',
    description VARCHAR(100) COMMENT '描述',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '更新时间',
    remarks VARCHAR(255) COMMENT '备注信息',
    system_flag CHAR(1) DEFAULT '0' COMMENT '系统标志',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志',
    PRIMARY KEY (id)
);

-- 字典项表
CREATE TABLE IF NOT EXISTS sys_dict_item (
    id BIGINT NOT NULL COMMENT '编号',
    dict_id BIGINT NOT NULL COMMENT '字典ID',
    item_value VARCHAR(100) COMMENT '字典项值',
    label VARCHAR(100) COMMENT '字典项名称',
    dict_type VARCHAR(100) COMMENT '字典类型',
    description VARCHAR(100) COMMENT '字典项描述',
    list_class VARCHAR(50) COMMENT '标签类型',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '更新时间',
    remarks VARCHAR(255) COMMENT '备注信息',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志',
    tenant_id BIGINT COMMENT '租户ID',
    PRIMARY KEY (id)
);

-- 菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    name VARCHAR(32) COMMENT '菜单名称',
    permission VARCHAR(128) COMMENT '权限标识',
    path VARCHAR(128) COMMENT '路由路径',
    component VARCHAR(255) COMMENT '组件',
    parent_id BIGINT COMMENT '父菜单ID',
    icon VARCHAR(64) COMMENT '菜单图标',
    visible CHAR(1) DEFAULT '1' COMMENT '是否可见',
    sort_order INT DEFAULT 1 COMMENT '排序值',
    keep_alive CHAR(1) DEFAULT '0' COMMENT '是否缓存',
    embedded CHAR(1) COMMENT '是否内嵌',
    menu_type CHAR(1) DEFAULT '0' COMMENT '菜单类型',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    update_time TIMESTAMP COMMENT '更新时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标志',
    tenant_id BIGINT COMMENT '租户ID',
    PRIMARY KEY (menu_id)
);

-- OAuth 客户端表
CREATE TABLE IF NOT EXISTS sys_oauth_client_details (
    id BIGINT NOT NULL COMMENT 'ID',
    client_id VARCHAR(32) NOT NULL COMMENT '客户端ID',
    resource_ids VARCHAR(256) COMMENT '资源ID集合',
    client_secret VARCHAR(256) COMMENT '客户端秘钥',
    scope VARCHAR(256) COMMENT '授权范围',
    authorized_grant_types VARCHAR(256) COMMENT '授权类型',
    web_server_redirect_uri VARCHAR(256) COMMENT '回调地址',
    authorities VARCHAR(256) COMMENT '权限集合',
    access_token_validity INT COMMENT '访问令牌有效期',
    refresh_token_validity INT COMMENT '刷新令牌有效期',
    additional_information VARCHAR(4096) COMMENT '附加信息',
    autoapprove VARCHAR(256) COMMENT '自动授权',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
);

-- 岗位表
CREATE TABLE IF NOT EXISTS sys_post (
    post_id BIGINT NOT NULL COMMENT '岗位ID',
    post_code VARCHAR(64) NOT NULL COMMENT '岗位编码',
    post_name VARCHAR(50) NOT NULL COMMENT '岗位名称',
    post_sort INT NOT NULL COMMENT '岗位排序',
    remark VARCHAR(500) COMMENT '岗位描述',
    del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    create_time TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(64) DEFAULT '' COMMENT '创建人',
    update_time TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(64) DEFAULT '' COMMENT '更新人',
    PRIMARY KEY (post_id)
);

-- 公共参数表
CREATE TABLE IF NOT EXISTS sys_public_param (
    public_id BIGINT NOT NULL COMMENT '编号',
    public_name VARCHAR(128) COMMENT '名称',
    public_key VARCHAR(128) COMMENT '键',
    public_value VARCHAR(128) COMMENT '值',
    status CHAR(1) DEFAULT '0' COMMENT '状态',
    validate_code VARCHAR(64) COMMENT '校验码',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '更新时间',
    public_type CHAR(1) DEFAULT '0' COMMENT '类型',
    system_flag CHAR(1) DEFAULT '0' COMMENT '系统标识',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (public_id)
);

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    role_name VARCHAR(64) COMMENT '角色名称',
    role_code VARCHAR(64) COMMENT '角色编码',
    role_desc VARCHAR(255) COMMENT '角色描述',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '更新时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    tenant_id BIGINT COMMENT '租户ID',
    PRIMARY KEY (role_id)
);

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
);

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    username VARCHAR(64) COMMENT '用户名',
    password VARCHAR(255) COMMENT '密码',
    salt VARCHAR(255) COMMENT '盐值',
    phone VARCHAR(20) COMMENT '电话号码',
    avatar VARCHAR(255) COMMENT '头像',
    nickname VARCHAR(64) COMMENT '昵称',
    name VARCHAR(64) COMMENT '姓名',
    email VARCHAR(128) COMMENT '邮箱地址',
    create_by VARCHAR(64) DEFAULT ' ' COMMENT '创建人',
    update_by VARCHAR(64) DEFAULT ' ' COMMENT '修改人',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '修改时间',
    lock_flag CHAR(1) DEFAULT '0' COMMENT '锁定标记',
    password_expire_flag CHAR(1) DEFAULT '0' COMMENT '密码是否过期',
    password_modify_time TIMESTAMP COMMENT '密码修改时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    wx_openid VARCHAR(32) COMMENT '微信openId',
    mini_openid VARCHAR(32) COMMENT '小程序openId',
    qq_openid VARCHAR(32) COMMENT 'QQ openId',
    gitee_login VARCHAR(100) COMMENT '码云标识',
    osc_id VARCHAR(100) COMMENT '开源中国标识',
    wx_cp_userid VARCHAR(100) COMMENT '企业微信标识',
    wx_ding_userid VARCHAR(100) COMMENT '钉钉标识',
    dept_id BIGINT COMMENT '主部门ID',
    tenant_id BIGINT COMMENT '租户ID',
    PRIMARY KEY (user_id)
);

-- 用户岗位关联表
CREATE TABLE IF NOT EXISTS sys_user_post (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    post_id BIGINT NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (user_id, post_id)
);

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
);

-- 用户部门关联表
CREATE TABLE IF NOT EXISTS sys_user_dept (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    dept_id BIGINT NOT NULL COMMENT '部门ID',
    PRIMARY KEY (user_id, dept_id)
);

-- 租户表
CREATE TABLE IF NOT EXISTS sys_tenant (
    id BIGINT NOT NULL COMMENT 'ID',
    code VARCHAR(50) NOT NULL COMMENT '租户编码',
    name VARCHAR(255) COMMENT '租户名称',
    domain VARCHAR(255) COMMENT '租户域名',
    create_time TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(64) COMMENT '创建人',
    update_by VARCHAR(64) COMMENT '更新人',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (id)
);

-- 系统日志表
CREATE TABLE IF NOT EXISTS sys_log (
    id BIGINT NOT NULL COMMENT 'ID',
    type CHAR(1) COMMENT '日志类型',
    title VARCHAR(255) COMMENT '日志标题',
    service_id VARCHAR(64) COMMENT '服务ID',
    remote_addr VARCHAR(255) COMMENT '操作IP地址',
    user_agent VARCHAR(1000) COMMENT '用户代理',
    request_uri VARCHAR(255) COMMENT '请求URI',
    method VARCHAR(10) COMMENT '操作方式',
    params TEXT COMMENT '操作提交的数据',
    time VARCHAR(30) COMMENT '执行时间',
    exception TEXT COMMENT '异常信息',
    create_by VARCHAR(64) COMMENT '创建人',
    create_time TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time TIMESTAMP COMMENT '更新时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (id)
);

-- 审计日志表
CREATE TABLE IF NOT EXISTS sys_audit_log (
    id BIGINT NOT NULL COMMENT '主键',
    audit_name VARCHAR(255) NOT NULL COMMENT '审计名称',
    audit_field VARCHAR(255) NOT NULL COMMENT '字段名称',
    before_val VARCHAR(255) COMMENT '变更前值',
    after_val VARCHAR(255) COMMENT '变更后值',
    create_by VARCHAR(64) COMMENT '操作人',
    create_time TIMESTAMP COMMENT '操作时间',
    del_flag CHAR(1) NOT NULL COMMENT '删除标记',
    PRIMARY KEY (id)
);

-- 系统配置表
CREATE TABLE IF NOT EXISTS sys_system_config (
    id BIGINT NOT NULL COMMENT '主键',
    config_type VARCHAR(64) COMMENT '配置类型',
    config_name VARCHAR(255) COMMENT '配置名称',
    config_key VARCHAR(255) COMMENT '配置标识',
    config_value CLOB COMMENT '配置值',
    config_status CHAR(1) COMMENT '开启状态',
    create_by VARCHAR(64) COMMENT '创建人',
    create_time TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '修改人',
    update_time TIMESTAMP COMMENT '更新时间',
    del_flag CHAR(1) DEFAULT '0' COMMENT '删除标记',
    PRIMARY KEY (id)
);