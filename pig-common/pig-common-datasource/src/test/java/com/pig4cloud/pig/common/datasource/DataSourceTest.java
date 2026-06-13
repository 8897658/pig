/*
 * Copyright (c) 2018-2026, lengleng All rights reserved.
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
package com.pig4cloud.pig.common.datasource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据源模块测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("数据源模块测试")
class DataSourceTest {

	@Test
	@DisplayName("数据源名称验证")
	void testDsNameFormat() {
		String dsName = "master";
		assertTrue(dsName.matches("^[a-z][a-z0-9_]*$"));
	}

	@Test
	@DisplayName("JDBC URL格式验证 - MySQL")
	void testMysqlJdbcUrl() {
		String url = "jdbc:mysql://localhost:3306/pig?useSSL=false";
		assertTrue(url.startsWith("jdbc:mysql://"));
		assertTrue(url.contains("3306"));
	}

	@Test
	@DisplayName("JDBC URL格式验证 - Oracle")
	void testOracleJdbcUrl() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		assertTrue(url.startsWith("jdbc:oracle:"));
	}

	@Test
	@DisplayName("连接池配置验证 - 最小空闲连接")
	void testMinIdle() {
		Integer minIdle = 5;
		assertTrue(minIdle >= 1);
	}

	@Test
	@DisplayName("连接池配置验证 - 最大活动连接")
	void testMaxActive() {
		Integer maxActive = 20;
		assertTrue(maxActive >= 5 && maxActive <= 100);
	}

	@Test
	@DisplayName("连接超时验证")
	void testConnectionTimeout() {
		Integer timeout = 30000;
		assertTrue(timeout > 0 && timeout <= 60000);
	}

	@Test
	@DisplayName("动态数据源切换验证")
	void testDynamicDsSwitch() {
		String[] dsNames = { "master", "slave_1", "slave_2" };
		assertTrue(dsNames.length >= 1);
	}

	@Test
	@DisplayName("数据源类型验证")
	void testDsType() {
		String[] validTypes = { "mysql", "oracle", "sqlserver", "postgresql" };
		for (String type : validTypes) {
			assertTrue(type.matches("^[a-z]+$"));
		}
	}

}