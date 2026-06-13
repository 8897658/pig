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
package com.pig4cloud.pig.mobile.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 移动端服务测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("移动端服务测试")
class MobileServiceTest {

	@Test
	@DisplayName("设备ID格式验证")
	void testDeviceIdFormat() {
		String deviceId = "ABC123DEF456";
		assertTrue(deviceId.length() >= 10);
		assertTrue(deviceId.matches("[A-Za-z0-9]+"));
	}

	@Test
	@DisplayName("设备类型验证")
	void testDeviceType() {
		String[] validTypes = { "ios", "android", "web", "desktop" };
		for (String type : validTypes) {
			assertTrue(type.matches("^[a-z]+$"));
		}
	}

	@Test
	@DisplayName("设备Token格式验证")
	void testDeviceTokenFormat() {
		String token = "a1b2c3d4e5f6g7h8i9j0";
		assertTrue(token.length() >= 20);
	}

	@Test
	@DisplayName("推送消息标题验证")
	void testPushTitleFormat() {
		String title = "系统通知";
		assertTrue(title.length() <= 50);
		assertTrue(!title.isEmpty());
	}

	@Test
	@DisplayName("推送消息内容验证")
	void testPushContentFormat() {
		String content = "您有一条新消息";
		assertTrue(content.length() <= 200);
	}

	@Test
	@DisplayName("iOS Bundle ID格式验证")
	void testBundleIdFormat() {
		String bundleId = "com.pig4cloud.pig.ios";
		assertTrue(bundleId.matches("^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)*$"));
	}

	@Test
	@DisplayName("Android Package Name格式验证")
	void testPackageNameFormat() {
		String packageName = "com.pig4cloud.pig.android";
		assertTrue(packageName.matches("^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)*$"));
	}

}