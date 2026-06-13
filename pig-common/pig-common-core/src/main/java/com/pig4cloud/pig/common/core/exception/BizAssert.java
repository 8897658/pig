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

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 业务断言工具类
 * <p>
 * 提供业务层面的断言方法，断言失败时抛出 BizException
 *
 * @author lengleng
 * @date 2026-06-13
 */
public final class BizAssert {

	private BizAssert() {
	}

	/**
	 * 断言对象不为空
	 * @param object 对象
	 * @param errorCode 错误码
	 */
	public static void notNull(Object object, ErrorCode errorCode) {
		if (object == null) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言对象不为空（带自定义消息）
	 * @param object 对象
	 * @param errorCode 错误码
	 * @param message 自定义消息
	 */
	public static void notNull(Object object, ErrorCode errorCode, String message) {
		if (object == null) {
			throw new BizException(errorCode, message);
		}
	}

	/**
	 * 断言字符串不为空
	 * @param str 字符串
	 * @param errorCode 错误码
	 */
	public static void notEmpty(String str, ErrorCode errorCode) {
		if (str == null || str.isEmpty()) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言集合不为空
	 * @param collection 集合
	 * @param errorCode 错误码
	 */
	public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
		if (collection == null || collection.isEmpty()) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言 Map 不为空
	 * @param map Map
	 * @param errorCode 错误码
	 */
	public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
		if (map == null || map.isEmpty()) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言条件为真
	 * @param condition 条件
	 * @param errorCode 错误码
	 */
	public static void isTrue(boolean condition, ErrorCode errorCode) {
		if (!condition) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言条件为假
	 * @param condition 条件
	 * @param errorCode 错误码
	 */
	public static void isFalse(boolean condition, ErrorCode errorCode) {
		if (condition) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言两个对象相等
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @param errorCode 错误码
	 */
	public static void equals(Object obj1, Object obj2, ErrorCode errorCode) {
		if (!Objects.equals(obj1, obj2)) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 断言两个对象不相等
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @param errorCode 错误码
	 */
	public static void notEquals(Object obj1, Object obj2, ErrorCode errorCode) {
		if (Objects.equals(obj1, obj2)) {
			throw new BizException(errorCode);
		}
	}

	/**
	 * 直接抛出业务异常
	 * @param errorCode 错误码
	 */
	public static void fail(ErrorCode errorCode) {
		throw new BizException(errorCode);
	}

	/**
	 * 直接抛出业务异常（带自定义消息）
	 * @param errorCode 错误码
	 * @param message 自定义消息
	 */
	public static void fail(ErrorCode errorCode, String message) {
		throw new BizException(errorCode, message);
	}

}