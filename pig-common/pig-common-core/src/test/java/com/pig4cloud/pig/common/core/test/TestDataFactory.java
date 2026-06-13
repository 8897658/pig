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
package com.pig4cloud.pig.common.core.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试数据工厂
 * <p>
 * 提供测试用的数据构建方法
 *
 * @author lengleng
 * @date 2026-06-12
 */
public final class TestDataFactory {

	private TestDataFactory() {
		// 私有构造函数
	}

	/**
	 * 创建测试用户数据
	 */
	public static Map<String, Object> createTestUserData() {
		return createTestUserData("testuser", "test@example.com");
	}

	/**
	 * 创建测试用户数据（指定用户名）
	 */
	public static Map<String, Object> createTestUserData(String username, String email) {
		Map<String, Object> user = new HashMap<>();
		user.put("username", username);
		user.put("password", "123456");
		user.put("email", email);
		user.put("phone", "13800138000");
		user.put("delFlag", "0");
		user.put("lockFlag", "0");
		return user;
	}

	/**
	 * 创建测试角色数据
	 */
	public static Map<String, Object> createTestRoleData() {
		return createTestRoleData("ROLE_TEST", "测试角色");
	}

	/**
	 * 创建测试角色数据（指定名称）
	 */
	public static Map<String, Object> createTestRoleData(String roleName, String roleDesc) {
		Map<String, Object> role = new HashMap<>();
		role.put("roleName", roleName);
		role.put("roleCode", roleName);
		role.put("roleDesc", roleDesc);
		role.put("delFlag", "0");
		return role;
	}

	/**
	 * 创建测试菜单数据
	 */
	public static Map<String, Object> createTestMenuData() {
		return createTestMenuData("测试菜单", "/test");
	}

	/**
	 * 创建测试菜单数据（指定名称和路径）
	 */
	public static Map<String, Object> createTestMenuData(String name, String path) {
		Map<String, Object> menu = new HashMap<>();
		menu.put("name", name);
		menu.put("path", path);
		menu.put("component", "test/index");
		menu.put("menuType", "0");
		menu.put("delFlag", "0");
		return menu;
	}

}
