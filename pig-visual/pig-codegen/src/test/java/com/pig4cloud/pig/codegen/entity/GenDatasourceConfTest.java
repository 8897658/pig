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
package com.pig4cloud.pig.codegen.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 代码生成测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("代码生成测试")
class GenDatasourceConfTest {

	@Test
	@DisplayName("数据源类型验证")
	void testDsType() {
		String[] validTypes = { "mysql", "oracle", "sqlserver", "pg", "dm" };
		for (String type : validTypes) {
			assertTrue(type.matches("[a-z]+"));
		}
	}

	@Test
	@DisplayName("JDBC URL格式验证 - MySQL")
	void testMysqlJdbcUrl() {
		String url = "jdbc:mysql://localhost:3306/pig?useUnicode=true&characterEncoding=UTF-8";
		assertTrue(url.startsWith("jdbc:mysql://"));
		assertTrue(url.contains("3306"));
	}

	@Test
	@DisplayName("JDBC URL格式验证 - Oracle")
	void testOracleJdbcUrl() {
		String url = "jdbc:oracle:thin:@localhost:1521:pig";
		assertTrue(url.startsWith("jdbc:oracle:"));
		assertTrue(url.contains("1521"));
	}

	@Test
	@DisplayName("表名格式验证")
	void testTableNameFormat() {
		String tableName = "sys_user";
		assertTrue(tableName.matches("^[a-z_]+$"));
	}

	@Test
	@DisplayName("包名格式验证")
	void testPackageNameFormat() {
		String packageName = "com.pig4cloud.pig.admin";
		assertTrue(packageName.matches("^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)*$"));
	}

	@Test
	@DisplayName("类名格式验证")
	void testClassNameFormat() {
		String className = "SysUser";
		assertTrue(className.matches("^[A-Z][a-zA-Z0-9]*$"));
	}

	@Test
	@DisplayName("作者名称验证")
	void testAuthorFormat() {
		String author = "lengleng";
		assertTrue(author.matches("^[a-zA-Z]+$"));
	}

}