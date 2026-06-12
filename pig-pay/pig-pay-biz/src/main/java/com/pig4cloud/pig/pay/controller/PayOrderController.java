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

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.pay.service.PayOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付订单控制器
 *
 * @author lengleng
 * @date 2026-06-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pay/order")
@Tag(description = "pay-order", name = "支付订单模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PayOrderController {

	private final PayOrderService payOrderService;

	/**
	 * 创建支付订单
	 * @param channel 支付渠道
	 * @param amount 金额
	 * @param subject 订单标题
	 * @param body 订单描述
	 * @return R
	 */
	@PostMapping("/create")
	@Operation(description = "创建支付订单", summary = "创建支付订单")
	public R<Map<String, Object>> createOrder(@RequestParam Integer channel, @RequestParam BigDecimal amount,
			@RequestParam String subject, @RequestParam(required = false) String body) {
		return R.ok(payOrderService.createOrder(channel, amount, subject, body));
	}

	/**
	 * 查询订单状态
	 * @param orderNo 商户订单号
	 * @return R
	 */
	@GetMapping("/status/{orderNo}")
	@Operation(description = "查询订单状态", summary = "查询订单状态")
	public R<Integer> queryStatus(@PathVariable String orderNo) {
		return R.ok(payOrderService.queryStatus(orderNo));
	}

	/**
	 * 发起退款
	 * @param orderNo 订单号
	 * @param refundAmount 退款金额
	 * @param reason 退款原因
	 * @return R
	 */
	@PostMapping("/refund")
	@Operation(description = "发起退款", summary = "发起退款")
	public R<String> refund(@RequestParam String orderNo, @RequestParam BigDecimal refundAmount,
			@RequestParam(required = false) String reason) {
		return R.ok(payOrderService.refund(orderNo, refundAmount, reason));
	}

}