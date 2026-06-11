# Pig 微服务平台模块扩展设计方案

> 创建日期：2026-06-11
> 状态：待实施
> 负责人：架构团队

## 1. 项目概述

### 1.1 背景

Pig 是一个基于 Spring Cloud 的微服务平台，当前已具备用户权限管理、服务网关、认证授权、代码生成、定时任务、监控等核心能力。为满足企业级应用场景，需扩展多租户、数据权限、动态路由、流程引擎、支付、公众号、报表、移动端服务等 8 个功能模块。

### 1.2 目标

- 构建完整的企业级微服务平台
- 支持 SaaS 多租户模式
- 提供关键业务能力（流程、支付、报表）
- 覆盖移动端全场景接入

### 1.3 当前架构

| 组件 | 技术栈 | 版本 |
|------|--------|------|
| 基础框架 | Spring Boot | 4.0.6 |
| 微服务 | Spring Cloud | 2025.1.1 |
| 服务治理 | Spring Cloud Alibaba | 2025.1.0.0 |
| 注册/配置中心 | Nacos | 3.1.2 |
| 网关 | Spring Cloud Gateway | - |
| 认证授权 | Spring Authorization Server | - |
| ORM | MyBatis Plus | 3.5.16 |
| 数据源 | Dynamic Datasource | 4.5.0 |

### 1.4 模块规划总览

| 里程碑 | 模块 | 核心方案 | 新增服务/模块 |
|--------|------|---------|--------------|
| M1 | 多租户 | 共享数据库 + tenant_id | pig-common-tenant |
| M1 | 数据权限 | 部门级数据范围 | pig-common-datapermission |
| M2 | 动态路由 | Nacos 全局 + 数据库租户路由 | pig-gateway 扩展 |
| M2 | 流程引擎 | Flowable + Web 设计器 | pig-process |
| M2 | 报表 | JimuReport 积木报表 | pig-report |
| M3 | 支付 | 微信/支付宝/银联 + 分账 | pig-pay |
| M3 | 公众号+小程序 | 公众号管理 + 小程序 + 用户统一 | pig-wechat |
| M3 | 移动端 | 全端 + 推送 + SDK | pig-mobile |

---

## 2. M1 里程碑：基础设施层

### 2.1 多租户模块

#### 2.1.1 方案选型

**采用共享数据库 + 租户字段模式（模式 B）**

选择理由：
- 与现有 MyBatis Plus 架构高度契合，租户插件可直接使用
- 兼容 Pig 支持的所有数据库（MySQL、Oracle、SQL Server、DM8、HighGo）
- 运维成本低，适合中小规模租户（<1000）
- 具备扩展性，未来可演进为混合模式

#### 2.1.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 租户上下文传递 | 扩展 Security Context，携带租户 ID |
| SQL 自动注入 | MyBatis Plus TenantLineInnerInterceptor |
| 租户识别 | Token 中携带租户标识 / 域名绑定 / 请求头 |
| 跨租户隔离 | 禁止跨租户查询，例外表配置 |
| 租户管理 | 独立管理后台，超级管理员可跨租户操作 |

#### 2.1.3 数据库设计

```sql
-- 租户表
CREATE TABLE sys_tenant (
    id BIGINT PRIMARY KEY,
    tenant_code VARCHAR(64) NOT NULL UNIQUE COMMENT '租户编码',
    tenant_name VARCHAR(128) NOT NULL COMMENT '租户名称',
    domain VARCHAR(128) COMMENT '绑定域名',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    expire_time DATETIME COMMENT '过期时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    del_flag TINYINT DEFAULT 0 COMMENT '删除标志'
);

-- 所有业务表添加 tenant_id 字段
ALTER TABLE sys_user ADD COLUMN tenant_id BIGINT DEFAULT 0 COMMENT '租户ID';
ALTER TABLE sys_role ADD COLUMN tenant_id BIGINT DEFAULT 0;
ALTER TABLE sys_menu ADD COLUMN tenant_id BIGINT DEFAULT 0;
-- ... 其他业务表
```

#### 2.1.4 模块结构

