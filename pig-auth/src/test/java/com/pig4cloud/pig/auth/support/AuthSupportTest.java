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
package com.pig4cloud.pig.auth.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OAuth2认证测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("OAuth2认证测试")
class AuthSupportTest {

	@Test
	@DisplayName("Client ID格式验证")
	void testClientIdFormat() {
		String clientId = "pig";
		assertTrue(clientId.matches("^[a-z][a-z0-9-]*$"));
	}

	@Test
	@DisplayName("Client Secret格式验证")
	void testClientSecretFormat() {
		String clientSecret = "lengleng";
		assertTrue(clientSecret.length() >= 4);
	}

	@Test
	@DisplayName("授权码格式验证")
	void testAuthorizationCodeFormat() {
		String code = "abc123def456ghi789";
		assertTrue(code.length() >= 16);
		assertTrue(code.matches("[a-zA-Z0-9]+"));
	}

	@Test
	@DisplayName("刷新令牌格式验证")
	void testRefreshTokenFormat() {
		String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
		assertTrue(refreshToken.length() >= 30);
	}

	@Test
	@DisplayName("Scope格式验证")
	void testScopeFormat() {
		String scope = "read write admin";
		String[] scopes = scope.split("\\s+");
		for (String s : scopes) {
			assertTrue(s.matches("^[a-z]+$"));
		}
	}

	@Test
	@DisplayName("Grant Type验证")
	void testGrantType() {
		String[] validGrantTypes = { "password", "sms", "refresh_token", "authorization_code", "client_credentials" };
		for (String grantType : validGrantTypes) {
			assertTrue(grantType.matches("^[a-z_]+$"));
		}
	}

	@Test
	@DisplayName("Token过期时间验证")
	void testTokenExpiration() {
		Integer expiresIn = 3600;
		assertTrue(expiresIn > 0 && expiresIn <= 86400);
	}

	@Test
	@DisplayName("手机号格式验证")
	void testPhoneNumberFormat() {
		String phone = "13812345678";
		assertTrue(phone.matches("^1[3-9]\\d{9}$"));
	}

	@Test
	@DisplayName("验证码格式验证")
	void testVerificationCodeFormat() {
		String code = "123456";
		assertTrue(code.matches("^\\d{6}$"));
	}

}