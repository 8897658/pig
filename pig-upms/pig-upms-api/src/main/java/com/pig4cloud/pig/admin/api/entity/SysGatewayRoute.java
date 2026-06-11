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
 * 网关路由配置表
 * </p>
 *
 * @author lengleng
 * @since 2026-06-11
 */
@Data
@TableName("sys_gateway_route")
@Schema(description = "网关路由配置")
@EqualsAndHashCode(callSuper = true)
public class SysGatewayRoute extends Model<SysGatewayRoute> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键ID")
	private Long id;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private Long tenantId;

	/**
	 * 路由ID（全局唯一）
	 */
	@NotBlank(message = "路由ID不能为空")
	@Schema(description = "路由ID")
	private String routeId;

	/**
	 * 路由名称
	 */
	@Schema(description = "路由名称")
	private String routeName;

	/**
	 * 路由URI（lb://service-name 或 http://xxx）
	 */
	@NotBlank(message = "路由URI不能为空")
	@Schema(description = "路由URI")
	private String uri;

	/**
	 * 谓词配置（JSON数组）
	 */
	@Schema(description = "谓词配置")
	private String predicates;

	/**
	 * 过滤器配置（JSON数组）
	 */
	@Schema(description = "过滤器配置")
	private String filters;

	/**
	 * 元数据配置（JSON对象）
	 */
	@Schema(description = "元数据配置")
	private String metadata;

	/**
	 * 排序值（数值越小优先级越高）
	 */
	@Schema(description = "排序值")
	private Integer sortOrder;

	/**
	 * 状态：0-启用，1-禁用
	 */
	@Schema(description = "状态：0-启用，1-禁用")
	private String status;

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