```
pig-common-tenant/
├── src/main/java/com/pig4cloud/pig/common/tenant/
│   ├── annotation/
│   │   └── TenantIgnore.java          # 跳过租户拦截注解
│   ├── aop/
│   │   └── TenantAspect.java          # 租户切面
│   ├── config/
│   │   └── TenantAutoConfiguration.java
│   ├── context/
│   │   └── TenantContextHolder.java   # 租户上下文
│   ├── interceptor/
│   │   └── TenantLineInnerInterceptor.java
│   └── properties/
│       └── TenantProperties.java      # 租户配置属性
└── pom.xml
```

#### 2.1.5 租户识别策略

支持三种方式，优先级从高到低：

1. **Token 携带**：用户登录后 Token 中包含 tenant_id
2. **域名绑定**：通过请求域名映射租户（如 tenant1.example.com）
3. **请求头**：`X-Tenant-Id` 请求头（用于 API 调用）

#### 2.1.6 例外表配置

以下表不进行租户隔离：

```yaml
pig:
  tenant:
    ignore-tables:
      - sys_tenant
      - sys_dict
      - sys_dict_item
      - sys_public_param
      - gen_ds_config
      - gen_table
      - gen_table_column
```

---

### 2.2 数据权限模块

#### 2.2.1 方案选型

**采用部门级数据权限**

选择理由：
- 覆盖 90% 的企业场景
- 实现简单，基于 MyBatis Plus 数据权限插件
- 字段脱敏可通过现有 pig-common-xss 模块扩展

#### 2.2.2 数据范围类型

| 范围类型 | 说明 | SQL 条件示例 |
|---------|------|-------------|
| 全部数据 | 可查看所有数据 | 无限制 |
| 本部门数据 | 仅查看所属部门数据 | `dept_id = {user.deptId}` |
| 本部门及下级 | 查看所属部门及子部门数据 | `dept_id IN ({dept_tree})` |
| 仅本人数据 | 仅查看自己创建的数据 | `create_by = {user.id}` |
| 自定义 | 指定特定部门范围 | `dept_id IN ({custom_depts})` |

#### 2.2.3 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 数据范围绑定 | 角色关联数据范围类型 |
| 权限继承 | 用户继承所有角色的数据权限，取并集 |
| SQL 自动注入 | MyBatis Plus DataPermissionInterceptor |
| 部门层级 | 复用现有 SysDeptRelation 闭包表 |
| 例外配置 | 特定接口/表跳过数据权限检查 |

#### 2.2.4 数据库设计

```sql
-- 数据权限范围表
CREATE TABLE sys_data_scope (
    id BIGINT PRIMARY KEY,
    role_id BIGINT NOT NULL COMMENT '角色ID',
    scope_type TINYINT NOT NULL COMMENT '范围类型：1-全部，2-本部门，3-本部门及下级，4-仅本人，5-自定义',
    scope_value VARCHAR(512) COMMENT '自定义范围值（部门ID列表）',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_role (role_id)
);

-- 角色表扩展数据权限字段
ALTER TABLE sys_role ADD COLUMN data_scope_type TINYINT DEFAULT 1 COMMENT '数据权限范围';
```

#### 2.2.5 模块结构

```
pig-common-datapermission/
├── src/main/java/com/pig4cloud/pig/common/datapermission/
│   ├── annotation/
│   │   └── DataPermission.java        # 数据权限注解
│   ├── config/
│   │   └── DataPermissionAutoConfiguration.java
│   ├── handler/
│   │   └── DataPermissionHandler.java # 数据权限处理器
│   └── properties/
│       └── DataPermissionProperties.java
└── pom.xml
```

---

## 3. M2 里程碑：平台增强层

### 3.1 动态路由模块

#### 3.1.1 方案选型

**采用混合模式（Nacos 全局路由 + 数据库租户路由）**

选择理由：
- 全局路由用 Nacos，运维简单
- 租户可定制路由策略（如特定租户路由到专属服务实例）
- 兼顾灵活性和可管理性

#### 3.1.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 全局路由 | Nacos 配置存储，Gateway 监听实时刷新 |
| 租户路由 | 数据库存储，支持租户自定义路由规则 |
| 路由管理 API | pig-upms-biz 新增路由管理接口 |
| 缓存机制 | Caffeine 本地缓存 + Redis 分布式缓存 |
| 动态刷新 | Nacos 监听器 + 数据库事件驱动 |

