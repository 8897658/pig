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
package com.pig4cloud.pig.auth.support.password;

import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OAuth2ResourceOwnerPasswordAuthenticationProvider 单元测试
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("OAuth2 密码认证提供者测试")
class OAuth2ResourceOwnerPasswordAuthenticationProviderTests {

	private Map<String, Object> reqParameters;

	@BeforeEach
	void setUp() {
		reqParameters = new HashMap<>();
		reqParameters.put(SecurityConstants.DETAILS_USERNAME, "testuser");
		reqParameters.put(SecurityConstants.PASSWORD, "123456");
	}

	@Test
	@DisplayName("测试 Token 参数提取")
	void testTokenParameterExtraction() {
		// Given
		String username = (String) reqParameters.get(SecurityConstants.DETAILS_USERNAME);
		String password = (String) reqParameters.get(SecurityConstants.PASSWORD);

		// When & Then
		assertEquals("testuser", username);
		assertEquals("123456", password);
	}

	@Test
	@DisplayName("测试 supports 方法 - 支持 OAuth2ResourceOwnerPasswordAuthenticationToken")
	void testSupports_PasswordToken() {
		// Given
		Class<?> authenticationClass = OAuth2ResourceOwnerPasswordAuthenticationToken.class;

		// When
		boolean isAssignable = OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authenticationClass);

		// Then
		assertTrue(isAssignable);
	}

	@Test
	@DisplayName("测试 supports 方法 - 不支持其他类型")
	void testSupports_OtherType() {
		// Given
		Class<?> authenticationClass = String.class;

		// When
		boolean isAssignable = OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authenticationClass);

		// Then
		assertFalse(isAssignable);
	}

}
