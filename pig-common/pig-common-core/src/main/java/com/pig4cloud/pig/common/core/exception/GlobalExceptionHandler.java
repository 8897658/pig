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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * <p>
 * 统一处理各类异常，返回标准响应格式
 *
 * @author lengleng
 * @date 2026-06-13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理业务异常
	 * @param e 业务异常
	 * @return 错误响应
	 */
	@ExceptionHandler(BizException.class)
	public R<Void> handleBizException(BizException e) {
		log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
		return R.failed(e.getCode(), e.getMessage());
	}

	/**
	 * 处理参数校验异常（@RequestBody）
	 * @param e 校验异常
	 * @return 错误响应
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(", "));
		log.warn("参数校验失败: {}", message);
		return R.failed(CommonErrorCode.PARAM_ERROR.getCode(), message);
	}

	/**
	 * 处理参数绑定异常
	 * @param e 绑定异常
	 * @return 错误响应
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R<Void> handleBindException(BindException e) {
		String message = e.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.joining(", "));
		log.warn("参数绑定失败: {}", message);
		return R.failed(CommonErrorCode.PARAM_ERROR.getCode(), message);
	}

	/**
	 * 处理约束违反异常（@RequestParam）
	 * @param e 约束违反异常
	 * @return 错误响应
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
		String message = e.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.joining(", "));
		log.warn("参数约束违反: {}", message);
		return R.failed(CommonErrorCode.PARAM_ERROR.getCode(), message);
	}

	/**
	 * 处理其他未知异常
	 * @param request 请求
	 * @param e 异常
	 * @return 错误响应
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R<Void> handleException(HttpServletRequest request, Exception e) {
		log.error("系统异常: uri={}, message={}", request.getRequestURI(), e.getMessage(), e);
		return R.failed(CommonErrorCode.SYSTEM_ERROR.getCode(), CommonErrorCode.SYSTEM_ERROR.getMessage());
	}

}