#### 3.1.3 数据库设计

```sql
-- 租户路由配置表
CREATE TABLE sys_gateway_route (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL COMMENT '租户ID',
    route_id VARCHAR(64) NOT NULL COMMENT '路由ID',
    route_name VARCHAR(128) COMMENT '路由名称',
    uri VARCHAR(512) NOT NULL COMMENT '目标URI',
    predicates TEXT COMMENT '断言配置（JSON）',
    filters TEXT COMMENT '过滤器配置（JSON）',
    order_num INT DEFAULT 0 COMMENT '路由顺序',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tenant_route (tenant_id, route_id)
);
```

#### 3.1.4 路由加载流程

```
1. Gateway 启动 → 加载 Nacos 全局路由配置
2. 租户请求 → 提取 tenant_id
3. 查询租户路由缓存 → 如有则合并覆盖全局路由
4. 路由匹配 → 转发请求
```

---

### 3.2 流程引擎模块

#### 3.2.1 方案选型

**采用 Flowable 7.x + 嵌入式 Web 设计器**

选择理由：
- 开源活跃，社区支持强
- 完整 BPMN 2.0 支持（流程、决策表、案例管理）
- 提供可视化流程设计器
- Spring Boot 集成完善
- 原生支持多租户（TenantId 机制）

#### 3.2.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 流程引擎 | Flowable 7.x（最新稳定版） |
| 流程设计器 | Flowable Modeler Web 应用 |
| 多租户支持 | Flowable TenantId 机制 |
| 表单集成 | 扩展 Flowable 表单引擎 |
| 流程监控 | Flowable IDM 管理界面 |

#### 3.2.3 模块结构

```
pig-process/
├── pig-process-api/
│   └── src/main/java/com/pig4cloud/pig/process/api/
│       ├── entity/
│       ├── feign/
│       └── dto/
├── pig-process-biz/
│   └── src/main/java/com/pig4cloud/pig/process/
│       ├── controller/
│       ├── service/
│       ├── config/
│       │   └── FlowableConfiguration.java
│       └── FlowableApplication.java
└── pom.xml
```

#### 3.2.4 流程模型数据库

Flowable 自动创建以下表（前缀 ACT_）：
- ACT_RE_*：流程定义表（资源库）
- ACT_RU_*：运行时表（执行库）
- ACT_HI_*：历史表（历史库）
- ACT_ID_*：身份信息表（用户组）

多租户支持：所有表添加 TENANT_ID_ 字段。

#### 3.2.5 接口设计

```java
// 流程定义接口
POST   /process/definition/deploy    # 部署流程定义
GET    /process/definition/list      # 流程定义列表
DELETE /process/definition/{id}      # 删除流程定义

// 流程实例接口
POST   /process/instance/start       # 启动流程实例
GET    /process/instance/{id}        # 查询流程实例
DELETE /process/instance/{id}        # 删除流程实例

// 任务接口
GET    /process/task/todo            # 待办任务列表
GET    /process/task/done            # 已办任务列表
POST   /process/task/{id}/complete  # 完成任务
POST   /process/task/{id}/reject    # 驳回任务
```

---

### 3.3 报表模块

#### 3.3.1 方案选型

**采用 JimuReport 2.x（积木报表）**

选择理由：
- 国产开源，中文文档完善
- 在线可视化拖拽设计
- 支持 Spring Boot 3.x/4.x
- 天然支持多数据源
- 支持租户隔离

#### 3.3.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 报表引擎 | JimuReport 2.x |
| 设计器 | 在线可视化拖拽设计 |
| 多数据源 | 复用 Pig 动态数据源能力 |
| 多租户 | 租户独立报表空间 |
| 导出能力 | PDF、Excel、Word 多格式导出 |

#### 3.3.3 模块结构

```
pig-report/
├── src/main/java/com/pig4cloud/pig/report/
│   ├── config/
│   │   └── JimuReportConfiguration.java
│   ├── controller/
│   │   └── ReportController.java
│   └── ReportApplication.java
└── pom.xml
```

#### 3.3.4 数据库设计

