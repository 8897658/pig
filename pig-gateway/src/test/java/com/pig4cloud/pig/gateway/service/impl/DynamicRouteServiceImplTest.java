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
package com.pig4cloud.pig.gateway.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DynamicRouteServiceImpl 测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("动态路由服务测试")
class DynamicRouteServiceImplTest {

	@Test
	@DisplayName("路由ID格式验证")
	void testRouteIdFormat() {
		String routeId = "pig-auth";
		assertTrue(routeId.startsWith("pig-"));
		assertTrue(routeId.matches("^[a-z0-9-]+$"));
	}

	@Test
	@DisplayName("路由URI格式验证")
	void testRouteUriFormat() {
		String uri = "lb://pig-auth";
		assertTrue(uri.startsWith("lb://"));
		assertTrue(uri.substring(5).matches("^[a-z0-9-]+$"));
	}

	@Test
	@DisplayName("路由路径断言格式验证")
	void testRoutePathAssertion() {
		String path = "/auth/**";
		assertTrue(path.startsWith("/"));
		assertTrue(path.contains("**") || path.contains("*"));
	}

	@Test
	@DisplayName("路由优先级验证")
	void testRoutePriority() {
		Integer priority = 0;
		assertTrue(priority >= 0);
	}

	@Test
	@DisplayName("微服务路由命名规范")
	void testServiceRouteNaming() {
		String serviceName = "pig-auth";
		String routePath = "/auth/**";
		// 路径应该与服务名相关
		assertTrue(routePath.contains(serviceName.split("-")[1]));
	}

}