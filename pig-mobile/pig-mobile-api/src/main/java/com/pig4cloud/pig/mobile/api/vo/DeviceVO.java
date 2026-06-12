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
package com.pig4cloud.pig.mobile.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备VO
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Data
@Schema(description = "设备信息")
public class DeviceVO {

	/**
	 * 设备ID
	 */
	@Schema(description = "设备ID")
	private String deviceId;

	/**
	 * 设备类型：1-iOS，2-Android，3-H5
	 */
	@Schema(description = "设备类型")
	private Integer deviceType;

	/**
	 * 设备名称
	 */
	@Schema(description = "设备名称")
	private String deviceName;

	/**
	 * 设备型号
	 */
	@Schema(description = "设备型号")
	private String deviceModel;

	/**
	 * 系统版本
	 */
	@Schema(description = "系统版本")
	private String osVersion;

	/**
	 * 最后登录时间
	 */
	@Schema(description = "最后登录时间")
	private LocalDateTime lastLoginTime;

	/**
	 * 状态
	 */
	@Schema(description = "状态")
	private Integer status;

}