JimuReport 自动创建报表相关表，需添加租户字段：

```sql
ALTER TABLE jimu_report ADD COLUMN tenant_id BIGINT DEFAULT 0;
ALTER TABLE jimu_report_db ADD COLUMN tenant_id BIGINT DEFAULT 0;
```

---

## 4. M3 里程碑：业务功能层 + 接入层

### 4.1 支付模块

#### 4.1.1 方案选型

**采用综合支付平台模式**

支付渠道：
- 微信支付（微信JSAPI、H5、APP、Native）
- 支付宝（手机网站支付、APP支付、当面付）
- 银联云闪付

扩展能力：分账、退款、对账

#### 4.1.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 支付网关 | 统一下单接口，渠道适配器模式 |
| 多租户支持 | 租户配置独立商户号、证书 |
| 分账能力 | 微信分账、支付宝分账、银联分账 |
| 订单管理 | 支付流水、退款流水 |
| 对账能力 | 每日自动对账、差异处理 |

#### 4.1.3 模块结构

```
pig-pay/
├── pig-pay-api/
│   └── src/main/java/com/pig4cloud/pig/pay/api/
│       ├── entity/
│       │   ├── PayOrder.java
│       │   ├── PayRefund.java
│       │   └── PayChannel.java
│       ├── feign/
│       └── dto/
├── pig-pay-biz/
│   └── src/main/java/com/pig4cloud/pig/pay/
│       ├── channel/
│       │   ├── WechatPayChannel.java
│       │   ├── AlipayChannel.java
│       │   └── UnionPayChannel.java
│       ├── controller/
│       ├── service/
│       └── PayApplication.java
└── pom.xml
```

#### 4.1.4 数据库设计

```sql
-- 支付订单表
CREATE TABLE pay_order (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    order_no VARCHAR(64) NOT NULL UNIQUE COMMENT '商户订单号',
    channel_order_no VARCHAR(64) COMMENT '渠道订单号',
    channel TINYINT NOT NULL COMMENT '支付渠道：1-微信，2-支付宝，3-银联',
    amount DECIMAL(12,2) NOT NULL COMMENT '支付金额（元）',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待支付，1-支付中，2-支付成功，3-支付失败',
    subject VARCHAR(256) COMMENT '订单标题',
    body VARCHAR(512) COMMENT '订单描述',
    notify_url VARCHAR(512) COMMENT '回调地址',
    expire_time DATETIME COMMENT '过期时间',
    pay_time DATETIME COMMENT '支付时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 退款订单表
CREATE TABLE pay_refund (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    refund_no VARCHAR(64) NOT NULL UNIQUE COMMENT '商户退款单号',
    channel_refund_no VARCHAR(64) COMMENT '渠道退款单号',
    order_no VARCHAR(64) NOT NULL COMMENT '原订单号',
    refund_amount DECIMAL(12,2) NOT NULL COMMENT '退款金额',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待退款，1-退款中，2-退款成功，3-退款失败',
    reason VARCHAR(256) COMMENT '退款原因',
    refund_time DATETIME COMMENT '退款时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 支付渠道配置表（租户级）
CREATE TABLE pay_channel_config (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    channel TINYINT NOT NULL COMMENT '支付渠道',
    app_id VARCHAR(128) COMMENT '应用ID',
    mch_id VARCHAR(64) COMMENT '商户号',
    api_key VARCHAR(256) COMMENT 'API密钥（加密存储）',
    cert_info TEXT COMMENT '证书信息（加密存储）',
    notify_url VARCHAR(512) COMMENT '回调地址',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tenant_channel (tenant_id, channel)
);

-- 分账记录表
CREATE TABLE pay_profit_sharing (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    order_no VARCHAR(64) NOT NULL,
    receiver_account VARCHAR(128) NOT NULL COMMENT '分账接收方账号',
    receiver_name VARCHAR(128) COMMENT '分账接收方名称',
    amount DECIMAL(12,2) NOT NULL COMMENT '分账金额',
    description VARCHAR(256) COMMENT '分账描述',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待分账，1-分账中，2-分账成功，3-分账失败',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### 4.1.5 接口设计

```java
// 统一下单
POST /pay/order/create
{
  "channel": "wechat_jsapi",  // 微信JSAPI、支付宝、银联
  "amount": 100.00,
  "subject": "订单标题",
  "body": "订单描述",
  "notifyUrl": "http://xxx/callback",
  "expireTime": "2026-06-11 12:00:00",
  "extra": {  // 渠道扩展参数
    "openid": "xxx"
  }
}

