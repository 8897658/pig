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
 * R 响应类测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("响应类测试")
class RTest {

	@Test
	@DisplayName("成功响应 - 无数据")
	void testOkNoData() {
		R<Void> r = R.ok();
		assertEquals(0, r.getCode());
		assertNull(r.getMsg());
		assertNull(r.getData());
		assertTrue(r.isOk());
	}

	@Test
	@DisplayName("成功响应 - 有数据")
	void testOkWithData() {
		R<String> r = R.ok("test data");
		assertEquals(0, r.getCode());
		assertNull(r.getMsg());
		assertEquals("test data", r.getData());
		assertTrue(r.isOk());
	}

	@Test
	@DisplayName("成功响应 - 数据和消息")
	void testOkWithDataAndMsg() {
		R<String> r = R.ok("test data", "操作成功");
		assertEquals(0, r.getCode());
		assertEquals("操作成功", r.getMsg());
		assertEquals("test data", r.getData());
	}

	@Test
	@DisplayName("失败响应 - 无消息")
	void testFailedNoMsg() {
		R<Void> r = R.failed();
		assertEquals(1, r.getCode());
		assertNull(r.getMsg());
		assertFalse(r.isOk());
	}

	@Test
	@DisplayName("失败响应 - 有消息")
	void testFailedWithMsg() {
		R<Void> r = R.failed("操作失败");
		assertEquals(1, r.getCode());
		assertEquals("操作失败", r.getMsg());
		assertFalse(r.isOk());
	}

	@Test
	@DisplayName("失败响应 - 错误码和消息")
	void testFailedWithCodeAndMsg() {
		R<Void> r = R.failed("100001", "系统异常");
		assertEquals(1, r.getCode());
		assertEquals("系统异常", r.getMsg());
		assertEquals("100001", r.getData());
		assertFalse(r.isOk());
	}

	@Test
	@DisplayName("链式调用测试")
	void testChainCall() {
		R<String> r = new R<String>().setCode(0).setMsg("success").setData("data");
		assertEquals(0, r.getCode());
		assertEquals("success", r.getMsg());
		assertEquals("data", r.getData());
	}

}
