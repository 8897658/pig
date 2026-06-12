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
 * 微信用户表
 * <p>
 * 公众号+小程序统一用户管理，通过 UnionID 打通身份
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@TableName("wx_user")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "微信用户")
public class WxUser extends Model<WxUser> {

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
	 * 来源AppID
	 */
	@Schema(description = "来源AppID")
	private String appId;

	/**
	 * UnionID（用于打通公众号和小程序）
	 */
	@Schema(description = "UnionID")
	private String unionId;

	/**
	 * OpenID
	 */
	@Schema(description = "OpenID")
	private String openId;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickname;

	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String headImgUrl;

	/**
	 * 性别
	 */
	@Schema(description = "性别")
	private Integer sex;

	/**
	 * 国家
	 */
	@Schema(description = "国家")
	private String country;

	/**
	 * 省份
	 */
	@Schema(description = "省份")
	private String province;

	/**
	 * 城市
	 */
	@Schema(description = "城市")
	private String city;

	/**
	 * 是否关注：0-未关注，1-已关注
	 */
	@Schema(description = "是否关注")
	private Integer subscribe;

	/**
	 * 关注时间
	 */
	@Schema(description = "关注时间")
	private LocalDateTime subscribeTime;

	/**
	 * 取消关注时间
	 */
	@Schema(description = "取消关注时间")
	private LocalDateTime unsubscribeTime;

	/**
	 * 关联Pig系统用户ID
	 */
	@Schema(description = "关联系统用户ID")
	private Long userId;

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