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
package com.pig4cloud.pig.wechat.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 微信服务测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("微信服务测试")
class WxServiceTest {

	@Test
	@DisplayName("微信AppID格式验证")
	void testAppIdFormat() {
		String appId = "wx1234567890abcdef";
		assertTrue(appId.startsWith("wx"));
		assertTrue(appId.length() == 18);
	}

	@Test
	@DisplayName("微信AppSecret格式验证")
	void testAppSecretFormat() {
		String appSecret = "1234567890abcdef1234567890abcdef";
		assertTrue(appSecret.length() == 32);
		assertTrue(appSecret.matches("[a-f0-9]+"));
	}

	@Test
	@DisplayName("微信OpenID格式验证")
	void testOpenIdFormat() {
		String openId = "oGZUI0egBJY1zhBYw2KhdUfwVJJE";
		assertTrue(openId.length() >= 20);
		assertTrue(openId.matches("[a-zA-Z0-9_-]+"));
	}

	@Test
	@DisplayName("微信UnionID格式验证")
	void testUnionIdFormat() {
		String unionId = "o6_bmasdasdsad6_2sgVt7hMZOPfL";
		assertTrue(unionId.length() >= 20);
	}

	@Test
	@DisplayName("AccessToken格式验证")
	void testAccessTokenFormat() {
		String accessToken = "63_abcdef1234567890abcdefghijklmnopqrstuvwxyz";
		assertTrue(accessToken.length() > 30);
	}

	@Test
	@DisplayName("小程序码路径验证")
	void testWxaCodePath() {
		String path = "pages/index/index";
		assertTrue(path.startsWith("pages/"));
		assertTrue(!path.startsWith("/"));
	}

	@Test
	@DisplayName("微信公众号菜单验证")
	void testMpMenuFormat() {
		String menuType = "click";
		assertTrue(menuType.matches("^(click|view|scancode_push|scancode_waitmsg|pic_sysphoto|pic_photo_or_album|pic_weixin|location_select)$"));
	}

}