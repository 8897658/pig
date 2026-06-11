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
package com.pig4cloud.pig.common.datapermission.handler;

import com.pig4cloud.pig.common.datapermission.properties.DataPermissionProperties;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DataPermissionHandler 单元测试
 *
 * @author lengleng
 * @date 2026-06-11
 */
class DataPermissionHandlerTest {

	private DataPermissionProperties properties;

	private DataPermissionHandler handler;

	@BeforeEach
	void setUp() {
		properties = new DataPermissionProperties();
		properties.setEnabled(true);
		properties.setDeptIdColumn("dept_id");
		properties.setIgnoreTables(Collections.singletonList("sys_log"));

		handler = new DataPermissionHandler(properties);
	}

	@AfterEach
	void tearDown() {
		DataPermissionHandler.clearContext();
	}

	@Test
	void testHandlerCreation() {
		assertNotNull(handler);
	}

	@Test
	void testPropertiesAreLoaded() {
		assertNotNull(properties);
		assertTrue(properties.isEnabled());
		assertEquals("dept_id", properties.getDeptIdColumn());
	}

	@Test
	void testSetContext() {
		Set<Long> deptIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));
		DataPermissionHandler.setContext(deptIds, false);

		DataPermissionHandler.DataPermissionContext context = DataPermissionHandler.getContext();
		assertNotNull(context);
		assertEquals(3, context.getDeptIds().size());
		assertFalse(context.isSuperAdmin());
	}

	@Test
	void testClearContext() {
		Set<Long> deptIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));
		DataPermissionHandler.setContext(deptIds, false);

		assertNotNull(DataPermissionHandler.getContext());

		DataPermissionHandler.clearContext();
		assertNull(DataPermissionHandler.getContext());
	}

	@Test
	void testSuperAdminContext() {
		Set<Long> deptIds = new HashSet<>(Arrays.asList(1L));
		DataPermissionHandler.setContext(deptIds, true);

		DataPermissionHandler.DataPermissionContext context = DataPermissionHandler.getContext();
		assertTrue(context.isSuperAdmin());
	}

	@Test
	void testGetSqlSegmentWhenDisabled() {
		properties.setEnabled(false);

		Table table = new Table("sys_user");
		Expression result = handler.getSqlSegment(table, null, "testMapper.selectById");

		assertNull(result);
	}

	@Test
	void testGetSqlSegmentWhenNoContext() {
		Table table = new Table("sys_user");
		Expression result = handler.getSqlSegment(table, null, "testMapper.selectById");

		assertNull(result);
	}

	@Test
	void testGetSqlSegmentWhenSuperAdmin() {
		Set<Long> deptIds = new HashSet<>(Arrays.asList(1L));
		DataPermissionHandler.setContext(deptIds, true);

		Table table = new Table("sys_user");
		Expression result = handler.getSqlSegment(table, null, "testMapper.selectById");

		assertNull(result);
	}

	@Test
	void testGetSqlSegmentWhenIgnoreTable() {
		Set<Long> deptIds = new HashSet<>(Arrays.asList(1L));
		DataPermissionHandler.setContext(deptIds, false);

		Table table = new Table("sys_log");
		Expression result = handler.getSqlSegment(table, null, "testMapper.selectById");

		assertNull(result);
	}

	@Test
	void testGetSqlSegmentWithSingleDeptId() {
		Set<Long> deptIds = new HashSet<>(Collections.singletonList(1L));
		DataPermissionHandler.setContext(deptIds, false);

		Table table = new Table("sys_user");
		Expression result = handler.getSqlSegment(table, null, "testMapper.selectById");

		assertNotNull(result);
		assertInstanceOf(EqualsTo.class, result);
	}

	@Test
	void testGetSqlSegmentWithMultipleDeptIds() {
		Set<Long> deptIds = new HashSet<>(Arrays.asList(1L, 2L, 3L));
		DataPermissionHandler.setContext(deptIds, false);

		Table table = new Table("sys_user");
		Expression result = handler.getSqlSegment(table, null, "testMapper.selectById");

		assertNotNull(result);
		assertInstanceOf(InExpression.class, result);
	}

	@Test
	void testGetSqlSegmentWithExistingWhere() {
		Set<Long> deptIds = new HashSet<>(Collections.singletonList(1L));
		DataPermissionHandler.setContext(deptIds, false);

		Table table = new Table("sys_user");
		Expression existingWhere = new EqualsTo();
		Expression result = handler.getSqlSegment(table, existingWhere, "testMapper.selectById");

		assertNotNull(result);
	}

	@Test
	void testIgnoreTablesConfiguration() {
		assertEquals(1, properties.getIgnoreTables().size());
		assertTrue(properties.getIgnoreTables().contains("sys_log"));
	}

}