// 支付回调（各渠道调用）
POST /pay/callback/wechat
POST /pay/callback/alipay
POST /pay/callback/union

// 订单查询
GET /pay/order/{orderNo}

// 退款
POST /pay/refund/create
{
  "orderNo": "xxx",
  "refundAmount": 50.00,
  "reason": "退款原因"
}

// 分账
POST /pay/profit-sharing/create
{
  "orderNo": "xxx",
  "receivers": [
    {"account": "xxx", "name": "xxx", "amount": 10.00}
  ]
}
```

---

### 4.2 公众号+小程序模块

#### 4.2.1 方案选型

**采用公众号 + 小程序一体化方案**

选择理由：
- 公众号 + 小程序是微信 C 端生态主流组合
- UnionID 打通用户身份
- 小程序支付复用 pig-pay 微信支付能力
- 公众号模板消息 + 小程序订阅消息 = 完整消息推送

#### 4.2.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 多租户公众号 | 租户配置独立 AppID/Secret |
| 公众号管理 | 消息、菜单、粉丝、素材、模板消息 |
| 小程序管理 | 代码发布、用户授权、订阅消息 |
| 用户统一 | UnionID 打通公众号 + 小程序用户身份 |
| OAuth2 集成 | 扩展 Pig Auth，支持微信登录 |

#### 4.2.3 模块结构

```
pig-wechat/
├── pig-wechat-api/
│   └── src/main/java/com/pig4cloud/pig/wechat/api/
│       ├── entity/
│       │   ├── WxAccount.java
│       │   ├── WxUser.java
│       │   ├── WxMessage.java
│       │   └── WxMiniUser.java
│       ├── feign/
│       └── dto/
├── pig-wechat-biz/
│   └── src/main/java/com/pig4cloud/pig/wechat/
│       ├── controller/
│       │   ├── MpController.java        # 公众号接口
│       │   ├── MiniController.java      # 小程序接口
│       │   └── WxUserController.java     # 微信用户接口
│       ├── service/
│       ├── handler/
│       │   ├── MpMessageHandler.java    # 公众号消息处理
│       │   └── MiniMessageHandler.java  # 小程序消息处理
│       └── WechatApplication.java
└── pom.xml
```

#### 4.2.4 数据库设计

```sql
-- 公众号/小程序账号配置表
CREATE TABLE wx_account (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    account_type TINYINT NOT NULL COMMENT '账号类型：1-公众号，2-小程序',
    app_id VARCHAR(64) NOT NULL COMMENT 'AppID',
    app_secret VARCHAR(128) NOT NULL COMMENT 'AppSecret（加密存储）',
    token VARCHAR(64) COMMENT '消息Token',
    aes_key VARCHAR(128) COMMENT '消息加密密钥',
    original_id VARCHAR(64) COMMENT '原始ID',
    name VARCHAR(128) COMMENT '账号名称',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tenant_appid (tenant_id, app_id)
);

-- 微信用户表（公众号+小程序统一）
CREATE TABLE wx_user (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    app_id VARCHAR(64) NOT NULL COMMENT '来源AppID',
    union_id VARCHAR(64) COMMENT 'UnionID（用于打通公众号和小程序）',
    open_id VARCHAR(64) NOT NULL COMMENT 'OpenID',
    nickname VARCHAR(128) COMMENT '昵称',
    head_img_url VARCHAR(512) COMMENT '头像',
    sex TINYINT COMMENT '性别',
    country VARCHAR(64) COMMENT '国家',
    province VARCHAR(64) COMMENT '省份',
    city VARCHAR(64) COMMENT '城市',
    subscribe TINYINT DEFAULT 1 COMMENT '是否关注：0-未关注，1-已关注',
    subscribe_time DATETIME COMMENT '关注时间',
    unsubscribe_time DATETIME COMMENT '取消关注时间',
    user_id BIGINT COMMENT '关联Pig系统用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tenant_app_openid (tenant_id, app_id, open_id)
);

