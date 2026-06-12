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
package com.pig4cloud.pig.pay.controller;

import com.pig4cloud.pig.pay.service.PayOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 支付回调控制器
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/pay/callback")
@Tag(description = "pay-callback", name = "支付回调模块")
public class PayCallbackController {

	private final PayOrderService payOrderService;

	/**
	 * 微信支付回调
	 * @param notifyData 回调数据
	 * @return 处理结果
	 */
	@PostMapping("/wechat")
	@Operation(description = "微信支付回调", summary = "微信支付回调")
	public String wechatCallback(@RequestBody String notifyData) {
		log.info("收到微信支付回调");
		boolean result = payOrderService.handleNotify(1, notifyData);
		return result ? "SUCCESS" : "FAIL";
	}

	/**
	 * 支付宝回调
	 * @param notifyData 回调数据
	 * @return 处理结果
	 */
	@PostMapping("/alipay")
	@Operation(description = "支付宝回调", summary = "支付宝回调")
	public String alipayCallback(@RequestBody String notifyData) {
		log.info("收到支付宝回调");
		boolean result = payOrderService.handleNotify(2, notifyData);
		return result ? "success" : "fail";
	}

}