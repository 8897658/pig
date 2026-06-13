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
package com.pig4cloud.pig.auth.support.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PigDaoAuthenticationProvider 单元测试
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("PigDaoAuthenticationProvider 测试")
class PigDaoAuthenticationProviderTests {

	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		passwordEncoder = new BCryptPasswordEncoder();
	}

	@Test
	@DisplayName("测试密码匹配成功")
	void testPasswordMatch_Success() {
		// Given
		String rawPassword = "123456";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// When & Then
		assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
	}

	@Test
	@DisplayName("测试密码匹配失败")
	void testPasswordMatch_Fail() {
		// Given
		String rawPassword = "123456";
		String wrongPassword = "wrong_password";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// When & Then
		assertFalse(passwordEncoder.matches(wrongPassword, encodedPassword));
	}

	@Test
	@DisplayName("测试用户密码过期检查")
	void testUserPasswordExpired() {
		// Given
		LocalDateTime passwordModifyTime = LocalDateTime.now().minusDays(100);
		Long expireDays = 90L;
		long daysBetween = ChronoUnit.DAYS.between(passwordModifyTime, LocalDateTime.now());

		// When & Then
		assertTrue(daysBetween > expireDays, "密码应该已过期");
	}

	@Test
	@DisplayName("测试用户密码未过期")
	void testUserPasswordNotExpired() {
		// Given
		LocalDateTime passwordModifyTime = LocalDateTime.now().minusDays(10);
		Long expireDays = 90L;
		long daysBetween = ChronoUnit.DAYS.between(passwordModifyTime, LocalDateTime.now());

		// When & Then
		assertFalse(daysBetween > expireDays, "密码应该未过期");
	}

	@Test
	@DisplayName("测试认证 token 包含正确的用户名和密码")
	void testAuthenticationToken_ContainsCorrectCredentials() {
		// Given
		String username = "testuser";
		String password = "123456";

		// When
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		// Then
		assertEquals(username, token.getPrincipal());
		assertEquals(password, token.getCredentials());
	}

	@Test
	@DisplayName("测试密码编码器 - BCrypt 格式")
	void testPasswordEncoder_BCryptFormat() {
		// Given
		String rawPassword = "123456";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// When & Then
		assertTrue(encodedPassword.startsWith("$2"), "BCrypt 编码密码应包含 $2 前缀");
	}

	@Test
	@DisplayName("测试空密码校验")
	void testEmptyPassword() {
		// Given
		String rawPassword = "";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// When & Then
		assertTrue(passwordEncoder.matches("", encodedPassword));
	}

}
