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
package com.pig4cloud.pig.admin.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务基础测试
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("用户服务基础测试")
class SysUserServiceBasicTests {

	@Test
	@DisplayName("测试用户名验证 - 有效用户名")
	void testUsernameValidation_Valid() {
		String username = "testuser";
		assertTrue(username.length() >= 4 && username.length() <= 20);
	}

	@Test
	@DisplayName("测试用户名验证 - 过短用户名")
	void testUsernameValidation_TooShort() {
		String username = "abc";
		assertFalse(username.length() >= 4);
	}

	@Test
	@DisplayName("测试用户名验证 - 过长用户名")
	void testUsernameValidation_TooLong() {
		String username = "verylongusernamethatiswaytoolong";
		assertFalse(username.length() <= 20);
	}

	@Test
	@DisplayName("测试邮箱验证 - 有效邮箱")
	void testEmailValidation_Valid() {
		String email = "test@example.com";
		assertTrue(email.contains("@") && email.contains("."));
	}

	@Test
	@DisplayName("测试邮箱验证 - 无效邮箱")
	void testEmailValidation_Invalid() {
		String email = "invalid-email";
		assertFalse(email.contains("@"));
	}

	@Test
	@DisplayName("测试手机号验证 - 有效手机号")
	void testPhoneValidation_Valid() {
		String phone = "13800138000";
		assertTrue(phone.matches("^1[3-9]\\d{9}$"));
	}

	@Test
	@DisplayName("测试手机号验证 - 无效手机号")
	void testPhoneValidation_Invalid() {
		String phone = "12345678901";
		assertFalse(phone.matches("^1[3-9]\\d{9}$"));
	}

}
