/*
 *
 *      Copyright (c) 2018-2026, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */
package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * <p>
 * 租户信息表
 * </p>
 *
 * @author lengleng
 * @since 2026-06-11
 */
@Data
@TableName("sys_tenant")
@Schema(description = "租户信息")
@EqualsAndHashCode(callSuper = true)
public class SysTenant extends Model<SysTenant> {

	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "租户ID")
	private Long id;

	/**
	 * 租户名称
	 */
	@NotBlank(message = "租户名称不能为空")
	@Schema(description = "租户名称")
	private String name;

	/**
	 * 租户编码
	 */
	@NotBlank(message = "租户编码不能为空")
	@Schema(description = "租户编码")
	private String code;

	/**
	 * 绑定域名
	 */
	@Schema(description = "绑定域名")
	private String domain;

	/**
	 * 状态：0-正常，1-禁用
	 */
	@Schema(description = "状态：0-正常，1-禁用")
	private String status;

	/**
	 * 过期时间
	 */
	@Schema(description = "过期时间")
	private LocalDateTime expireTime;

	/**
	 * 联系人
	 */
	@Schema(description = "联系人")
	private String contactName;

	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	private String contactPhone;

	/**
	 * 联系邮箱
	 */
	@Schema(description = "联系邮箱")
	private String contactEmail;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新人
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新人")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标记：0-正常，1-已删除
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "删除标记：0-正常，1-已删除")
	private String delFlag;

}
