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
package com.pig4cloud.pig.pay.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 退款订单表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("pay_refund")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "退款订单")
public class PayRefund extends Model<PayRefund> {

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "主键ID")
	private Long id;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private Long tenantId;

	/**
	 * 商户退款单号
	 */
	@Schema(description = "商户退款单号")
	private String refundNo;

	/**
	 * 渠道退款单号
	 */
	@Schema(description = "渠道退款单号")
	private String channelRefundNo;

	/**
	 * 原订单号
	 */
	@Schema(description = "原订单号")
	private String orderNo;

	/**
	 * 退款金额
	 */
	@Schema(description = "退款金额")
	private BigDecimal refundAmount;

	/**
	 * 状态：0-待退款，1-退款中，2-退款成功，3-退款失败
	 */
	@Schema(description = "状态")
	private Integer status;

	/**
	 * 退款原因
	 */
	@Schema(description = "退款原因")
	private String reason;

	/**
	 * 退款时间
	 */
	@Schema(description = "退款时间")
	private LocalDateTime refundTime;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}