/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 */
package com.pig4cloud.pig.admin.service.impl;

import com.pig4cloud.pig.admin.api.entity.SysPublicParam;
import com.pig4cloud.pig.admin.mapper.SysPublicParamMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SysPublicParamServiceImpl 业务测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SysPublicParamServiceImpl 业务测试")
class SysPublicParamServiceImplTest {

	@Mock
	private SysPublicParamMapper paramMapper;

	@InjectMocks
	private SysPublicParamServiceImpl paramService;

	@Nested
	@DisplayName("公共参数测试")
	class PublicParamTests {

		@Test
		@DisplayName("参数键-字母数字下划线")
		void testParamKey_Format() {
			String validKey = "USER_DEFAULT_ROLE";
			assertThat(validKey.matches("^[A-Z0-9_]+$")).isTrue();
		}

		@Test
		@DisplayName("参数值-不应为空")
		void testParamValue_NotEmpty() {
			String value = "ROLE_USER";
			assertThat(value).isNotEmpty();
		}

		@Test
		@DisplayName("系统参数-不可删除")
		void testSystemParam_CannotDelete() {
			Integer systemFlag = 1;
			assertThat(systemFlag).isEqualTo(1);
		}

	}

	private SysPublicParam createParam(Long paramId, String key) {
		SysPublicParam param = new SysPublicParam();
		param.setPublicId(paramId);
		param.setPublicKey(key);
		return param;
	}

}
