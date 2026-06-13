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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpringContextHolder 集成测试
 * <p>
 * 需要Spring上下文才能正常工作
 *
 * @author test
 * @date 2026-06-13
 */
class SpringContextHolderTest {

	@Test
	void testGetBeanWithoutContext() {
		// 在没有Spring上下文时，调用会抛出NullPointerException
		assertThrows(NullPointerException.class, () -> {
			SpringContextHolder.getBean("testBean");
		});
	}

	@Test
	void testGetApplicationContext() {
		// 没有Spring上下文时返回null
		assertNull(SpringContextHolder.getApplicationContext());
	}

}
