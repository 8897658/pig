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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 支付渠道配置表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("pay_channel_config")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付渠道配置")
public class PayChannelConfig extends Model<PayChannelConfig> {

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
	 * 支付渠道
	 */
	@NotNull(message = "支付渠道不能为空")
	@Schema(description = "支付渠道")
	private Integer channel;

	/**
	 * 应用ID
	 */
	@NotBlank(message = "应用ID不能为空")
	@Schema(description = "应用ID")
	private String appId;

	/**
	 * 商户号
	 */
	@NotBlank(message = "商户号不能为空")
	@Schema(description = "商户号")
	private String mchId;

	/**
	 * API密钥（加密存储）
	 */
	@NotBlank(message = "API密钥不能为空")
	@Schema(description = "API密钥")
	private String apiKey;

	/**
	 * 证书信息（加密存储）
	 */
	@Schema(description = "证书信息")
	private String certInfo;

	/**
	 * 回调地址
	 */
	@NotBlank(message = "回调地址不能为空")
	@Schema(description = "回调地址")
	private String notifyUrl;

	/**
	 * 状态：0-禁用，1-启用
	 */
	@Schema(description = "状态")
	private Integer status;

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
