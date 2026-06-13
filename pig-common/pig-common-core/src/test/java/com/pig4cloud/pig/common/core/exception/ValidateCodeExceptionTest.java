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
package com.pig4cloud.pig.common.core.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ValidateCodeException 测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("ValidateCodeException 测试")
class ValidateCodeExceptionTest {

	@Test
	@DisplayName("无参构造")
	void testNoArgsConstructor() {
		ValidateCodeException ex = new ValidateCodeException();
		assertNull(ex.getMessage());
	}

	@Test
	@DisplayName("带消息构造")
	void testMessageConstructor() {
		ValidateCodeException ex = new ValidateCodeException("验证码错误");
		assertEquals("验证码错误", ex.getMessage());
	}

}