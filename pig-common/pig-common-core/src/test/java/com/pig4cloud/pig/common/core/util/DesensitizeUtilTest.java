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
package com.pig4cloud.pig.common.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DesensitizeUtil 测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("数据脱敏工具测试")
class DesensitizeUtilTest {

	@Test
	@DisplayName("手机号脱敏")
	void testPhone() {
		assertEquals("138****5678", DesensitizeUtil.phone("13812345678"));
		assertEquals("138****6789", DesensitizeUtil.phone("138123456789")); // 12位
		assertEquals("123", DesensitizeUtil.phone("123"));
		assertEquals("", DesensitizeUtil.phone(""));
		assertEquals(null, DesensitizeUtil.phone(null));
	}

	@Test
	@DisplayName("邮箱脱敏")
	void testEmail() {
		assertEquals("tes***@example.com", DesensitizeUtil.email("test@example.com"));
		assertEquals("abc***@test.org", DesensitizeUtil.email("abcdef@test.org"));
		assertEquals("ab@test.com", DesensitizeUtil.email("ab@test.com"));
		assertEquals("invalid", DesensitizeUtil.email("invalid"));
		assertEquals(null, DesensitizeUtil.email(null));
	}

	@Test
	@DisplayName("身份证号脱敏")
	void testIdCard() {
		assertEquals("110101********1234", DesensitizeUtil.idCard("110101199001011234"));
		assertEquals("123456********7890", DesensitizeUtil.idCard("1234567890")); // 10位
		assertEquals("123", DesensitizeUtil.idCard("123"));
		assertEquals(null, DesensitizeUtil.idCard(null));
	}

	@Test
	@DisplayName("银行卡号脱敏")
	void testBankCard() {
		assertEquals("6222********7890", DesensitizeUtil.bankCard("6222021234567890")); // 16位
		assertEquals("12345678", DesensitizeUtil.bankCard("12345678")); // 8位，middleLength=0
		assertEquals("1234567", DesensitizeUtil.bankCard("1234567"));
		assertEquals(null, DesensitizeUtil.bankCard(null));
	}

	@Test
	@DisplayName("姓名脱敏")
	void testName() {
		assertEquals("张*", DesensitizeUtil.name("张三"));
		assertEquals("张**", DesensitizeUtil.name("张三四"));
		assertEquals("李", DesensitizeUtil.name("李")); // 单字不处理
		assertEquals(null, DesensitizeUtil.name(null));
		assertEquals("", DesensitizeUtil.name(""));
	}

	@Test
	@DisplayName("地址脱敏")
	void testAddress() {
		assertEquals("北京市朝阳区某某街道***", DesensitizeUtil.address("北京市朝阳区某某街道某某号"));
		assertEquals("北京市朝阳区", DesensitizeUtil.address("北京市朝阳区")); // 6位，不足10
		assertEquals(null, DesensitizeUtil.address(null));
	}

	@Test
	@DisplayName("密码脱敏")
	void testPassword() {
		assertEquals("****", DesensitizeUtil.password("123456"));
		assertEquals("****", DesensitizeUtil.password("abcdefg"));
		assertEquals("****", DesensitizeUtil.password(null));
		assertEquals("****", DesensitizeUtil.password(""));
	}

	@Test
	@DisplayName("自定义脱敏")
	void testCustom() {
		assertEquals("abc*****fgh", DesensitizeUtil.custom("abcdefghfgh", 3, 3)); // 11位，前3后3，中间5个星号
		assertEquals("abcdef", DesensitizeUtil.custom("abcdef", 3, 3)); // 刚好等于
		assertEquals(null, DesensitizeUtil.custom(null, 3, 3));
		assertEquals("ab****yz", DesensitizeUtil.custom("abcdefyz", 2, 2)); // 8位，前2后2，中间4个星号
	}

}