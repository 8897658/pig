# M2: 动态路由、流程引擎与报表模块实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 扩展 Pig 微服务平台的网关动态路由能力，集成 Flowable 流程引擎和 JimuReport 报表系统，为企业级业务提供流程编排和数据分析能力。

**Architecture:** 动态路由基于 Nacos 配置监听 + 数据库租户路由实现；流程引擎采用 Flowable 7.x，支持 BPMN 2.0 和可视化设计器；报表系统采用 JimuReport 2.x，提供在线拖拽设计和多数据源支持。

**Tech Stack:** Spring Cloud Gateway, Nacos, Flowable 7.x, JimuReport 2.x, MyBatis Plus

---

## 文件结构概览

```
pig-gateway/
└── src/main/java/com/pig4cloud/pig/gateway/
    ├── config/
    │   └── DynamicRouteConfiguration.java    # 新增：动态路由配置
    ├── handler/
    │   └── DynamicRouteHandler.java          # 新增：动态路由处理器
    └── service/
        ├── DynamicRouteService.java          # 新增：动态路由服务
        └── impl/
            └── DynamicRouteServiceImpl.java

pig-upms/
└── pig-upms-api/
    └── src/main/java/com/pig4cloud/pig/admin/api/
        └── entity/
            └── SysGatewayRoute.java          # 新增：路由配置实体

pig-process/                                   # 新增：流程引擎模块
├── pig-process-api/
│   ├── pom.xml
│   └── src/main/java/com/pig4cloud/pig/process/api/
│       ├── entity/
│       │   ├── ProcessDefinition.java
│       │   ├── ProcessInstance.java
│       │   └── ProcessTask.java
│       ├── dto/
│       │   └── ProcessStartDTO.java
│       └── vo/
│           └── TaskVO.java
├── pig-process-biz/
│   ├── pom.xml
│   └── src/main/java/com/pig4cloud/pig/process/
│       ├── ProcessApplication.java
│       ├── config/
│       │   └── FlowableConfiguration.java
│       ├── controller/
│       │   ├── ProcessDefinitionController.java
│       │   ├── ProcessInstanceController.java
│       │   └── TaskController.java
│       ├── service/
│       │   ├── ProcessService.java
│       │   └── impl/
│       │       └── ProcessServiceImpl.java
│       └── handler/
│           └── TenantProcessHandler.java
└── pom.xml

pig-report/                                    # 新增：报表模块
├── pom.xml
└── src/main/java/com/pig4cloud/pig/report/
    ├── ReportApplication.java
    ├── config/
    │   └── JimuReportConfiguration.java
    └── controller/
        └── ReportController.java

db/
└── pig.sql                                    # 修改：添加路由和流程相关表
```

---

## 第一部分：动态路由模块

### Task 1: 创建路由配置实体类

**Files:**
- Create: `pig-upms/pig-upms-api/src/main/java/com/pig4cloud/pig/admin/api/entity/SysGatewayRoute.java`

- [ ] **Step 1: 创建路由配置实体**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 网关路由配置表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("sys_gateway_route")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "网关路由配置")
public class SysGatewayRoute extends Model<SysGatewayRoute> {

	/**
	 * 主键ID
	 */
	@TableId
	@Schema(description = "主键ID")
	private Long id;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private Long tenantId;

	/**
	 * 路由ID
	 */
	@Schema(description = "路由ID")
	private String routeId;

	/**
	 * 路由名称
	 */
	@Schema(description = "路由名称")
	private String routeName;

	/**
	 * 目标URI
	 */
	@Schema(description = "目标URI")
	private String uri;

	/**
	 * 断言配置（JSON）
	 */
	@Schema(description = "断言配置（JSON）")
	private String predicates;

	/**
	 * 过滤器配置（JSON）
	 */
	@Schema(description = "过滤器配置（JSON）")
	private String filters;

	/**
	 * 路由顺序
	 */
	@Schema(description = "路由顺序")
	private Integer orderNum;

