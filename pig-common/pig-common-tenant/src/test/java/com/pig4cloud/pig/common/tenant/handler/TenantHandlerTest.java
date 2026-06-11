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
package com.pig4cloud.pig.common.tenant.handler;

import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import com.pig4cloud.pig.common.tenant.properties.TenantProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TenantHandler 单元测试
 *
 * @author lengleng
 * @date 2026-06-11
 */
class TenantHandlerTest {

	private TenantProperties properties;

	private TenantHandler tenantHandler;

	@BeforeEach
	void setUp() {
		properties = new TenantProperties();
		properties.setEnabled(true);
		properties.setColumn("tenant_id");
		properties.setIgnoreTables(Arrays.asList("sys_tenant", "sys_dict"));

		tenantHandler = new TenantHandler(properties);
		TenantContextHolder.clear();
	}

	@AfterEach
	void tearDown() {
		TenantContextHolder.clear();
	}

	@Test
	void testGetTenantIdWithValue() {
		String tenantId = "12345";
		TenantContextHolder.setTenantId(tenantId);

		Expression expression = tenantHandler.getTenantId();

		assertInstanceOf(StringValue.class, expression);
		assertEquals(tenantId, ((StringValue) expression).getValue());
	}

	@Test
	void testGetTenantIdWhenNotSet() {
		// 未设置租户ID时返回 NullValue
		Expression expression = tenantHandler.getTenantId();

		assertInstanceOf(NullValue.class, expression);
	}

	@Test
	void testGetTenantIdColumn() {
		assertEquals("tenant_id", tenantHandler.getTenantIdColumn());
	}

	@Test
	void testIgnoreTableWhenDisabled() {
		properties.setEnabled(false);

		// 禁用时应忽略所有表
		assertTrue(tenantHandler.ignoreTable("sys_user"));
		assertTrue(tenantHandler.ignoreTable("sys_role"));
	}

	@Test
	void testIgnoreTableWhenNoTenantSet() {
		// 未设置租户时忽略所有表
		assertTrue(tenantHandler.ignoreTable("sys_user"));
	}

	@Test
	void testIgnoreTableInIgnoreList() {
		TenantContextHolder.setTenantId("12345");

		// 在忽略列表中的表应被忽略
		assertTrue(tenantHandler.ignoreTable("sys_tenant"));
		assertTrue(tenantHandler.ignoreTable("sys_dict"));
	}

	@Test
	void testIgnoreTableNotInIgnoreList() {
		TenantContextHolder.setTenantId("12345");

		// 不在忽略列表中的表不应被忽略
		assertFalse(tenantHandler.ignoreTable("sys_user"));
		assertFalse(tenantHandler.ignoreTable("sys_role"));
	}

}
