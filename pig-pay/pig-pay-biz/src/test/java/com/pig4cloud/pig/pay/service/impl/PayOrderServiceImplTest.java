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
package com.pig4cloud.pig.pay.service.impl;

import com.pig4cloud.pig.common.core.exception.BizException;
import com.pig4cloud.pig.common.core.exception.CommonErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PayOrderServiceImpl 单元测试
 *
 * @author lengleng
 * @date 2026-06-13
 */
@DisplayName("支付订单服务测试")
class PayOrderServiceImplTest {

	@Test
	@DisplayName("支付渠道枚举验证")
	void testPayChannelEnum() {
		// 验证错误码定义正确
		assertEquals("210001", CommonErrorCode.PAY_CHANNEL_NOT_CONFIG.getCode());
		assertEquals("210002", CommonErrorCode.PAY_CHANNEL_NOT_SUPPORT.getCode());
		assertEquals("210003", CommonErrorCode.PAY_ORDER_NOT_FOUND.getCode());
		assertEquals("支付渠道未配置", CommonErrorCode.PAY_CHANNEL_NOT_CONFIG.getMessage());
	}

	@Test
	@DisplayName("BizException 异常信息验证")
	void testBizExceptionInfo() {
		BizException ex = new BizException(CommonErrorCode.PAY_ORDER_NOT_FOUND, "订单[123]不存在");
		assertEquals("210003", ex.getCode());
		assertEquals("订单[123]不存在", ex.getMessage());
	}

	@Test
	@DisplayName("BizException 带原因构造")
	void testBizExceptionWithCause() {
		Exception cause = new RuntimeException("原始异常");
		BizException ex = new BizException(CommonErrorCode.PAY_REFUND_ERROR, "退款失败", cause);

		assertEquals("210007", ex.getCode());
		assertEquals("退款失败", ex.getMessage());
		assertEquals(cause, ex.getCause());
	}

	@Test
	@DisplayName("支付金额错误码验证")
	void testPayAmountErrorCode() {
		assertEquals("210006", CommonErrorCode.PAY_AMOUNT_ERROR.getCode());
		assertEquals("支付金额错误", CommonErrorCode.PAY_AMOUNT_ERROR.getMessage());
	}

	@Test
	@DisplayName("订单过期错误码验证")
	void testPayOrderExpiredErrorCode() {
		assertEquals("210004", CommonErrorCode.PAY_ORDER_EXPIRED.getCode());
		assertEquals("订单已过期", CommonErrorCode.PAY_ORDER_EXPIRED.getMessage());
	}

	@Test
	@DisplayName("订单已支付错误码验证")
	void testPayOrderPaidErrorCode() {
		assertEquals("210005", CommonErrorCode.PAY_ORDER_PAID.getCode());
		assertEquals("订单已支付", CommonErrorCode.PAY_ORDER_PAID.getMessage());
	}

}