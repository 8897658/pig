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
package com.pig4cloud.pig.mobile.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.mobile.api.dto.PushMessageDTO;
import com.pig4cloud.pig.mobile.service.DeviceService;
import com.pig4cloud.pig.mobile.service.PushService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * 移动端App控制器
 * <p>
 * 聚合接口，一次请求获取多个数据
 *
 * @author lengleng
 * @date 2026-06-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mobile/app")
@Tag(description = "mobile", name = "移动端服务")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AppController {

	private final DeviceService deviceService;

	private final PushService pushService;

	/**
	 * 初始化数据
	 * @return 聚合数据
	 */
	@GetMapping("/init")
	@Operation(description = "App初始化数据", summary = "App初始化数据")
	public R<Object> init() {
		// TODO: 返回用户信息、未读消息数、系统配置等
		return R.ok();
	}

	/**
	 * 注册设备
	 * @param deviceId 设备ID
	 * @param deviceType 设备类型
	 * @param deviceName 设备名称
	 * @return 是否成功
	 */
	@PostMapping("/device/register")
	@Operation(description = "注册设备", summary = "注册设备")
	public R<Boolean> registerDevice(@RequestParam String deviceId, @RequestParam Integer deviceType,
			@RequestParam(required = false) String deviceName) {
		return R.ok(deviceService.register(null));
	}

	/**
	 * 获取登录设备列表
	 * @return 设备列表
	 */
	@GetMapping("/device/list")
	@Operation(description = "获取登录设备列表", summary = "获取登录设备列表")
	public R<Object> listDevices() {
		return R.ok(deviceService.listByUserId(null));
	}

	/**
	 * 踢下线
	 * @param deviceId 设备ID
	 * @return 是否成功
	 */
	@DeleteMapping("/device/{deviceId}")
	@Operation(description = "踢下线", summary = "踢下线")
	public R<Boolean> kickOut(@PathVariable String deviceId) {
		return R.ok(deviceService.kickOut(null, deviceId));
	}

	/**
	 * 发送推送消息
	 * @param message 推送消息
	 * @return 是否成功
	 */
	@PostMapping("/push/send")
	@Operation(description = "发送推送消息", summary = "发送推送消息")
	public R<Boolean> sendPush(@RequestBody PushMessageDTO message) {
		return R.ok(pushService.send(message));
	}

}