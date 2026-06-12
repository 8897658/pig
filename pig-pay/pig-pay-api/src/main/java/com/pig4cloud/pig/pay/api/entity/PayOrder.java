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
 * 支付订单表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("pay_order")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付订单")
public class PayOrder extends Model<PayOrder> {

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
	 * 商户订单号
	 */
	@Schema(description = "商户订单号")
	private String orderNo;

	/**
	 * 渠道订单号
	 */
	@Schema(description = "渠道订单号")
	private String channelOrderNo;

	/**
	 * 支付渠道：1-微信，2-支付宝，3-银联
	 */
	@Schema(description = "支付渠道")
	private Integer channel;

	/**
	 * 支付金额（元）
	 */
	@Schema(description = "支付金额（元）")
	private BigDecimal amount;

	/**
	 * 状态：0-待支付，1-支付中，2-支付成功，3-支付失败
	 */
	@Schema(description = "状态")
	private Integer status;

	/**
	 * 订单标题
	 */
	@Schema(description = "订单标题")
	private String subject;

	/**
	 * 订单描述
	 */
	@Schema(description = "订单描述")
	private String body;

	/**
	 * 回调地址
	 */
	@Schema(description = "回调地址")
	private String notifyUrl;

	/**
	 * 过期时间
	 */
	@Schema(description = "过期时间")
	private LocalDateTime expireTime;

	/**
	 * 支付时间
	 */
	@Schema(description = "支付时间")
	private LocalDateTime payTime;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}