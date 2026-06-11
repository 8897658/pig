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

package com.pig4cloud.pig.common.tenant.interceptor;

import com.pig4cloud.pig.common.tenant.annotation.TenantIgnore;
import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import com.pig4cloud.pig.common.tenant.properties.TenantProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * 租户请求拦截器
 * <p>
 * 从请求头中提取租户ID并设置到线程上下文中，支持在后续的数据库操作中自动进行租户过滤。 支持 @TenantIgnore 注解标注的方法忽略租户过滤。
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@RequiredArgsConstructor
public class TenantRequestInterceptor implements AsyncHandlerInterceptor {

	/**
	 * 租户ID请求头名称
	 */
	private static final String TENANT_ID_HEADER = "TENANT-ID";

	/**
	 * 租户配置属性
	 */
	private final TenantProperties properties;

	/**
	 * 请求预处理
	 * <p>
	 * 从请求头中提取租户ID并设置到 TenantContextHolder。 如果方法标注了 @TenantIgnore 注解，则不设置租户上下文。
	 * @param request HTTP 请求
	 * @param response HTTP 响应
	 * @param handler 处理器
	 * @return true 表示继续执行，false 表示中断请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. 非控制器请求直接跳过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 2. 检查是否开启多租户
		if (!properties.isEnabled()) {
			return true;
		}

		// 3. 检查是否有 @TenantIgnore 注解
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		TenantIgnore tenantIgnore = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), TenantIgnore.class);
		if (tenantIgnore == null) {
			// 尝试从类级别获取注解
			tenantIgnore = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), TenantIgnore.class);
		}

		// 4. 如果标注了 @TenantIgnore，则跳过租户上下文设置
		if (tenantIgnore != null) {
			log.debug("Method {} is marked with @TenantIgnore, skipping tenant context setup",
					handlerMethod.getMethod().getName());
			return true;
		}

		// 5. 从请求头获取租户ID
		String tenantId = request.getHeader(TENANT_ID_HEADER);
		if (tenantId != null && !tenantId.isEmpty()) {
			TenantContextHolder.setTenantId(tenantId);
			log.debug("Set tenant ID from request header: {}", tenantId);
		}
		else {
			log.debug("No tenant ID found in request header, tenant filtering will be skipped");
		}

		return true;
	}

	/**
	 * 请求完成后清理
	 * <p>
	 * 清除 TenantContextHolder 中的租户ID，避免内存泄漏。
	 * @param request HTTP 请求
	 * @param response HTTP 响应
	 * @param handler 处理器
	 * @param ex 异常（如果有）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		TenantContextHolder.clear();
	}

	/**
	 * 异步请求完成后清理
	 * <p>
	 * 清除 TenantContextHolder 中的租户ID，避免内存泄漏。
	 * @param request HTTP 请求
	 * @param response HTTP 响应
	 * @param handler 处理器
	 */
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		TenantContextHolder.clear();
	}

}
