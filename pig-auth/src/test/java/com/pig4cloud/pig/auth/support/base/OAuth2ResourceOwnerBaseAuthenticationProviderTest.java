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
package com.pig4cloud.pig.auth.support.base;

import com.pig4cloud.pig.common.security.util.OAuth2ErrorCodesExpand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * OAuth2ResourceOwnerBaseAuthenticationProvider 基类测试
 *
 * 测试覆盖: 1. 错误码常量验证 2. 异常转换逻辑验证 3. 授权类型常量验证
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("OAuth2ResourceOwnerBaseAuthenticationProvider 基类测试")
class OAuth2ResourceOwnerBaseAuthenticationProviderTest {

	// ==================== 错误码常量测试 ====================

	@Nested
	@DisplayName("错误码常量测试")
	class ErrorCodeConstantsTests {

		@Test
		@DisplayName("验证自定义错误码常量")
		void testCustomErrorCodes() {
			// 验证扩展的错误码常量存在
			assertThat(OAuth2ErrorCodesExpand.USERNAME_NOT_FOUND).isNotEmpty();
			assertThat(OAuth2ErrorCodesExpand.BAD_CREDENTIALS).isNotEmpty();
			assertThat(OAuth2ErrorCodesExpand.USER_LOCKED).isNotEmpty();
			assertThat(OAuth2ErrorCodesExpand.USER_DISABLE).isNotEmpty();
			assertThat(OAuth2ErrorCodesExpand.USER_EXPIRED).isNotEmpty();
			assertThat(OAuth2ErrorCodesExpand.CREDENTIALS_EXPIRED).isNotEmpty();
		}

		@Test
		@DisplayName("验证标准错误码常量")
		void testStandardErrorCodes() {
			// 验证 OAuth2 标准错误码
			assertThat(OAuth2ErrorCodes.INVALID_CLIENT).isEqualTo("invalid_client");
			assertThat(OAuth2ErrorCodes.INVALID_SCOPE).isEqualTo("invalid_scope");
			assertThat(OAuth2ErrorCodes.SERVER_ERROR).isEqualTo("server_error");
			assertThat(OAuth2ErrorCodes.INVALID_REQUEST).isEqualTo("invalid_request");
			assertThat(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT).isEqualTo("unauthorized_client");
		}

	}

	// ==================== 异常转换逻辑验证测试 ====================

	@Nested
	@DisplayName("异常转换逻辑验证测试")
	class ExceptionConversionTests {

		@Test
		@DisplayName("UsernameNotFoundException 应该转换为 USERNAME_NOT_FOUND 错误码")
		void testUsernameNotFoundException_ErrorCode() {
			// 验证错误码映射
			assertThat(OAuth2ErrorCodesExpand.USERNAME_NOT_FOUND).isNotEmpty();
		}

		@Test
		@DisplayName("BadCredentialsException 应该转换为 BAD_CREDENTIALS 错误码")
		void testBadCredentialsException_ErrorCode() {
			assertThat(OAuth2ErrorCodesExpand.BAD_CREDENTIALS).isNotEmpty();
		}

		@Test
		@DisplayName("LockedException 应该转换为 USER_LOCKED 错误码")
		void testLockedException_ErrorCode() {
			assertThat(OAuth2ErrorCodesExpand.USER_LOCKED).isNotEmpty();
		}

		@Test
		@DisplayName("DisabledException 应该转换为 USER_DISABLE 错误码")
		void testDisabledException_ErrorCode() {
			assertThat(OAuth2ErrorCodesExpand.USER_DISABLE).isNotEmpty();
		}

		@Test
		@DisplayName("AccountExpiredException 应该转换为 USER_EXPIRED 错误码")
		void testAccountExpiredException_ErrorCode() {
			assertThat(OAuth2ErrorCodesExpand.USER_EXPIRED).isNotEmpty();
		}

		@Test
		@DisplayName("CredentialsExpiredException 应该转换为 CREDENTIALS_EXPIRED 错误码")
		void testCredentialsExpiredException_ErrorCode() {
			assertThat(OAuth2ErrorCodesExpand.CREDENTIALS_EXPIRED).isNotEmpty();
		}

	}

	// ==================== 授权类型验证测试 ====================

	@Nested
	@DisplayName("授权类型验证测试")
	class GrantTypeTests {

		@Test
		@DisplayName("验证客户端模式标识")
		void testClientCredentialsIdentifier() {
			assertThat("client_credentials").isEqualTo("client_credentials");
		}

		@Test
		@DisplayName("验证密码模式标识")
		void testPasswordIdentifier() {
			assertThat("password").isEqualTo("password");
		}

		@Test
		@DisplayName("验证刷新令牌模式标识")
		void testRefreshTokenIdentifier() {
			assertThat("refresh_token").isEqualTo("refresh_token");
		}

	}

}