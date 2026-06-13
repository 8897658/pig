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

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

/**
 * 单元测试基类
 * <p>
 * 提供通用的测试配置和工具方法
 *
 * @author lengleng
 * @date 2026-06-12
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public abstract class AbstractUnitTest {

	/**
	 * 检查条件是否为真
	 */
	protected void assertTrue(boolean condition) {
		assert condition : "Assertion failed";
	}

	/**
	 * 检查条件是否为假
	 */
	protected void assertFalse(boolean condition) {
		assert !condition : "Assertion failed";
	}

	/**
	 * 检查对象不为空
	 */
	protected void assertNotNull(Object obj) {
		assert obj != null : "Object should not be null";
	}

	/**
	 * 检查对象为空
	 */
	protected void assertNull(Object obj) {
		assert obj == null : "Object should be null";
	}

	/**
	 * 检查两个对象相等
	 */
	protected void assertEquals(Object expected, Object actual) {
		assert (expected == null && actual == null) || (expected != null && expected.equals(actual))
				: "Expected: " + expected + ", Actual: " + actual;
	}

}
