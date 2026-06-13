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
package com.pig4cloud.pig.common.core.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CommonConstants 常量测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("通用常量测试")
class CommonConstantsTest {

	@Test
	@DisplayName("成功标记")
	void testSuccess() {
		assertEquals(0, CommonConstants.SUCCESS);
	}

	@Test
	@DisplayName("失败标记")
	void testFail() {
		assertEquals(1, CommonConstants.FAIL);
	}

	@Test
	@DisplayName("状态常量")
	void testStatus() {
		assertEquals("1", CommonConstants.STATUS_DEL);
		assertEquals("0", CommonConstants.STATUS_NORMAL);
		assertEquals("9", CommonConstants.STATUS_LOCK);
	}

	@Test
	@DisplayName("菜单树根节点")
	void testMenuTreeRootId() {
		assertEquals(-1L, CommonConstants.MENU_TREE_ROOT_ID);
	}

	@Test
	@DisplayName("工程名常量")
	void testProjectNames() {
		assertEquals("pig-ui", CommonConstants.FRONT_END_PROJECT);
		assertEquals("pig-app", CommonConstants.UNI_END_PROJECT);
		assertEquals("pig", CommonConstants.BACK_END_PROJECT);
	}

}
