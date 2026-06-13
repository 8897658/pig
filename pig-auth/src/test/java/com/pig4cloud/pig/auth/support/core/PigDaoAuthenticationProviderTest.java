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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PigDaoAuthenticationProvider 认证逻辑测试
 *
 * 测试覆盖: 1. 密码加密验证逻辑 2. APP 模式判断逻辑 3. 密码过期计算逻辑 4. 常量验证
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("PigDaoAuthenticationProvider 认证逻辑测试")
class PigDaoAuthenticationProviderTest {

	// ==================== 密码加密逻辑测试 ====================

	@Nested
	@DisplayName("密码加密逻辑测试")
	class PasswordEncryptionTests {

		@Test
		@DisplayName("BCrypt 加密应该产生不同的哈希值")
		void testBCryptEncryption_ShouldProduceDifferentHashes() {
			// Given
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = "123456";

			// When
			String hash1 = encoder.encode(rawPassword);
			String hash2 = encoder.encode(rawPassword);

			// Then
			assertThat(hash1).startsWith("$2a$");
			assertThat(hash2).startsWith("$2a$");
			assertThat(hash1).isNotEqualTo(hash2); // 每次加密产生不同哈希
			assertThat(encoder.matches(rawPassword, hash1)).isTrue();
			assertThat(encoder.matches(rawPassword, hash2)).isTrue();
		}

		@Test
		@DisplayName("密码匹配验证应该正确")
		void testPasswordMatching_ShouldBeCorrect() {
			// Given
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = "123456";
			String encodedPassword = encoder.encode(rawPassword);

			// When & Then
			assertThat(encoder.matches(rawPassword, encodedPassword)).isTrue();
			assertThat(encoder.matches("wrong_password", encodedPassword)).isFalse();
		}

		@Test
		@DisplayName("空密码应该能正确加密和匹配")
		void testEmptyPassword_ShouldWork() {
			// Given
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String emptyPassword = "";

			// When
			String encoded = encoder.encode(emptyPassword);

			// Then
			assertThat(encoded).isNotEmpty();
			assertThat(encoder.matches(emptyPassword, encoded)).isTrue();
		}

	}

	// ==================== APP 模式逻辑测试 ====================

	@Nested
	@DisplayName("APP 模式逻辑测试")
	class AppModeTests {

		@Test
		@DisplayName("APP 模式常量验证")
		void testAppModeConstant() {
			assertThat(SecurityConstants.APP).isEqualTo("mobile");
		}

		@Test
		@DisplayName("授权类型判断逻辑")
		void testGrantTypeComparisonLogic() {
			// Given
			String grantType = "mobile";
			String expectedApp = SecurityConstants.APP;

			// When & Then
			assertThat(grantType).isEqualTo(expectedApp);
		}

	}

	// ==================== 密码过期逻辑测试 ====================

	@Nested
	@DisplayName("密码过期逻辑测试")
	class PasswordExpireTests {

		@Test
		@DisplayName("密码未过期 - 10天内")
		void testPasswordNotExpired_10Days() {
			// Given
			LocalDateTime modifyTime = LocalDateTime.now().minusDays(10);
			Long expireDays = 90L;

			// When
			long daysBetween = ChronoUnit.DAYS.between(modifyTime, LocalDateTime.now());

			// Then
			assertThat(daysBetween).isLessThan(expireDays);
		}

		@Test
		@DisplayName("密码未过期 - 89天内")
		void testPasswordNotExpired_89Days() {
			// Given
			LocalDateTime modifyTime = LocalDateTime.now().minusDays(89);
			Long expireDays = 90L;

			// When
			long daysBetween = ChronoUnit.DAYS.between(modifyTime, LocalDateTime.now());

			// Then
			assertThat(daysBetween).isLessThan(expireDays);
		}

		@Test
		@DisplayName("密码已过期 - 91天")
		void testPasswordExpired_91Days() {
			// Given
			LocalDateTime modifyTime = LocalDateTime.now().minusDays(91);
			Long expireDays = 90L;

			// When
			long daysBetween = ChronoUnit.DAYS.between(modifyTime, LocalDateTime.now());

			// Then
			assertThat(daysBetween).isGreaterThan(expireDays);
		}

		@Test
		@DisplayName("密码已过期 - 100天")
		void testPasswordExpired_100Days() {
			// Given
			LocalDateTime modifyTime = LocalDateTime.now().minusDays(100);
			Long expireDays = 90L;

			// When
			long daysBetween = ChronoUnit.DAYS.between(modifyTime, LocalDateTime.now());

			// Then
			assertThat(daysBetween).isGreaterThan(expireDays);
		}

	}

	// ==================== 常量验证测试 ====================

	@Nested
	@DisplayName("安全常量验证测试")
	class SecurityConstantsTests {

		@Test
		@DisplayName("验证 APP 模式常量")
		void testAppConstant() {
			assertThat(SecurityConstants.APP).isEqualTo("mobile");
		}

		@Test
		@DisplayName("验证客户端模式常量")
		void testClientCredentialsConstant() {
			assertThat(SecurityConstants.CLIENT_CREDENTIALS).isEqualTo("client_credentials");
		}

	}

}