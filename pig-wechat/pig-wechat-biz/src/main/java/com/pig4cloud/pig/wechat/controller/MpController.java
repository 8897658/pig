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

import com.pig4cloud.pig.wechat.service.WxMpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 公众号控制器
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wechat/mp")
@Tag(description = "mp", name = "公众号管理")
public class MpController {

	private final WxMpService wxMpService;

	/**
	 * 公众号消息接收
	 * @param appId AppID
	 * @param requestBody 请求体
	 * @param signature 签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param openid OpenID
	 * @return 响应内容
	 */
	@PostMapping("/{appId}/message")
	@Operation(description = "公众号消息接收", summary = "公众号消息接收")
	public String handleMessage(@PathVariable String appId, @RequestBody String requestBody,
			@RequestParam(required = false) String signature, @RequestParam(required = false) String timestamp,
			@RequestParam(required = false) String nonce, @RequestParam(required = false) String openid) {
		return wxMpService.handleMessage(appId, requestBody, signature, timestamp, nonce, openid);
	}

	/**
	 * 获取授权URL
	 * @param appId AppID
	 * @param redirectUri 回调地址
	 * @return 授权URL
	 */
	@GetMapping("/{appId}/auth/url")
	@Operation(description = "获取授权URL", summary = "获取授权URL")
	public String getAuthUrl(@PathVariable String appId, @RequestParam String redirectUri) {
		return wxMpService.getAuthorizationUrl(appId, redirectUri, "snsapi_userinfo", null);
	}

}