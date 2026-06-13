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
package com.pig4cloud.pig.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SysOauthClientDetailsServiceImpl OAuth 客户端管理测试
 *
 * 测试覆盖:
 * 1. JSON 扩展字段处理逻辑
 * 2. 缓存常量验证
 * 3. 扩展字段常量验证
 *
 * @author lengleng
 * @date 2026-06-12
 */
@DisplayName("SysOauthClientDetailsServiceImpl OAuth 客户端管理测试")
class SysOauthClientDetailsServiceImplTest {

	// ==================== JSON 扩展字段处理测试 ====================

	@Nested
	@DisplayName("JSON 扩展字段处理测试")
	class JsonExtensionTests {

		@Test
		@DisplayName("扩展信息 JSON 应该包含 captchaFlag")
		void testExtensionJson_ShouldContainCaptchaFlag() {
			// Given
			String information = "{}";
			String captchaFlag = "1";

			// When
			String result = JSONUtil.parseObj(information)
				.set(CommonConstants.CAPTCHA_FLAG, captchaFlag)
				.toString();

			// Then
			assertThat(result).contains(CommonConstants.CAPTCHA_FLAG);
			assertThat(JSONUtil.parseObj(result).getStr(CommonConstants.CAPTCHA_FLAG)).isEqualTo(captchaFlag);
		}

		@Test
		@DisplayName("扩展信息 JSON 应该包含 encFlag")
		void testExtensionJson_ShouldContainEncFlag() {
			// Given
			String information = "{}";
			String encFlag = "1";

			// When
			String result = JSONUtil.parseObj(information)
				.set(CommonConstants.ENC_FLAG, encFlag)
				.toString();

			// Then
			assertThat(result).contains(CommonConstants.ENC_FLAG);
			assertThat(JSONUtil.parseObj(result).getStr(CommonConstants.ENC_FLAG)).isEqualTo(encFlag);
		}

		@Test
		@DisplayName("扩展信息 JSON 应该包含 onlineQuantity")
		void testExtensionJson_ShouldContainOnlineQuantity() {
			// Given
			String information = "{}";
			String onlineQuantity = "1";

			// When
			String result = JSONUtil.parseObj(information)
				.set(CommonConstants.ONLINE_QUANTITY, onlineQuantity)
				.toString();

			// Then
			assertThat(result).contains(CommonConstants.ONLINE_QUANTITY);
			assertThat(JSONUtil.parseObj(result).getStr(CommonConstants.ONLINE_QUANTITY)).isEqualTo(onlineQuantity);
		}

		@Test
		@DisplayName("扩展信息 JSON 应该正确合并所有字段")
		void testExtensionJson_ShouldMergeAllFields() {
			// Given
			String information = "{\"custom_field\":\"value\"}";
			String captchaFlag = "1";
			String encFlag = "0";
			String onlineQuantity = "1";

			// When
			String result = JSONUtil.parseObj(information)
				.set(CommonConstants.CAPTCHA_FLAG, captchaFlag)
				.set(CommonConstants.ENC_FLAG, encFlag)
				.set(CommonConstants.ONLINE_QUANTITY, onlineQuantity)
				.toString();

			// Then
			assertThat(result).contains(CommonConstants.CAPTCHA_FLAG);
			assertThat(result).contains(CommonConstants.ENC_FLAG);
			assertThat(result).contains(CommonConstants.ONLINE_QUANTITY);
			assertThat(result).contains("custom_field");
		}

		@Test
		@DisplayName("从 JSON 中提取扩展字段")
		void testExtractExtensionFields() {
			// Given
			String information = "{\"captcha_flag\":\"1\",\"enc_flag\":\"0\",\"online_quantity\":\"1\"}";

			// When
			String captchaFlag = JSONUtil.parseObj(information).getStr(CommonConstants.CAPTCHA_FLAG);
			String encFlag = JSONUtil.parseObj(information).getStr(CommonConstants.ENC_FLAG);
			String onlineQuantity = JSONUtil.parseObj(information).getStr(CommonConstants.ONLINE_QUANTITY);

			// Then
			assertThat(captchaFlag).isEqualTo("1");
			assertThat(encFlag).isEqualTo("0");
			assertThat(onlineQuantity).isEqualTo("1");
		}

	}

	// ==================== 缓存常量验证测试 ====================

	@Nested
	@DisplayName("缓存常量验证测试")
	class CacheConstantsTests {

		@Test
		@DisplayName("客户端详情缓存 key 常量")
		void testClientDetailsCacheKey() {
			assertThat(CacheConstants.CLIENT_DETAILS_KEY).isNotEmpty();
		}

	}

	// ==================== 扩展字段常量验证测试 ====================

	@Nested
	@DisplayName("扩展字段常量验证测试")
	class ExtensionConstantsTests {

		@Test
		@DisplayName("验证码开关常量")
		void testCaptchaFlagConstant() {
			assertThat(CommonConstants.CAPTCHA_FLAG).isNotEmpty();
		}

		@Test
		@DisplayName("加密开关常量")
		void testEncFlagConstant() {
			assertThat(CommonConstants.ENC_FLAG).isNotEmpty();
		}

		@Test
		@DisplayName("在线数量常量")
		void testOnlineQuantityConstant() {
			assertThat(CommonConstants.ONLINE_QUANTITY).isNotEmpty();
		}

	}

}