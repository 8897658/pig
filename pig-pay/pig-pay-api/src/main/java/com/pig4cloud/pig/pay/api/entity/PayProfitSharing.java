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
 * 分账记录表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("pay_profit_sharing")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分账记录")
public class PayProfitSharing extends Model<PayProfitSharing> {

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
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 分账接收方账号
	 */
	@Schema(description = "分账接收方账号")
	private String receiverAccount;

	/**
	 * 分账接收方名称
	 */
	@Schema(description = "分账接收方名称")
	private String receiverName;

	/**
	 * 分账金额
	 */
	@Schema(description = "分账金额")
	private BigDecimal amount;

	/**
	 * 分账描述
	 */
	@Schema(description = "分账描述")
	private String description;

	/**
	 * 状态：0-待分账，1-分账中，2-分账成功，3-分账失败
	 */
	@Schema(description = "状态")
	private Integer status;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}