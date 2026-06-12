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
package com.pig4cloud.pig.wechat.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.wechat.service.WxMiniService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 小程序控制器
 *
 * @author lengleng
 * @date 2026-06-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wechat/mini")
@Tag(description = "mini", name = "小程序管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MiniController {

	private final WxMiniService wxMiniService;

	/**
	 * 小程序登录
	 * @param appId AppID
	 * @param code 登录码
	 * @return 登录结果
	 */
	@PostMapping("/{appId}/login")
	@Operation(description = "小程序登录", summary = "小程序登录")
	public R<Map<String, Object>> login(@PathVariable String appId, @RequestParam String code) {
		return R.ok(wxMiniService.login(appId, code));
	}

	/**
	 * 获取手机号
	 * @param appId AppID
	 * @param code 手机号code
	 * @return 手机号信息
	 */
	@PostMapping("/{appId}/phone")
	@Operation(description = "获取手机号", summary = "获取手机号")
	public R<Map<String, Object>> getPhoneNumber(@PathVariable String appId, @RequestParam String code) {
		return R.ok(wxMiniService.getPhoneNumber(appId, code));
	}

	/**
	 * 生成小程序码
	 * @param appId AppID
	 * @param scene 场景值
	 * @param page 页面路径
	 * @return 小程序码URL
	 */
	@GetMapping("/{appId}/qrcode")
	@Operation(description = "生成小程序码", summary = "生成小程序码")
	public R<String> generateQrCode(@PathVariable String appId, @RequestParam String scene,
			@RequestParam(required = false) String page) {
		return R.ok(wxMiniService.generateQrCode(appId, scene, page));
	}

}