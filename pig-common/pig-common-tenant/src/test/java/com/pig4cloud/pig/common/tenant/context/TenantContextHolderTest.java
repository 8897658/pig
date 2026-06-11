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
package com.pig4cloud.pig.common.tenant.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TenantContextHolder 单元测试
 *
 * @author lengleng
 * @date 2026-06-11
 */
class TenantContextHolderTest {

	@BeforeEach
	void setUp() {
		TenantContextHolder.clear();
	}

	@AfterEach
	void tearDown() {
		TenantContextHolder.clear();
	}

	@Test
	void testSetAndGetTenantId() {
		String tenantId = "12345";
		TenantContextHolder.setTenantId(tenantId);

		assertEquals(tenantId, TenantContextHolder.getTenantId());
	}

	@Test
	void testGetTenantIdWhenNotSet() {
		// 未设置时应返回 null
		assertNull(TenantContextHolder.getTenantId());
	}

	@Test
	void testClear() {
		String tenantId = "99999";
		TenantContextHolder.setTenantId(tenantId);
		assertEquals(tenantId, TenantContextHolder.getTenantId());

		TenantContextHolder.clear();
		assertNull(TenantContextHolder.getTenantId());
	}

	@Test
	void testHasTenant() {
		// 未设置时返回false
		assertFalse(TenantContextHolder.hasTenant());

		// 设置后返回true
		TenantContextHolder.setTenantId("12345");
		assertTrue(TenantContextHolder.hasTenant());

		// 清除后返回false
		TenantContextHolder.clear();
		assertFalse(TenantContextHolder.hasTenant());
	}

	@Test
	void testNullTenantId() {
		// 设置null
		TenantContextHolder.setTenantId(null);
		assertNull(TenantContextHolder.getTenantId());
		assertFalse(TenantContextHolder.hasTenant());
	}

	@Test
	void testMultipleSetOperations() {
		// 连续设置多次
		TenantContextHolder.setTenantId("1");
		assertEquals("1", TenantContextHolder.getTenantId());

		TenantContextHolder.setTenantId("2");
		assertEquals("2", TenantContextHolder.getTenantId());

		TenantContextHolder.setTenantId("3");
		assertEquals("3", TenantContextHolder.getTenantId());
	}

	@Test
	void testEmptyStringTenantId() {
		TenantContextHolder.setTenantId("");
		assertEquals("", TenantContextHolder.getTenantId());
		assertTrue(TenantContextHolder.hasTenant());
	}

}
