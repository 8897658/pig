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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用错误码枚举
 * <p>
 * 定义系统通用的错误码，遵循以下规范：
 * - 1xxxxx: 系统级错误
 * - 2xxxxx: 业务级错误
 * - 3xxxxx: 第三方服务错误
 *
 * @author lengleng
 * @date 2026-06-13
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	// ==================== 系统级错误 (1xxxxx) ====================
	SYSTEM_ERROR("100001", "系统异常，请稍后重试"),
	PARAM_ERROR("100002", "参数校验失败"),
	DATA_NOT_FOUND("100003", "数据不存在"),
	DATA_EXISTS("100004", "数据已存在"),
	DATABASE_ERROR("100005", "数据库操作异常"),

	// ==================== 业务级错误 (2xxxxx) ====================
	USER_NOT_FOUND("200001", "用户不存在"),
	USER_DISABLED("200002", "用户已被禁用"),
	USER_LOCKED("200003", "用户已被锁定"),
	PASSWORD_ERROR("200004", "密码错误"),
	PASSWORD_EXPIRED("200005", "密码已过期"),
	TOKEN_EXPIRED("200006", "令牌已过期"),
	TOKEN_INVALID("200007", "令牌无效"),
	PERMISSION_DENIED("200008", "权限不足"),

	// ==================== 支付业务错误 (21xxxx) ====================
	PAY_CHANNEL_NOT_CONFIG("210001", "支付渠道未配置"),
	PAY_CHANNEL_NOT_SUPPORT("210002", "不支持的支付渠道"),
	PAY_ORDER_NOT_FOUND("210003", "订单不存在"),
	PAY_ORDER_EXPIRED("210004", "订单已过期"),
	PAY_ORDER_PAID("210005", "订单已支付"),
	PAY_AMOUNT_ERROR("210006", "支付金额错误"),
	PAY_REFUND_ERROR("210007", "退款失败"),

	// ==================== 第三方服务错误 (3xxxxx) ====================
	THIRD_SERVICE_ERROR("300001", "第三方服务异常"),
	WECHAT_PAY_ERROR("310001", "微信支付异常"),
	ALIPAY_ERROR("310002", "支付宝异常"),
	SMS_SEND_ERROR("320001", "短信发送失败"),
	;

	private final String code;
	private final String message;

}