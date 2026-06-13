-- 性能优化索引建议
-- 根据代码中的查询模式，建议添加以下索引

-- ===================
-- 用户表相关索引
-- ===================

-- 用户名模糊查询索引（前端搜索优化）
CREATE INDEX idx_sys_user_username ON sys_user(username);

-- 手机号查询索引
CREATE INDEX idx_sys_user_phone ON sys_user(phone);

-- 部门ID查询索引（树形结构查询优化）
CREATE INDEX idx_sys_user_dept_id ON sys_user(dept_id);

-- ===================
-- 部门表相关索引
-- ===================

-- 部门名称模糊查询索引
CREATE INDEX idx_sys_dept_name ON sys_dept(name);

-- 父级部门ID索引（树形结构查询优化）
CREATE INDEX idx_sys_dept_parent_id ON sys_dept(parent_id);

-- ===================
-- 角色表相关索引
-- ===================

-- 角色编码唯一索引
CREATE UNIQUE INDEX idx_sys_role_role_code ON sys_role(role_code);

-- ===================
-- 菜单表相关索引
-- ===================

-- 菜单类型索引
CREATE INDEX idx_sys_menu_type ON sys_menu(type);

-- 父级菜单ID索引
CREATE INDEX idx_sys_menu_parent_id ON sys_menu(parent_id);

-- ===================
-- 日志表相关索引
-- ===================

-- 创建时间索引（日志查询优化）
CREATE INDEX idx_sys_log_create_time ON sys_log(create_time);

-- 操作类型索引
CREATE INDEX idx_sys_log_type ON sys_log(type);

-- ===================
-- 公共参数表相关索引
-- ===================

-- 参数键名索引
CREATE INDEX idx_sys_public_param_key ON sys_public_param(public_key);

-- 参数类型索引
CREATE INDEX idx_sys_public_param_type ON sys_public_param(system_flag);

-- ===================
-- 消息表相关索引
-- ===================

-- 发送状态索引
CREATE INDEX idx_sys_message_send_flag ON sys_message(send_flag);

-- 分类索引
CREATE INDEX idx_sys_message_category ON sys_message(category);

-- ===================
-- 敏感词表相关索引
-- ===================

-- 敏感词索引
CREATE INDEX idx_sys_sensitive_word_word ON sys_sensitive_word(word);

-- ===================
-- 定时任务表相关索引
-- ===================

-- 任务状态索引
CREATE INDEX idx_sys_job_status ON sys_job(job_status);

-- 任务执行状态索引
CREATE INDEX idx_sys_job_log_status ON sys_job_log(job_log_status);

-- ===================
-- 文件表相关索引
-- ===================

-- 文件名索引
CREATE INDEX idx_sys_file_name ON sys_file(file_name);

-- 文件类型索引
CREATE INDEX idx_sys_file_type ON sys_file(type);

-- ===================
-- 支付表相关索引
-- ===================

-- 订单号索引
CREATE INDEX idx_pay_order_order_no ON pay_order(order_no);

-- 支付状态索引
CREATE INDEX idx_pay_order_status ON pay_order(status);

-- 创建时间索引
CREATE INDEX idx_pay_order_create_time ON pay_order(create_time);

-- ===================
-- 三方登录表相关索引
-- ===================

-- 类型索引
CREATE INDEX idx_sys_social_details_type ON sys_social_details(type);

-- ===================
-- 复合索引建议（高频联合查询）
-- ===================

-- 用户部门联合查询索引
CREATE INDEX idx_sys_user_dept_username ON sys_user(dept_id, username);

-- 日志用户时间联合索引
CREATE INDEX idx_sys_log_user_time ON sys_log(create_by, create_time);

-- ===================
-- 注意事项
-- ===================
-- 1. 生产环境添加索引建议在低峰期执行
-- 2. 大表添加索引可能需要较长时间，建议使用 pt-online-schema-change 工具
-- 3. 索引会占用额外存储空间，需要评估收益
-- 4. 定期检查索引使用情况，删除未使用的索引
