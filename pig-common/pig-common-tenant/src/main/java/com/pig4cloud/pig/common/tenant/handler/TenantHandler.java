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

package com.pig4cloud.pig.common.tenant.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import com.pig4cloud.pig.common.tenant.properties.TenantProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;

import java.util.List;

/**
 * 租户 SQL 处理器
 * <p>
 * 基于 MyBatis Plus TenantLineHandler 实现，在 SQL 执行前自动拼接租户过滤条件。
 * </p>
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@RequiredArgsConstructor
public class TenantHandler implements TenantLineHandler {

	/**
	 * 租户配置属性
	 */
	private final TenantProperties properties;

	/**
	 * 获取租户ID值
	 * <p>
	 * 从 TenantContextHolder 中获取当前租户ID，返回对应的 SQL 表达式。
	 * 如果未设置租户ID，返回 NullValue 以避免 SQL 错误。
	 *
	 * @return 租户ID的 SQL 表达式
	 */
	@Override
	public Expression getTenantId() {
		String tenantId = TenantContextHolder.getTenantId();
		if (tenantId == null) {
			log.warn("Tenant ID is null, returning NullValue for tenant filter");
			return new NullValue();
		}
		return new StringValue(tenantId);
	}

	/**
	 * 获取租户字段名称
	 *
	 * @return 租户字段名称，默认为 tenant_id
	 */
	@Override
	public String getTenantIdColumn() {
		return properties.getColumn();
	}

	/**
	 * 判断是否忽略租户过滤
	 * <p>
	 * 根据配置的忽略表列表判断当前表是否需要进行租户过滤。
	 *
	 * @param tableName 表名
	 * @return true 表示忽略租户过滤，false 表示需要租户过滤
	 */
	@Override
	public boolean ignoreTable(String tableName) {
		// 检查是否开启多租户
		if (!properties.isEnabled()) {
			return true;
		}

		// 检查是否在忽略列表中
		List<String> ignoreTables = properties.getIgnoreTables();
		if (ignoreTables != null && ignoreTables.contains(tableName)) {
			return true;
		}

		// 检查当前线程是否设置了租户ID
		return !TenantContextHolder.hasTenant();
	}

}
