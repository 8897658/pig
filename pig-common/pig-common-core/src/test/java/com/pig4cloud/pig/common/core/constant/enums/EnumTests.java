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
package com.pig4cloud.pig.common.core.constant.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 枚举类测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("枚举类测试")
class EnumTests {

	@Test
	@DisplayName("ProcessStatusEnum 枚举值验证")
	void testProcessStatusEnum() {
		assertEquals("active", ProcessStatusEnum.ACTIVE.getStatus());
		assertEquals("suspend", ProcessStatusEnum.SUSPEND.getStatus());
		assertEquals(2, ProcessStatusEnum.values().length);
	}

	@Test
	@DisplayName("YesNoEnum 枚举值验证")
	void testYesNoEnum() {
		assertEquals("1", YesNoEnum.YES.getCode());
		assertEquals("0", YesNoEnum.NO.getCode());
		assertEquals("是", YesNoEnum.YES.getDesc());
		assertEquals("否", YesNoEnum.NO.getDesc());
		assertEquals("1", YesNoEnum.getCode(true));
		assertEquals("0", YesNoEnum.getCode(false));
	}

	@Test
	@DisplayName("CaptchaFlagTypeEnum 枚举值验证")
	void testCaptchaFlagTypeEnum() {
		assertEquals("1", CaptchaFlagTypeEnum.ON.getType());
		assertEquals("0", CaptchaFlagTypeEnum.OFF.getType());
		assertEquals("开启验证码", CaptchaFlagTypeEnum.ON.getDescription());
		assertEquals("关闭验证码", CaptchaFlagTypeEnum.OFF.getDescription());
	}

	@Test
	@DisplayName("UserTypeEnum 枚举值验证")
	void testUserTypeEnum() {
		assertEquals("0", UserTypeEnum.TOB.getStatus());
		assertEquals("1", UserTypeEnum.TOC.getStatus());
		assertEquals("面向后台应用", UserTypeEnum.TOB.getDescription());
		assertEquals("面向小程序", UserTypeEnum.TOC.getDescription());
	}

	@Test
	@DisplayName("TaskStatusEnum 枚举值验证")
	void testTaskStatusEnum() {
		assertEquals("0", TaskStatusEnum.UNSUBMIT.getStatus());
		assertEquals("1", TaskStatusEnum.CHECK.getStatus());
		assertEquals("2", TaskStatusEnum.COMPLETED.getStatus());
		assertEquals("9", TaskStatusEnum.OVERRULE.getStatus());
		assertEquals("未提交", TaskStatusEnum.UNSUBMIT.getDescription());
		assertEquals("审核中", TaskStatusEnum.CHECK.getDescription());
		assertEquals("已完成", TaskStatusEnum.COMPLETED.getDescription());
		assertEquals("驳回", TaskStatusEnum.OVERRULE.getDescription());
	}

	@Test
	@DisplayName("StyleTypeEnum 枚举值验证")
	void testStyleTypeEnum() {
		assertEquals("0", StyleTypeEnum.AVUE.getStyle());
		assertEquals("1", StyleTypeEnum.ELEMENT.getStyle());
		assertEquals("2", StyleTypeEnum.UVIEW.getStyle());
		assertEquals("3", StyleTypeEnum.MAGIC.getStyle());
		assertEquals("4", StyleTypeEnum.PLUS.getStyle());
		assertEquals("avue", StyleTypeEnum.AVUE.getDescription());
		assertEquals("element", StyleTypeEnum.ELEMENT.getDescription());
		assertEquals("avue", StyleTypeEnum.getDesc("0"));
		assertEquals("element", StyleTypeEnum.getDesc("1"));
		assertEquals("", StyleTypeEnum.getDesc("unknown"));
	}

	@Test
	@DisplayName("ResourceTypeEnum 枚举值验证")
	void testResourceTypeEnum() {
		assertEquals("image", ResourceTypeEnum.IMAGE.getType());
		assertEquals("xml", ResourceTypeEnum.XML.getType());
		assertEquals("图片资源", ResourceTypeEnum.IMAGE.getDescription());
		assertEquals("xml资源", ResourceTypeEnum.XML.getDescription());
	}

	@Test
	@DisplayName("MenuTypeEnum 枚举值验证")
	void testMenuTypeEnum() {
		assertEquals("0", MenuTypeEnum.LEFT_MENU.getType());
		assertEquals("2", MenuTypeEnum.TOP_MENU.getType());
		assertEquals("1", MenuTypeEnum.BUTTON.getType());
		assertEquals("left", MenuTypeEnum.LEFT_MENU.getDescription());
		assertEquals("top", MenuTypeEnum.TOP_MENU.getDescription());
		assertEquals("button", MenuTypeEnum.BUTTON.getDescription());
	}

	@Test
	@DisplayName("LoginTypeEnum 枚举值验证")
	void testLoginTypeEnum() {
		assertEquals("PWD", LoginTypeEnum.PWD.getType());
		assertEquals("SMS", LoginTypeEnum.SMS.getType());
		assertEquals("账号密码登录", LoginTypeEnum.PWD.getDescription());
		assertEquals("验证码登录", LoginTypeEnum.SMS.getDescription());
		assertTrue(LoginTypeEnum.values().length > 0);
	}

	@Test
	@DisplayName("EncFlagTypeEnum 枚举值验证")
	void testEncFlagTypeEnum() {
		assertEquals("0", EncFlagTypeEnum.NO.getType());
		assertEquals("1", EncFlagTypeEnum.YES.getType());
	}

	@Test
	@DisplayName("DictTypeEnum 枚举值验证")
	void testDictTypeEnum() {
		assertEquals("1", DictTypeEnum.SYSTEM.getType());
		assertEquals("0", DictTypeEnum.BIZ.getType());
		assertEquals("系统内置", DictTypeEnum.SYSTEM.getDescription());
		assertEquals("业务类", DictTypeEnum.BIZ.getDescription());
	}

	@Test
	@DisplayName("ParamTypeEnum 枚举值验证")
	void testParamTypeEnum() {
		assertEquals("0", ParamTypeEnum.DEFAULT.getType());
		assertEquals("1", ParamTypeEnum.SEARCH.getType());
		assertEquals("2", ParamTypeEnum.ORIGINAL.getType());
		assertEquals("默认", ParamTypeEnum.DEFAULT.getDescription());
		assertEquals("检索", ParamTypeEnum.SEARCH.getDescription());
		assertTrue(ParamTypeEnum.values().length > 5);
	}

}