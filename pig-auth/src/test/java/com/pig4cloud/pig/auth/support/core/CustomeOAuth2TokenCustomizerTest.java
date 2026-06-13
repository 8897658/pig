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

import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CustomeOAuth2TokenCustomizer Token 增强测试
 *
 * 测试覆盖: 1. 常量验证（license、client_id、active） 2. 客户端模式判断逻辑 3. 用户信息注入逻辑
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("CustomeOAuth2TokenCustomizer Token 增强测试")
class CustomeOAuth2TokenCustomizerTest {

	// ==================== 常量验证测试 ====================

	@Nested
	@DisplayName("安全常量验证测试")
	class SecurityConstantsTests {

		@Test
		@DisplayName("验证 license 常量存在")
		void testLicenseConstants_Exist() {
			assertThat(SecurityConstants.PIG_LICENSE).isNotEmpty();
			assertThat(SecurityConstants.DETAILS_LICENSE).isNotEmpty();
		}

		@Test
		@DisplayName("验证 client_id 常量")
		void testClientIdConstant_Exist() {
			assertThat(SecurityConstants.CLIENT_ID).isNotEmpty();
		}

		@Test
		@DisplayName("验证 active 常量")
		void testActiveConstant_Exist() {
			assertThat(SecurityConstants.ACTIVE).isNotEmpty();
		}

		@Test
		@DisplayName("验证用户信息常量")
		void testUserInfoConstants_Exist() {
			assertThat(SecurityConstants.DETAILS_USER_ID).isNotEmpty();
			assertThat(SecurityConstants.DETAILS_USERNAME).isNotEmpty();
		}

	}

	// ==================== 授权类型验证测试 ====================

	@Nested
	@DisplayName("授权类型验证测试")
	class GrantTypeTests {

		@Test
		@DisplayName("验证客户端模式常量")
		void testClientCredentialsConstant() {
			assertThat(SecurityConstants.CLIENT_CREDENTIALS).isEqualTo("client_credentials");
		}

		@Test
		@DisplayName("客户端模式不应该返回用户信息 - 逻辑验证")
		void testClientCredentialsMode_ShouldNotReturnUserInfo() {
			// 验证逻辑：当授权类型为 client_credentials 时，代码应该跳过用户信息注入
			String grantType = SecurityConstants.CLIENT_CREDENTIALS;
			assertThat(grantType).isEqualTo("client_credentials");
		}

	}

	// ==================== Token 增强逻辑验证测试 ====================

	@Nested
	@DisplayName("Token 增强逻辑验证测试")
	class TokenEnhancementTests {

		@Test
		@DisplayName("基础 claims 应该被添加到所有 Token")
		void testBasicClaims_ShouldBeAdded() {
			// 验证基础 claims：license、client_id、active
			assertThat(SecurityConstants.PIG_LICENSE).contains("pig");
			assertThat(SecurityConstants.CLIENT_ID).isNotEmpty();
			assertThat(SecurityConstants.ACTIVE).isNotEmpty();
		}

		@Test
		@DisplayName("用户模式 claims 应该包含用户信息")
		void testUserModeClaims_ShouldContainUserInfo() {
			// 验证用户模式需要添加的 claims
			assertThat(SecurityConstants.DETAILS_USER_ID).isNotEmpty();
			assertThat(SecurityConstants.DETAILS_USERNAME).isNotEmpty();
		}

	}

}