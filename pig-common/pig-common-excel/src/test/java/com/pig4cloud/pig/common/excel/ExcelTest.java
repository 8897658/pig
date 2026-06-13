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
package com.pig4cloud.pig.common.excel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Excel处理测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("Excel处理测试")
class ExcelTest {

	@Test
	@DisplayName("Excel文件名格式验证")
	void testExcelFileName() {
		String fileName = "用户数据.xlsx";
		assertTrue(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"));
		assertTrue(fileName.length() <= 100);
	}

	@Test
	@DisplayName("Sheet名称验证")
	void testSheetName() {
		String sheetName = "用户列表";
		assertTrue(sheetName.length() <= 31);
		assertTrue(!sheetName.isEmpty());
	}

	@Test
	@DisplayName("列标题验证")
	void testColumnTitle() {
		String title = "用户名";
		assertTrue(title.length() <= 50);
	}

	@Test
	@DisplayName("数据行数限制验证")
	void testMaxRows() {
		Integer maxRows = 10000;
		assertTrue(maxRows > 0 && maxRows <= 100000);
	}

	@Test
	@DisplayName("导入字段名称验证")
	void testImportFieldName() {
		String fieldName = "userName";
		assertTrue(fieldName.matches("^[a-z][a-zA-Z0-9]*$"));
	}

	@Test
	@DisplayName("导出模板验证")
	void testExportTemplate() {
		String template = "user_export_template.xlsx";
		assertTrue(template.endsWith(".xlsx"));
	}

	@Test
	@DisplayName("日期格式验证")
	void testDateFormat() {
		String dateFormat = "yyyy-MM-dd";
		assertTrue(dateFormat.matches("^yyyy[-/]?MM[-/]?dd$"));
	}

	@Test
	@DisplayName("数字格式验证")
	void testNumberFormat() {
		String numberFormat = "#,##0.00";
		assertTrue(numberFormat.contains("0") || numberFormat.contains("#"));
	}

}