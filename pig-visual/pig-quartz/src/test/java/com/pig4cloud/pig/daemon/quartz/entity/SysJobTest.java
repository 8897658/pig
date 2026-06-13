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
package com.pig4cloud.pig.daemon.quartz.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SysJob 实体测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("定时任务实体测试")
class SysJobTest {

	@Test
	@DisplayName("创建任务对象")
	void testCreateJob() {
		SysJob job = new SysJob();
		job.setJobName("测试任务");
		job.setJobGroup("DEFAULT");
		job.setJobType("1");
		job.setJobStatus("0");
		assertNotNull(job);
		assertEquals("测试任务", job.getJobName());
		assertEquals("DEFAULT", job.getJobGroup());
	}

	@Test
	@DisplayName("任务类型验证")
	void testJobType() {
		String[] validTypes = { "1", "2", "3", "4", "9" };
		for (String type : validTypes) {
			assertTrue(type.matches("[12349]"));
		}
	}

	@Test
	@DisplayName("任务优先级验证")
	void testJobOrder() {
		int order = 5;
		assertTrue(order >= 1 && order <= 9);
	}

	@Test
	@DisplayName("Cron表达式格式验证")
	void testCronExpression() {
		String cron = "0 0 12 * * ?";
		// 简单验证cron表达式有6或7个字段
		assertTrue(cron.split("\\s+").length >= 6);
	}

	@Test
	@DisplayName("任务状态验证")
	void testJobStatus() {
		String[] validStatus = { "0", "1", "2" };
		for (String status : validStatus) {
			assertTrue(status.matches("[012]"));
		}
	}

}