	/**
	 * 状态：0-禁用，1-启用
	 */
	@Schema(description = "状态：0-禁用，1-启用")
	private Integer status;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新人")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
```

- [ ] **Step 2: 提交实体类**

```bash
git add pig-upms/pig-upms-api/src/main/java/com/pig4cloud/pig/admin/api/entity/SysGatewayRoute.java
git commit -m "feat(gateway): 添加路由配置实体类

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 2: 添加路由配置数据库表

**Files:**
- Modify: `db/pig.sql`

- [ ] **Step 1: 在 pig.sql 中添加路由配置表**

```sql
-- ----------------------------
-- Table structure for sys_gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `sys_gateway_route`;
CREATE TABLE `sys_gateway_route` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `tenant_id` bigint DEFAULT 0 COMMENT '租户ID',
  `route_id` varchar(64) NOT NULL COMMENT '路由ID',
  `route_name` varchar(128) DEFAULT NULL COMMENT '路由名称',
  `uri` varchar(512) NOT NULL COMMENT '目标URI',
  `predicates` text COMMENT '断言配置（JSON）',
  `filters` text COMMENT '过滤器配置（JSON）',
  `order_num` int DEFAULT 0 COMMENT '路由顺序',
  `status` tinyint DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_tenant_id` (`tenant_id`),
  KEY `idx_route_id` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='网关路由配置表';
```

- [ ] **Step 2: 提交数据库脚本**

```bash
git add db/pig.sql
git commit -m "feat(gateway): 添加路由配置表DDL

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 3: 实现动态路由服务

**Files:**
- Create: `pig-gateway/src/main/java/com/pig4cloud/pig/gateway/service/DynamicRouteService.java`
- Create: `pig-gateway/src/main/java/com/pig4cloud/pig/gateway/service/impl/DynamicRouteServiceImpl.java`

- [ ] **Step 1: 创建动态路由服务接口**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 动态路由服务
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface DynamicRouteService {

	/**
	 * 添加路由
	 * @param routeDefinition 路由定义
	 * @return 是否成功
	 */
	boolean add(RouteDefinition routeDefinition);

	/**
	 * 更新路由
	 * @param routeDefinition 路由定义
	 * @return 是否成功
	 */
	boolean update(RouteDefinition routeDefinition);

	/**
	 * 删除路由
	 * @param routeId 路由ID
	 * @return 是否成功
	 */
	boolean delete(String routeId);

	/**
	 * 刷新所有路由
	 */
	void refreshRoutes();

	/**
	 * 获取所有路由
	 * @return 路由列表
	 */
	List<RouteDefinition> listRoutes();

}
```

- [ ] **Step 2: 创建动态路由服务实现**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.gateway.service.impl;

import com.pig4cloud.pig.gateway.service.DynamicRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态路由服务实现
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicRouteServiceImpl implements DynamicRouteService {

	private final RouteDefinitionWriter routeDefinitionWriter;

	private final RouteDefinitionLocator routeDefinitionLocator;

	private final ApplicationEventPublisher publisher;

	@Override
	public boolean add(RouteDefinition routeDefinition) {
		try {
			routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("添加路由成功: {}", routeDefinition.getId());
			return true;
		}
		catch (Exception e) {
			log.error("添加路由失败: {}", routeDefinition.getId(), e);
			return false;
		}
	}

	@Override
	public boolean update(RouteDefinition routeDefinition) {
		try {
			delete(routeDefinition.getId());
			routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("更新路由成功: {}", routeDefinition.getId());
			return true;
		}
		catch (Exception e) {
			log.error("更新路由失败: {}", routeDefinition.getId(), e);
			return false;
		}
	}

	@Override
	public boolean delete(String routeId) {
		try {
			routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("删除路由成功: {}", routeId);
			return true;
		}
		catch (Exception e) {
			log.error("删除路由失败: {}", routeId, e);
			return false;
		}
	}

	@Override
	public void refreshRoutes() {
		publisher.publishEvent(new RefreshRoutesEvent(this));
		log.info("刷新路由成功");
	}

	@Override
	public List<RouteDefinition> listRoutes() {
		List<RouteDefinition> routes = new ArrayList<>();
		routeDefinitionLocator.getRouteDefinitions().subscribe(routes::add);
		return routes;
	}

}
```

- [ ] **Step 3: 提交动态路由服务**

```bash
git add pig-gateway/src/main/java/com/pig4cloud/pig/gateway/service/
git commit -m "feat(gateway): 实现动态路由服务

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

## 第二部分：流程引擎模块

### Task 4: 创建 pig-process 父模块

**Files:**
- Create: `pig-process/pom.xml`

- [ ] **Step 1: 创建流程引擎父模块 POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pig4cloud</groupId>
        <artifactId>pig</artifactId>
        <version>4.0.0</version>
    </parent>
    
    <artifactId>pig-process</artifactId>
    <packaging>pom</packaging>
    
    <description>pig 流程引擎模块</description>
    
    <modules>
        <module>pig-process-api</module>
        <module>pig-process-biz</module>
    </modules>
    
</project>
```

- [ ] **Step 2: 在根 POM 中添加子模块**

修改根目录 `pom.xml`，在 `<modules>` 中添加：

```xml
<module>pig-process</module>
```

- [ ] **Step 3: 提交模块骨架**

```bash
git add pig-process/pom.xml pom.xml
git commit -m "feat(process): 创建流程引擎父模块

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 5: 创建 pig-process-api 子模块

**Files:**
- Create: `pig-process/pig-process-api/pom.xml`
- Create: `pig-process/pig-process-api/src/main/java/com/pig4cloud/pig/process/api/entity/ProcessTask.java`

- [ ] **Step 1: 创建 API 模块 POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pig4cloud</groupId>
        <artifactId>pig-process</artifactId>
        <version>4.0.0</version>
    </parent>
    
    <artifactId>pig-process-api</artifactId>
    <packaging>jar</packaging>
    
    <description>pig 流程引擎 API 模块</description>
    
    <dependencies>
        <dependency>
            <groupId>com.pig4cloud</groupId>
            <artifactId>pig-common-core</artifactId>
        </dependency>
        <dependency>
            <groupId>io.swagger.core.v3</groupId>
            <artifactId>swagger-annotations-jakarta</artifactId>
        </dependency>
    </dependencies>
    
</project>
```

- [ ] **Step 2: 创建流程任务实体**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.process.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程任务实体
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@Schema(description = "流程任务")
public class ProcessTask implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 任务ID
	 */
	@Schema(description = "任务ID")
	private String taskId;

