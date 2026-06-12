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

import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.cfg.multitenant.TenantInfo;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

/**
 * Flowable 多租户配置
 * <p>
 * 通过实现 EngineConfigurationConfigurer 接口，配置 Flowable 引擎的多租户支持。 租户信息从 TenantContextHolder
 * 中获取，确保流程数据的租户隔离。
 *
 * @author lengleng
 * @date 2025-06-11
 */
@Slf4j
@Configuration
public class FlowableConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

	/**
	 * 配置 Flowable 流程引擎的多租户支持
	 * <p>
	 * 设置 TenantAware 数据源和租户提供者，确保流程实例、任务等数据按照租户隔离存储。
	 * @param processEngineConfiguration 流程引擎配置
	 */
	@Override
	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		log.info("初始化 Flowable 多租户配置");

		// 配置租户提供者，从 TenantContextHolder 获取当前租户ID
		processEngineConfiguration.setDefaultTenantProvider(new PigTenantProvider());

		// 启用多租户支持
		processEngineConfiguration.setEnableMultiTenant(true);

		// 配置租户信息获取策略
		processEngineConfiguration.setTenantInfo(new PigTenantInfo());

		log.info("Flowable 多租户配置完成");
	}

	/**
	 * Pig 租户提供者
	 * <p>
	 * 从 TenantContextHolder 获取当前请求的租户ID，用于 Flowable 引擎的多租户数据隔离。
	 */
	public static class PigTenantProvider implements java.util.function.Supplier<String> {

		@Override
		public String get() {
			String tenantId = TenantContextHolder.getTenantId();
			if (tenantId == null) {
				log.warn("当前租户ID为空，使用默认租户");
				return "default";
			}
			return tenantId;
		}

	}

	/**
	 * Pig 租户信息
	 * <p>
	 * 提供租户信息的获取和验证策略。
	 */
	public static class PigTenantInfo implements TenantInfo {

		@Override
		public String getDefaultTenant() {
			return "default";
		}

		@Override
		public boolean validateTenant(String tenantId) {
			// 验证租户ID有效性
			return tenantId != null && !tenantId.trim().isEmpty();
		}

		@Override
		public Consumer<String> getTenantChangedCallback() {
			return tenantId -> {
				if (log.isDebugEnabled()) {
					log.debug("租户切换: {}", tenantId);
				}
			};
		}

	}

}
