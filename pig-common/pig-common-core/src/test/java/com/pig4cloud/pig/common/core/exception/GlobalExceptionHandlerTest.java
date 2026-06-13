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

import com.pig4cloud.pig.common.core.util.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * GlobalExceptionHandler 单元测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("全局异常处理器测试")
class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	@DisplayName("处理 BizException")
	void testHandleBizException() {
		BizException ex = new BizException(CommonErrorCode.USER_NOT_FOUND, "用户[admin]不存在");
		R<Void> result = handler.handleBizException(ex);

		assertEquals(1, result.getCode());
		assertEquals("用户[admin]不存在", result.getMsg());
	}

	@Test
	@DisplayName("处理 BindException")
	void testHandleBindException() {
		BindException ex = new BindException(new Object(), "object");
		ex.addError(new FieldError("object", "field", "字段错误"));

		R<Void> result = handler.handleBindException(ex);

		assertEquals(1, result.getCode());
		assertEquals("字段错误", result.getMsg());
	}

	@Test
	@DisplayName("处理 ConstraintViolationException")
	void testHandleConstraintViolationException() {
		ConstraintViolation<?> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("参数不能为空");

		Set<ConstraintViolation<?>> violations = new HashSet<>();
		violations.add(violation);

		ConstraintViolationException ex = new ConstraintViolationException(violations);
		R<Void> result = handler.handleConstraintViolationException(ex);

		assertEquals(1, result.getCode());
		assertEquals("参数不能为空", result.getMsg());
	}

	@Test
	@DisplayName("处理未知 Exception")
	void testHandleException() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURI()).thenReturn("/api/test");

		R<Void> result = handler.handleException(request, new RuntimeException("测试异常"));

		assertEquals(1, result.getCode());
		assertEquals(CommonErrorCode.SYSTEM_ERROR.getMessage(), result.getMsg());
	}

}
