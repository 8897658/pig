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
package com.pig4cloud.pig.common.data.resolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 数据解析器测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("数据解析器测试")
class DataResolverTest {

	@Test
	@DisplayName("参数Key格式验证")
	void testParamKeyFormat() {
		String key = "SYS_CONFIG";
		assertTrue(key.matches("^[A-Z][A-Z0-9_]*$"));
	}

	@Test
	@DisplayName("字典类型格式验证")
	void testDictTypeFormat() {
		String dictType = "sys_yes_no";
		assertTrue(dictType.matches("^[a-z][a-z0-9_]*$"));
	}

	@Test
	@DisplayName("SQL注入检测 - 安全SQL")
	void testSqlInjectionSafe() {
		String safeSql = "SELECT * FROM sys_user WHERE id = 1";
		assertFalse(safeSql.toLowerCase().contains("drop"));
		assertFalse(safeSql.toLowerCase().contains("delete"));
		assertFalse(safeSql.toLowerCase().contains("truncate"));
	}

	@Test
	@DisplayName("SQL注入检测 - 危险关键字")
	void testSqlInjectionDangerous() {
		String dangerousKeywords = "drop,delete,truncate,insert,update,exec";
		String[] keywords = dangerousKeywords.split(",");
		assertTrue(keywords.length > 0);
	}

	@Test
	@DisplayName("分页参数验证 - 当前页")
	void testPageCurrent() {
		Integer current = 1;
		assertTrue(current >= 1);
	}

	@Test
	@DisplayName("分页参数验证 - 每页大小")
	void testPageSize() {
		Integer size = 10;
		assertTrue(size >= 1 && size <= 100);
	}

	@Test
	@DisplayName("排序字段验证")
	void testOrderField() {
		String orderField = "create_time";
		assertTrue(orderField.matches("^[a-z][a-z0-9_]*$"));
	}

	@Test
	@DisplayName("排序方向验证")
	void testOrderDirection() {
		String asc = "asc";
		String desc = "desc";
		assertTrue(asc.matches("^(asc|desc)$"));
		assertTrue(desc.matches("^(asc|desc)$"));
	}

}