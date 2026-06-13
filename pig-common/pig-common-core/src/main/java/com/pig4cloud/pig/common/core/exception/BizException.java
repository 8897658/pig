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

import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常基类
 * <p>
 * 所有业务异常应继承此类，提供统一的异常处理机制
 *
 * @author lengleng
 * @date 2026-06-13
 */
@Getter
public class BizException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	private final String code;

	/**
	 * 错误消息
	 */
	private final String message;

	/**
	 * 构造业务异常
	 * @param errorCode 错误码枚举
	 */
	public BizException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	/**
	 * 构造业务异常（带自定义消息）
	 * @param errorCode 错误码枚举
	 * @param message 自定义错误消息
	 */
	public BizException(ErrorCode errorCode, String message) {
		super(message);
		this.code = errorCode.getCode();
		this.message = message;
	}

	/**
	 * 构造业务异常（带原因）
	 * @param errorCode 错误码枚举
	 * @param cause 原因
	 */
	public BizException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	/**
	 * 构造业务异常（带自定义消息和原因）
	 * @param errorCode 错误码枚举
	 * @param message 自定义错误消息
	 * @param cause 原因
	 */
	public BizException(ErrorCode errorCode, String message, Throwable cause) {
		super(message, cause);
		this.code = errorCode.getCode();
		this.message = message;
	}

}
