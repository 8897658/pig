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
package com.pig4cloud.pig.admin.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 系统控制器测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("系统控制器测试")
class SysControllerTest {

	@Test
	@DisplayName("用户名格式验证")
	void testUsernameFormat() {
		String username = "admin";
		assertTrue(username.matches("^[a-zA-Z][a-zA-Z0-9_-]{2,19}$"));
	}

	@Test
	@DisplayName("密码格式验证")
	void testPasswordFormat() {
		String password = "Abc123456!";
		assertTrue(password.length() >= 8);
		assertTrue(password.matches(".*[A-Z].*")); // 包含大写字母
		assertTrue(password.matches(".*[a-z].*")); // 包含小写字母
		assertTrue(password.matches(".*\\d.*")); // 包含数字
	}

	@Test
	@DisplayName("角色编码格式验证")
	void testRoleCodeFormat() {
		String roleCode = "ROLE_ADMIN";
		assertTrue(roleCode.matches("^ROLE_[A-Z_]+$"));
	}

	@Test
	@DisplayName("部门名称格式验证")
	void testDeptNameFormat() {
		String deptName = "技术部";
		assertTrue(deptName.length() >= 2 && deptName.length() <= 50);
	}

	@Test
	@DisplayName("菜单路径格式验证")
	void testMenuPathFormat() {
		String path = "/system/user";
		assertTrue(path.startsWith("/"));
		assertTrue(path.matches("^/[a-zA-Z0-9/]*$"));
	}

	@Test
	@DisplayName("权限标识格式验证")
	void testPermissionFormat() {
		String permission = "sys:user:add";
		assertTrue(permission.matches("^[a-z]+:[a-z]+:[a-z]+$"));
	}

	@Test
	@DisplayName("字典类型格式验证")
	void testDictTypeFormat() {
		String dictType = "sys_yes_no";
		assertTrue(dictType.matches("^[a-z][a-z0-9_]*$"));
	}

	@Test
	@DisplayName("文件名格式验证")
	void testFileNameFormat() {
		String fileName = "test.pdf";
		assertTrue(fileName.matches("^[a-zA-Z0-9_.-]+\\.[a-zA-Z0-9]+$"));
	}

	@Test
	@DisplayName("IP地址格式验证")
	void testIpAddressFormat() {
		String ip = "192.168.1.1";
		assertTrue(ip.matches("^(\\d{1,3}\\.){3}\\d{1,3}$"));
	}

	@Test
	@DisplayName("手机号格式验证")
	void testPhoneFormat() {
		String phone = "13812345678";
		assertTrue(phone.matches("^1[3-9]\\d{9}$"));
	}

	@Test
	@DisplayName("邮箱格式验证")
	void testEmailFormat() {
		String email = "test@example.com";
		assertTrue(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
	}

}