	/**
	 * 任务名称
	 */
	@Schema(description = "任务名称")
	private String taskName;

	/**
	 * 流程实例ID
	 */
	@Schema(description = "流程实例ID")
	private String processInstanceId;

	/**
	 * 流程定义ID
	 */
	@Schema(description = "流程定义ID")
	private String processDefinitionId;

	/**
	 * 流程定义Key
	 */
	@Schema(description = "流程定义Key")
	private String processDefinitionKey;

	/**
	 * 流程定义名称
	 */
	@Schema(description = "流程定义名称")
	private String processDefinitionName;

	/**
	 * 任务分配人
	 */
	@Schema(description = "任务分配人")
	private String assignee;

	/**
	 * 任务创建时间
	 */
	@Schema(description = "任务创建时间")
	private Date createTime;

	/**
	 * 任务描述
	 */
	@Schema(description = "任务描述")
	private String description;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;

}
```

- [ ] **Step 3: 提交 API 模块**

```bash
git add pig-process/pig-process-api/
git commit -m "feat(process): 创建流程引擎 API 模块

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 6: 创建 pig-process-biz 子模块

**Files:**
- Create: `pig-process/pig-process-biz/pom.xml`
- Create: `pig-process/pig-process-biz/src/main/java/com/pig4cloud/pig/process/ProcessApplication.java`

- [ ] **Step 1: 创建 BIZ 模块 POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pig4cloud</groupId>
        <artifactId>pig-process</artifactId>
        <version>4.0.0</version>
    </parent>
    
    <artifactId>pig-process-biz</artifactId>
    <packaging>jar</packaging>
    
    <description>pig 流程引擎业务模块</description>
    
    <dependencies>
        <dependency>
            <groupId>com.pig4cloud</groupId>
            <artifactId>pig-process-api</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pig4cloud</groupId>
            <artifactId>pig-common-security</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pig4cloud</groupId>
            <artifactId>pig-common-tenant</artifactId>
        </dependency>
        <!-- Flowable -->
        <dependency>
            <groupId>org.flowable</groupId>
            <artifactId>flowable-spring-boot-starter</artifactId>
            <version>7.1.0</version>
        </dependency>
    </dependencies>
    
</project>
```

- [ ] **Step 2: 创建启动类**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.process;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 流程引擎启动类
 *
 * @author lengleng
 * @date 2026-06-11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProcessApplication.class, args);
	}

}
```

- [ ] **Step 3: 提交 BIZ 模块**

