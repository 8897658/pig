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

package com.pig4cloud.pig.common.tenant.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.pig4cloud.pig.common.tenant.handler.TenantHandler;
import com.pig4cloud.pig.common.tenant.interceptor.TenantRequestInterceptor;
import com.pig4cloud.pig.common.tenant.properties.TenantProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 租户自动配置类
 * <p>
 * 当配置 pig.tenant.enabled=true 时自动激活多租户功能。
 * 配置包括：
 * 1. TenantLineInnerInterceptor - MyBatis Plus 租户拦截器，自动拼接租户过滤条件
 * 2. TenantRequestInterceptor - Web 请求拦截器，从请求头提取租户ID
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
@EnableConfigurationProperties(TenantProperties.class)
@ConditionalOnProperty(prefix = TenantProperties.PREFIX, name = "enabled", havingValue = "true")
public class TenantAutoConfiguration implements WebMvcConfigurer {

	/**
	 * 租户配置属性
	 */
	private final TenantProperties properties;

	/**
	 * 租户处理器
	 * <p>
	 * 实现 TenantLineHandler 接口，提供租户ID和忽略表逻辑
	 *
	 * @return TenantHandler
	 */
	@Bean
	@ConditionalOnMissingBean
	public TenantHandler tenantHandler() {
		log.info("Initializing TenantHandler with column: {}", properties.getColumn());
		return new TenantHandler(properties);
	}

	/**
	 * 租户行拦截器
	 * <p>
	 * MyBatis Plus 内置拦截器，自动在 SQL 语句中拼接租户过滤条件
	 *
	 * @param tenantHandler 租户处理器
	 * @return TenantLineInnerInterceptor
	 */
	@Bean
	@ConditionalOnMissingBean(TenantLineInnerInterceptor.class)
	public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantHandler tenantHandler) {
		log.info("Initializing TenantLineInnerInterceptor");
		return new TenantLineInnerInterceptor(tenantHandler);
	}

	/**
	 * 将 TenantLineInnerInterceptor 添加到 MybatisPlusInterceptor
	 * <p>
	 * 注意：租户拦截器应该添加在最前面，确保在所有其他拦截器之前执行。
	 * 执行顺序：租户拦截器 -> 数据权限 -> 动态表名 -> 分页 -> 乐观锁
	 *
	 * @param interceptor MyBatis Plus 拦截器
	 * @param tenantLineInnerInterceptor 租户行拦截器
	 */
	@Autowired(required = false)
	@ConditionalOnBean(MybatisPlusInterceptor.class)
	public void configureTenantInterceptor(MybatisPlusInterceptor interceptor,
			TenantLineInnerInterceptor tenantLineInnerInterceptor) {
		if (interceptor != null) {
			log.info("Adding TenantLineInnerInterceptor to MybatisPlusInterceptor chain at position 0");
			// 租户拦截器应该添加在最前面，确保在所有其他拦截器之前执行
			interceptor.getInterceptors().add(0, tenantLineInnerInterceptor);
		}
	}

	/**
	 * 租户请求拦截器
	 * <p>
	 * 从请求头提取租户ID并设置到线程上下文
	 *
	 * @return TenantRequestInterceptor
	 */
	@Bean
	@ConditionalOnMissingBean
	public TenantRequestInterceptor tenantRequestInterceptor() {
		log.info("Initializing TenantRequestInterceptor");
		return new TenantRequestInterceptor(properties);
	}

	/**
	 * 注册租户请求拦截器
	 * <p>
	 * 将拦截器添加到 Spring MVC 拦截器链中
	 *
	 * @param registry 拦截器注册表
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tenantRequestInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/actuator/**", "/error", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**");
		log.info("TenantRequestInterceptor registered for path pattern: /**");
	}

}
