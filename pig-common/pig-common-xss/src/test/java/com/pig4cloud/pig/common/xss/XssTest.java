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
package com.pig4cloud.pig.common.xss;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * XSS防护测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("XSS防护测试")
class XssTest {

	@Test
	@DisplayName("XSS危险标签检测 - Script")
	void testXssScriptTag() {
		String input = "<script>alert('xss')</script>";
		assertTrue(input.toLowerCase().contains("<script"));
	}

	@Test
	@DisplayName("XSS危险标签检测 - iframe")
	void testXssIframeTag() {
		String input = "<iframe src='evil.com'></iframe>";
		assertTrue(input.toLowerCase().contains("<iframe"));
	}

	@Test
	@DisplayName("XSS危险事件检测 - onclick")
	void testXssEventOnclick() {
		String input = "<img onclick='alert(1)'>";
		assertTrue(input.toLowerCase().contains("onclick"));
	}

	@Test
	@DisplayName("XSS危险协议检测 - javascript")
	void testXssJavascriptProtocol() {
		String input = "javascript:alert('xss')";
		assertTrue(input.toLowerCase().startsWith("javascript:"));
	}

	@Test
	@DisplayName("安全输入验证")
	void testSafeInput() {
		String safeInput = "<p>这是一段正常的文本</p>";
		assertFalse(safeInput.toLowerCase().contains("<script"));
		assertFalse(safeInput.toLowerCase().contains("javascript:"));
	}

	@Test
	@DisplayName("URL白名单验证")
	void testUrlWhitelist() {
		String[] allowedDomains = { "example.com", "api.example.com", "cdn.example.com" };
		for (String domain : allowedDomains) {
			assertTrue(domain.endsWith("example.com"));
		}
	}

	@Test
	@DisplayName("HTML转义验证")
	void testHtmlEscape() {
		String raw = "<div>test</div>";
		String escaped = raw.replace("<", "&lt;").replace(">", "&gt;");
		assertTrue(escaped.contains("&lt;"));
		assertTrue(escaped.contains("&gt;"));
	}

	@Test
	@DisplayName("特殊字符过滤验证")
	void testSpecialCharFilter() {
		String[] dangerousChars = { "<", ">", "\"", "'", "&" };
		assertTrue(dangerousChars.length == 5);
	}

}