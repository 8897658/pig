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
package com.pig4cloud.pig.wechat.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公众号/小程序账号配置表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("wx_account")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "微信账号配置")
public class WxAccount extends Model<WxAccount> {

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
	 * 账号类型：1-公众号，2-小程序
	 */
	@Schema(description = "账号类型")
	private Integer accountType;

	/**
	 * AppID
	 */
	@Schema(description = "AppID")
	private String appId;

	/**
	 * AppSecret（加密存储）
	 */
	@Schema(description = "AppSecret")
	private String appSecret;

	/**
	 * 消息Token
	 */
	@Schema(description = "消息Token")
	private String token;

	/**
	 * 消息加密密钥
	 */
	@Schema(description = "消息加密密钥")
	private String aesKey;

	/**
	 * 原始ID
	 */
	@Schema(description = "原始ID")
	private String originalId;

	/**
	 * 账号名称
	 */
	@Schema(description = "账号名称")
	private String name;

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