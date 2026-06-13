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

/**
 * 错误码接口
 * <p>
 * 所有错误码枚举应实现此接口，提供统一的错误码定义
 *
 * @author lengleng
 * @date 2026-06-13
 */
public interface ErrorCode {

	/**
	 * 获取错误码
	 * @return 错误码
	 */
	String getCode();

	/**
	 * 获取错误消息
	 * @return 错误消息
	 */
	String getMessage();

}