-- 消息记录表
CREATE TABLE wx_message (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    app_id VARCHAR(64) NOT NULL,
    open_id VARCHAR(64) NOT NULL,
    msg_type VARCHAR(32) COMMENT '消息类型',
    content TEXT COMMENT '消息内容',
    direction TINYINT COMMENT '方向：1-接收，2-发送',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 模板消息/订阅消息发送记录
CREATE TABLE wx_template_message (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    app_id VARCHAR(64) NOT NULL,
    open_id VARCHAR(64) NOT NULL,
    template_id VARCHAR(64) NOT NULL,
    data TEXT COMMENT '模板数据（JSON）',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待发送，1-发送成功，2-发送失败',
    send_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### 4.2.5 接口设计

```java
// 公众号消息接收（微信服务器调用）
POST /wechat/mp/{appId}/message

// 公众号菜单管理
GET    /wechat/mp/{appId}/menu
POST   /wechat/mp/{appId}/menu
DELETE /wechat/mp/{appId}/menu

// 公众号粉丝管理
GET  /wechat/mp/{appId}/users
POST /wechat/mp/{appId}/user/sync    # 同步粉丝列表

// 模板消息发送
POST /wechat/mp/{appId}/template/send

// 小程序登录
POST /wechat/mini/{appId}/login
{
  "code": "xxx",
  "encryptedData": "xxx",
  "iv": "xxx"
}

// 小程序订阅消息发送
POST /wechat/mini/{appId}/subscribe/send

// 小程序代码发布
POST /wechat/mini/{appId}/code/upload
```

#### 4.2.6 OAuth2 集成

扩展 Pig Auth 支持微信登录：

1. 新增授权类型：`wechat_mp`（公众号）、`wechat_mini`（小程序）
2. 认证流程：
   - 前端获取微信 code → 调用 Pig Auth `/oauth2/token`
   - 后端通过 code 换取 openid/unionid
   - 匹配 wx_user 表，关联 user_id
   - 生成 Pig Token 返回

---

### 4.3 移动端服务模块

#### 4.3.1 方案选型

**采用全端覆盖 + 完整能力 + SDK**

移动端类型：iOS App + Android App + H5

核心能力：推送、设备管理、离线消息、安全策略

SDK：iOS（Swift）、Android（Kotlin）、JavaScript（H5）

#### 4.3.2 核心能力

| 能力 | 实现方式 |
|-----|---------|
| 移动端 API | 专属接口层，简化数据结构 |
| 推送服务 | 极光推送 + 厂商通道（华为、小米、APNs） |
| 设备管理 | 设备绑定、登录设备列表、设备踢下线 |
| 离线消息 | 消息队列 + 本地存储 |
| 安全策略 | 设备指纹、API 签名校验、请求频率限制 |
| SDK | 三端 SDK 封装认证、API、推送 |

#### 4.3.3 模块结构

```
pig-mobile/
├── pig-mobile-api/
│   └── src/main/java/com/pig4cloud/pig/mobile/api/
│       ├── dto/
│       └── vo/
├── pig-mobile-biz/
│   └── src/main/java/com/pig4cloud/pig/mobile/
│       ├── controller/
│       │   ├── AppController.java       # 移动端聚合接口
│       │   ├── PushController.java      # 推送接口
│       │   └── DeviceController.java    # 设备管理接口
│       ├── service/
│       │   ├── PushService.java
│       │   └── DeviceService.java
│       ├── push/
│       │   ├── JPushChannel.java        # 极光推送
│       │   ├── HuaweiPushChannel.java   # 华为推送
│       │   ├── XiaomiPushChannel.java    # 小米推送
│       │   └── ApnsChannel.java         # 苹果APNs
│       └── MobileApplication.java
└── pom.xml
```

#### 4.3.4 数据库设计

```sql
-- 设备表
CREATE TABLE mobile_device (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    device_id VARCHAR(128) NOT NULL COMMENT '设备唯一标识',
    device_type TINYINT NOT NULL COMMENT '设备类型：1-iOS，2-Android，3-H5',
    device_name VARCHAR(128) COMMENT '设备名称',
    device_model VARCHAR(64) COMMENT '设备型号',
    os_version VARCHAR(32) COMMENT '系统版本',
    push_token VARCHAR(256) COMMENT '推送Token',
    fingerprint VARCHAR(256) COMMENT '设备指纹',
    last_login_time DATETIME COMMENT '最后登录时间',
    last_login_ip VARCHAR(64) COMMENT '最后登录IP',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tenant_user_device (tenant_id, user_id, device_id)
);

-- 推送消息表
CREATE TABLE mobile_push_message (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    device_id VARCHAR(128),
    title VARCHAR(256) COMMENT '消息标题',
    content TEXT COMMENT '消息内容',
    payload TEXT COMMENT '扩展数据（JSON）',
    push_channel TINYINT COMMENT '推送渠道',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待发送，1-发送中，2-发送成功，3-发送失败',
    read_status TINYINT DEFAULT 0 COMMENT '阅读状态：0-未读，1-已读',
    send_time DATETIME,
    read_time DATETIME,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 离线消息表
CREATE TABLE mobile_offline_message (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    message_type VARCHAR(32) COMMENT '消息类型',
    content TEXT COMMENT '消息内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### 4.3.5 接口设计

```java
// 移动端聚合接口（一次请求获取多个数据）
GET /mobile/app/init
返回：用户信息、未读消息数、系统配置等

// 设备注册
POST /mobile/device/register
{
  "deviceId": "xxx",
  "deviceType": 1,
  "deviceName": "iPhone 15",
  "deviceModel": "iPhone15,2",
  "osVersion": "17.0",
  "pushToken": "xxx",
  "fingerprint": "xxx"
}

// 获取登录设备列表
GET /mobile/device/list

// 踢下线
DELETE /mobile/device/{deviceId}

// 推送消息发送
POST /mobile/push/send
{
  "userId": 123,
  "title": "标题",
  "content": "内容",
  "payload": {}
}

// 离线消息同步
GET /mobile/message/offline?lastMessageId=xxx

// 消息已读
PUT /mobile/message/{id}/read
```

#### 4.3.6 SDK 设计

**iOS SDK（Swift）**

```swift
import PigSDK

// 初始化
PigSDK.shared.configure(apiKey: "xxx", baseUrl: "https://api.example.com")

// 登录
PigSDK.shared.auth.login(username: "xxx", password: "xxx") { result in
    // ...
}

// API 调用
PigSDK.shared.api.request("/mobile/app/init") { result in
    // ...
}

// 推送注册
PigSDK.shared.push.register(deviceToken: deviceToken)
```

**Android SDK（Kotlin）**

```kotlin
import com.pig4cloud.pig.sdk.PigSDK

// 初始化
PigSDK.init(context, apiKey = "xxx", baseUrl = "https://api.example.com")

// 登录
PigSDK.auth.login("username", "password") { result ->
    // ...
}

// API 调用
PigSDK.api.get("/mobile/app/init") { result ->
    // ...
}

// 推送注册
PigSDK.push.register(deviceToken)
```

**JavaScript SDK（H5）**

```javascript
import { PigSDK } from '@pig4cloud/pig-sdk-js'

// 初始化
const sdk = new PigSDK({ apiKey: 'xxx', baseUrl: 'https://api.example.com' })

// 登录
await sdk.auth.login({ username: 'xxx', password: 'xxx' })

// API 调用
const data = await sdk.api.get('/mobile/app/init')

// 推送注册（H5使用WebSocket或轮询）
await sdk.push.register()
```

---

## 5. 技术选型汇总

| 模块 | 技术选型 | 版本 | 备注 |
|------|---------|------|------|
| 多租户 | MyBatis Plus TenantLine | 3.5.16 | 内置插件 |
| 数据权限 | MyBatis Plus DataPermission | 3.5.16 | 内置插件 |
| 动态路由 | Spring Cloud Gateway + Nacos | - | 现有技术栈 |
| 流程引擎 | Flowable | 7.x | BPMN 2.0 |
| 报表 | JimuReport | 2.x | 积木报表 |
| 支付 | 自建网关 + 官方SDK | - | 微信/支付宝/银联 |
| 公众号/小程序 | WxJava | 4.x | 微信Java SDK |
| 移动端推送 | 极光推送 | 3.x | JPush |
| 消息队列 | Spring Cloud Stream + Kafka/RabbitMQ | - | 离线消息 |

---

## 6. 实施里程碑

### 6.1 M1：基础设施层（2-3周）

| 阶段 | 任务 | 工期 |
|------|------|------|
| 1.1 | pig-common-tenant 模块开发 | 5天 |
| 1.2 | pig-common-datapermission 模块开发 | 5天 |
| 1.3 | 现有模块适配（pig-upms、pig-auth等） | 3天 |
| 1.4 | 测试与文档 | 2天 |

### 6.2 M2：平台增强层（3-4周）

| 阶段 | 任务 | 工期 |
|------|------|------|
| 2.1 | 动态路由开发 | 5天 |
| 2.2 | pig-process 模块开发（Flowable集成） | 8天 |
| 2.3 | pig-report 模块开发（JimuReport集成） | 5天 |
| 2.4 | 测试与文档 | 4天 |

### 6.3 M3：业务功能层 + 接入层（2-3周）

| 阶段 | 任务 | 工期 |
|------|------|------|
| 3.1 | pig-pay 模块开发 | 6天 |
| 3.2 | pig-wechat 模块开发 | 5天 |
| 3.3 | pig-mobile 模块开发 | 5天 |
| 3.4 | 测试与文档 | 3天 |

**总工期：约 8-10 周**

---

## 7. 风险与依赖

### 7.1 技术风险

| 风险 | 影响 | 缓解措施 |
|------|------|---------|
| MyBatis Plus 多租户插件性能 | 高并发下 SQL 注入可能有性能损耗 | 压测验证，必要时优化拦截器 |
| Flowable 多租户隔离复杂度 | 流程定义、实例隔离需仔细设计 | 参考 Flowable 官方多租户最佳实践 |
| 支付回调可靠性 | 网络异常可能导致状态不一致 | 幂等处理、重试机制、人工对账 |
| 推送到达率 | 厂商通道稳定性影响 | 多通道备份（极光+厂商） |

### 7.2 依赖项

| 依赖 | 状态 | 备注 |
|------|------|------|
| Spring Boot 4.0.6 | 已具备 | - |
| MyBatis Plus 3.5.16 | 已具备 | - |
| Flowable 7.x | 需引入 | Maven 依赖 |
| JimuReport 2.x | 需引入 | Maven 依赖 |
| WxJava 4.x | 需引入 | 微信SDK |
| JPush SDK | 需引入 | 推送SDK |
| 微信支付SDK | 需引入 | 官方SDK |
| 支付宝SDK | 需引入 | 官方SDK |
| 银联SDK | 需引入 | 官方SDK |

### 7.3 外部系统依赖

| 系统 | 用途 | 备注 |
|------|------|------|
| 微信开放平台 | 公众号/小程序管理 | 需申请账号 |
| 微信支付商户平台 | 支付能力 | 需申请商户号 |
| 支付宝开放平台 | 支付能力 | 需申请账号 |
| 银联商户平台 | 支付能力 | 需申请商户号 |
| 极光推送 | 移动端推送 | 需申请AppKey |

---

## 8. 后续工作

1. 用户审阅本文档
2. 基于 `superpowers:writing-plans` 编写详细实施计划
3. 按里程碑分阶段实施
4. 每个里程碑完成后进行集成测试
5. 全部完成后进行系统测试与文档编写

---

## 附录：参考资料

- [MyBatis Plus 多租户插件](https://github.com/baomidou/mybatis-plus)
- [Flowable 官方文档](https://www.flowable.com/open-source/docs/)
- [JimuReport 官方文档](http://www.jimureport.com/)
- [WxJava GitHub](https://github.com/Wechat-Group/WxJava)
- [极光推送文档](https://docs.jiguang.cn/)
- [微信支付开发文档](https://pay.weixin.qq.com/wiki/doc/apiv3/index.shtml)
- [支付宝开放平台](https://opendocs.alipay.com/)
- [银联开放平台](https://open.unionpay.com/)
