/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
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

package com.pig4cloud.pig.process.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程任务表
 *
 * @author lengleng
 * @since 2025-06-11
 */
@Data
@Schema(description = "流程任务")
@EqualsAndHashCode(callSuper = true)
public class ProcessTask extends Model<ProcessTask> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "task_id", type = IdType.ASSIGN_ID)
	@Schema(description = "主键id")
	private Long taskId;

	/**
	 * 流程实例ID
	 */
	@Schema(description = "流程实例ID")
	private String processInstanceId;

	/**
	 * 任务名称
	 */
	@Schema(description = "任务名称")
	private String taskName;

	/**
	 * 任务描述
	 */
	@Schema(description = "任务描述")
	private String taskDescription;

	/**
	 * 任务状态
	 */
	@Schema(description = "任务状态")
	private String taskStatus;

	/**
	 * 任务优先级
	 */
	@Schema(description = "任务优先级")
	private Integer priority;

	/**
	 * 任务创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "任务创建人")
	private String createBy;

	/**
	 * 任务修改人
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "任务修改人")
	private String updateBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标记
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private Long tenantId;

}
