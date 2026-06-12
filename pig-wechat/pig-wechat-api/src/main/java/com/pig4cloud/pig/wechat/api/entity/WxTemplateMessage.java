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
 * 模板消息/订阅消息发送记录表
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("wx_template_message")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "模板消息记录")
public class WxTemplateMessage extends Model<WxTemplateMessage> {

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
	 * AppID
	 */
	@Schema(description = "AppID")
	private String appId;

	/**
	 * 接收用户OpenID
	 */
	@Schema(description = "OpenID")
	private String openId;

	/**
	 * 模板ID
	 */
	@Schema(description = "模板ID")
	private String templateId;

	/**
	 * 模板数据（JSON）
	 */
	@Schema(description = "模板数据")
	private String data;

	/**
	 * 状态：0-待发送，1-发送成功，2-发送失败
	 */
	@Schema(description = "状态")
	private Integer status;

	/**
	 * 发送时间
	 */
	@Schema(description = "发送时间")
	private LocalDateTime sendTime;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}