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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色数据权限表
 * </p>
 *
 * @author lengleng
 * @since 2026-06-11
 */
@Data
@TableName("sys_data_scope")
@Schema(description = "角色数据权限")
@EqualsAndHashCode(callSuper = true)
public class SysDataScope extends Model<SysDataScope> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键ID")
	private Long id;

	/**
	 * 角色ID
	 */
	@NotNull(message = "角色ID不能为空")
	@Schema(description = "角色ID")
	private Long roleId;

	/**
	 * 数据范围类型：0-全部，1-自定义，2-本部门，3-本部门及以下，4-仅本人
	 */
	@NotNull(message = "数据范围类型不能为空")
	@Schema(description = "数据范围类型：0-全部，1-自定义，2-本部门，3-本部门及以下，4-仅本人")
	private String scopeType;

	/**
	 * 数据范围值（自定义时存储部门ID列表）
	 */
	@Schema(description = "数据范围值（自定义时存储部门ID列表）")
	private String scopeValue;

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
