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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BizAssert 断言工具测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("业务断言工具测试")
class BizAssertTest {

	@Test
	@DisplayName("notNull - 对象为空时抛出异常")
	void testNotNullWithNull() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.notNull(null, CommonErrorCode.DATA_NOT_FOUND);
		});
		assertEquals(CommonErrorCode.DATA_NOT_FOUND.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("notNull - 对象不为空时正常通过")
	void testNotNullWithNotNull() {
		assertDoesNotThrow(() -> {
			BizAssert.notNull(new Object(), CommonErrorCode.DATA_NOT_FOUND);
		});
	}

	@Test
	@DisplayName("notEmpty - 字符串为空时抛出异常")
	void testNotEmptyStringWithEmpty() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.notEmpty("", CommonErrorCode.PARAM_ERROR);
		});
		assertEquals(CommonErrorCode.PARAM_ERROR.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("notEmpty - 集合为空时抛出异常")
	void testNotEmptyCollectionWithEmpty() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.notEmpty(Collections.emptyList(), CommonErrorCode.PARAM_ERROR);
		});
		assertEquals(CommonErrorCode.PARAM_ERROR.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("notEmpty - Map为空时抛出异常")
	void testNotEmptyMapWithEmpty() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.notEmpty(new HashMap<>(), CommonErrorCode.PARAM_ERROR);
		});
		assertEquals(CommonErrorCode.PARAM_ERROR.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("isTrue - 条件为假时抛出异常")
	void testIsTrueWithFalse() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.isTrue(false, CommonErrorCode.PERMISSION_DENIED);
		});
		assertEquals(CommonErrorCode.PERMISSION_DENIED.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("isFalse - 条件为真时抛出异常")
	void testIsFalseWithTrue() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.isFalse(true, CommonErrorCode.PERMISSION_DENIED);
		});
		assertEquals(CommonErrorCode.PERMISSION_DENIED.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("equals - 对象不相等时抛出异常")
	void testEqualsWithDifferent() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.equals("a", "b", CommonErrorCode.PARAM_ERROR);
		});
		assertEquals(CommonErrorCode.PARAM_ERROR.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("notEquals - 对象相等时抛出异常")
	void testNotEqualsWithSame() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.notEquals("a", "a", CommonErrorCode.DATA_EXISTS);
		});
		assertEquals(CommonErrorCode.DATA_EXISTS.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("fail - 直接抛出异常")
	void testFail() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.fail(CommonErrorCode.SYSTEM_ERROR);
		});
		assertEquals(CommonErrorCode.SYSTEM_ERROR.getCode(), ex.getCode());
	}

	@Test
	@DisplayName("fail - 带自定义消息抛出异常")
	void testFailWithMessage() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.fail(CommonErrorCode.USER_NOT_FOUND, "用户[test]不存在");
		});
		assertEquals(CommonErrorCode.USER_NOT_FOUND.getCode(), ex.getCode());
		assertEquals("用户[test]不存在", ex.getMessage());
	}

	@Test
	@DisplayName("notNull - 带自定义消息")
	void testNotNullWithMessage() {
		BizException ex = assertThrows(BizException.class, () -> {
			BizAssert.notNull(null, CommonErrorCode.DATA_NOT_FOUND, "数据[ID=123]不存在");
		});
		assertEquals(CommonErrorCode.DATA_NOT_FOUND.getCode(), ex.getCode());
		assertEquals("数据[ID=123]不存在", ex.getMessage());
	}

}
