/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
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

import lombok.extern.slf4j.Slf4j;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * Flowable 流程引擎配置
 * <p>
 * 通过实现 EngineConfigurationConfigurer 接口，配置 Flowable 引擎。
 * Flowable 7.x 的多租户支持通过在部署、启动流程时设置 tenantId 来实现数据隔离。
 * <p>
 * 多租户策略：
 * - 部署时：repositoryService.createDeployment().tenantId(tenantId).deploy()
 * - 启动流程：runtimeService.createProcessInstanceBuilder().tenantId(tenantId).start()
 * - 查询：query.taskTenantId(tenantId) 或 query.processInstanceTenantId(tenantId)
 *
 * @author lengleng
 * @date 2025-06-11
 */
@Slf4j
@Configuration
public class FlowableConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

	/**
	 * 配置 Flowable 流程引擎
	 * <p>
	 * 设置引擎的基本配置参数，优化性能。
	 * @param processEngineConfiguration 流程引擎配置
	 */
	@Override
	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		log.info("初始化 Flowable 流程引擎配置");

		// 设置数据库表自动更新策略
		processEngineConfiguration.setDatabaseSchemaUpdate("true");

		// 启用异步执行器
		processEngineConfiguration.setAsyncExecutorActivate(true);

		log.info("Flowable 流程引擎配置完成");
	}

}