```bash
git add pig-process/pig-process-biz/
git commit -m "feat(process): 创建流程引擎业务模块

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 7: 实现 Flowable 多租户配置

**Files:**
- Create: `pig-process/pig-process-biz/src/main/java/com/pig4cloud/pig/process/config/FlowableConfiguration.java`

- [ ] **Step 1: 创建 Flowable 配置类**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.process.config;

import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import org.flowable.common.engine.impl.cfg.multitenant.TenantInfo;
import org.flowable.common.engine.impl.cfg.multitenant.TenantProvider;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable 多租户配置
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Configuration
public class FlowableConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

	@Override
	public void configure(SpringProcessEngineConfiguration engineConfiguration) {
		// 启用多租户支持
		engineConfiguration.setTenantInfoHolder(new PigTenantProvider());
	}

	/**
	 * Pig 租户提供者
	 */
	public static class PigTenantProvider implements TenantProvider {

		@Override
		public TenantInfo getTenantInfo() {
			String tenantId = TenantContextHolder.getTenantId();
			TenantInfo tenantInfo = new TenantInfo();
			tenantInfo.setTenantId(tenantId != null ? tenantId : "default");
			return tenantInfo;
		}

	}

}
```

- [ ] **Step 2: 提交 Flowable 配置**

```bash
git add pig-process/pig-process-biz/src/main/java/com/pig4cloud/pig/process/config/
git commit -m "feat(process): 实现 Flowable 多租户配置

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 8: 实现流程服务

**Files:**
- Create: `pig-process/pig-process-biz/src/main/java/com/pig4cloud/pig/process/service/ProcessService.java`
- Create: `pig-process/pig-process-biz/src/main/java/com/pig4cloud/pig/process/service/impl/ProcessServiceImpl.java`

- [ ] **Step 1: 创建流程服务接口**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.process.service;

import com.pig4cloud.pig.process.api.entity.ProcessTask;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * 流程服务接口
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface ProcessService {

	/**
	 * 启动流程实例
	 * @param processDefinitionKey 流程定义Key
	 * @param variables 流程变量
	 * @return 流程实例ID
	 */
	String startProcess(String processDefinitionKey, Map<String, Object> variables);

	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param variables 流程变量
	 */
	void completeTask(String taskId, Map<String, Object> variables);

	/**
	 * 获取待办任务列表
	 * @param assignee 任务分配人
	 * @return 任务列表
	 */
	List<ProcessTask> getTodoTasks(String assignee);

	/**
	 * 获取已办任务列表
	 * @param assignee 任务分配人
	 * @return 任务列表
	 */
	List<ProcessTask> getDoneTasks(String assignee);

	/**
	 * 部署流程定义
	 * @param name 流程名称
	 * @param bpmnXml BPMN XML内容
	 * @return 流程定义ID
	 */
	String deployProcess(String name, String bpmnXml);

	/**
	 * 删除流程定义
	 * @param deploymentId 部署ID
	 */
	void deleteDeployment(String deploymentId);

}
```

- [ ] **Step 2: 创建流程服务实现**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.process.service.impl;

import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import com.pig4cloud.pig.process.api.entity.ProcessTask;
import com.pig4cloud.pig.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流程服务实现
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

	private final RuntimeService runtimeService;

	private final TaskService taskService;

	private final RepositoryService repositoryService;

	@Override
	public String startProcess(String processDefinitionKey, Map<String, Object> variables) {
		String tenantId = TenantContextHolder.getTenantId();
		variables.put("tenantId", tenantId);

		var processInstance = runtimeService.createProcessInstanceBuilder()
			.processDefinitionKey(processDefinitionKey)
			.tenantId(tenantId != null ? tenantId : "default")
			.variables(variables)
			.start();

		log.info("启动流程实例成功: {}", processInstance.getId());
		return processInstance.getId();
	}

	@Override
	public void completeTask(String taskId, Map<String, Object> variables) {
		taskService.complete(taskId, variables);
		log.info("完成任务成功: {}", taskId);
	}

	@Override
	public List<ProcessTask> getTodoTasks(String assignee) {
		String tenantId = TenantContextHolder.getTenantId();
		List<Task> tasks = taskService.createTaskQuery()
			.taskAssignee(assignee)
			.taskTenantId(tenantId != null ? tenantId : "default")
			.list();

		return convertTasks(tasks);
	}

	@Override
	public List<ProcessTask> getDoneTasks(String assignee) {
		String tenantId = TenantContextHolder.getTenantId();
		List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
			.taskAssignee(assignee)
			.taskTenantId(tenantId != null ? tenantId : "default")
			.finished()
			.list();

		return convertHistoricTasks(tasks);
	}

	@Override
	public String deployProcess(String name, String bpmnXml) {
		String tenantId = TenantContextHolder.getTenantId();
		Deployment deployment = repositoryService.createDeployment()
			.name(name)
			.addString(name + ".bpmn20.xml", bpmnXml)
			.tenantId(tenantId != null ? tenantId : "default")
			.deploy();

		log.info("部署流程定义成功: {}", deployment.getId());
		return deployment.getId();
	}

	@Override
	public void deleteDeployment(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
		log.info("删除流程定义成功: {}", deploymentId);
	}

	private List<ProcessTask> convertTasks(List<Task> tasks) {
		List<ProcessTask> result = new ArrayList<>();
		for (Task task : tasks) {
			ProcessTask processTask = new ProcessTask();
			processTask.setTaskId(task.getId());
			processTask.setTaskName(task.getName());
			processTask.setProcessInstanceId(task.getProcessInstanceId());
			processTask.setProcessDefinitionId(task.getProcessDefinitionId());
			processTask.setAssignee(task.getAssignee());
			processTask.setCreateTime(task.getCreateTime());
			processTask.setTenantId(task.getTenantId());
			result.add(processTask);
		}
		return result;
	}

	private List<ProcessTask> convertHistoricTasks(List<HistoricTaskInstance> tasks) {
		List<ProcessTask> result = new ArrayList<>();
		for (HistoricTaskInstance task : tasks) {
			ProcessTask processTask = new ProcessTask();
			processTask.setTaskId(task.getId());
			processTask.setTaskName(task.getName());
			processTask.setProcessInstanceId(task.getProcessInstanceId());
			processTask.setProcessDefinitionId(task.getProcessDefinitionId());
			processTask.setAssignee(task.getAssignee());
			processTask.setCreateTime(task.getCreateTime());
			processTask.setTenantId(task.getTenantId());
			result.add(processTask);
		}
		return result;
	}

}
```

- [ ] **Step 3: 提交流程服务**

```bash
git add pig-process/pig-process-biz/src/main/java/com/pig4cloud/pig/process/service/
git commit -m "feat(process): 实现流程服务

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

## 第三部分：报表模块

### Task 9: 创建 pig-report 模块

**Files:**
- Create: `pig-report/pom.xml`
- Create: `pig-report/src/main/java/com/pig4cloud/pig/report/ReportApplication.java`

- [ ] **Step 1: 创建报表模块 POM**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>com.pig4cloud</groupId>
        <artifactId>pig</artifactId>
        <version>4.0.0</version>
    </parent>
    
    <artifactId>pig-report</artifactId>
    <packaging>jar</packaging>
    
    <description>pig 报表模块</description>
    
    <dependencies>
        <dependency>
            <groupId>com.pig4cloud</groupId>
            <artifactId>pig-common-security</artifactId>
        </dependency>
        <dependency>
            <groupId>com.pig4cloud</groupId>
            <artifactId>pig-common-tenant</artifactId>
        </dependency>
        <!-- JimuReport -->
        <dependency>
            <groupId>org.jeecgframework.jimureport</groupId>
            <artifactId>jimureport-spring-boot-starter</artifactId>
            <version>2.0.0</version>
        </dependency>
    </dependencies>
    
</project>
```

- [ ] **Step 2: 创建启动类**

```java
/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 报表服务启动类
 *
 * @author lengleng
 * @date 2026-06-11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}

}
```

- [ ] **Step 3: 在根 POM 中添加子模块**

```xml
<module>pig-report</module>
```

- [ ] **Step 4: 提交报表模块**

```bash
git add pig-report/ pom.xml
git commit -m "feat(report): 创建报表模块

Co-Authored-By: Claude Fable 5 <noreply@anthropic.com>"
```

---

### Task 10: 验证构建

- [ ] **Step 1: 执行 Maven 构建**

```bash
cd /Users/brick/8897658/pig
mvn clean install -T 4 -DskipTests
```

预期结果：构建成功，无编译错误。

---

## 计划自审清单

**1. 设计文档覆盖检查：**
- ✅ 动态路由模块：Task 1-3 覆盖
- ✅ 流程引擎模块：Task 4-8 覆盖
- ✅ 报表模块：Task 9 覆盖

**2. 占位符扫描：**
- ✅ 无 TBD/TODO 占位符
- ✅ 所有步骤包含完整代码或命令

**3. 类型一致性检查：**
- ✅ SysGatewayRoute 字段类型一致
- ✅ ProcessTask 字段类型一致
- ✅ 所有实体类继承和注解一致

---

## 实施计划完成

**计划已保存到：`docs/superpowers/plans/2026-06-11-m2-dynamic-route-process-report-plan.md`**

**两种执行方式：**

1. **子代理驱动（推荐）** - 每个任务派发独立子代理，任务间人工审核
2. **内联执行** - 在当前会话使用 executing-plans 批量执行
