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
package com.pig4cloud.pig.pay.channel;

import com.pig4cloud.pig.pay.api.entity.PayChannelConfig;
import com.pig4cloud.pig.pay.api.entity.PayOrder;
import com.pig4cloud.pig.pay.api.enums.PayChannelEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付渠道实现
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@Component
public class WechatPayChannel implements PayChannel {

	@Override
	public int getChannelCode() {
		return PayChannelEnum.WECHAT.getCode();
	}

	@Override
	public Map<String, Object> createPayment(PayOrder order, PayChannelConfig config) {
		log.info("创建微信支付订单: orderNo={}, amount={}", order.getOrderNo(), order.getAmount());
		// TODO: 实现微信支付下单逻辑（使用 wechatpay-java SDK）
		Map<String, Object> result = new HashMap<>();
		result.put("appId", config.getAppId());
		result.put("orderNo", order.getOrderNo());
		result.put("amount", order.getAmount());
		return result;
	}

	@Override
	public String handleNotify(String notifyData, PayChannelConfig config) {
		log.info("处理微信支付回调");
		// TODO: 实现微信支付回调验签和处理
		return null;
	}

	@Override
	public Integer queryOrderStatus(String orderNo, PayChannelConfig config) {
		log.info("查询微信支付订单状态: orderNo={}", orderNo);
		// TODO: 实现微信支付订单查询
		return null;
	}

	@Override
	public String refund(PayOrder order, BigDecimal refundAmount, String reason, PayChannelConfig config) {
		log.info("微信支付退款: orderNo={}, refundAmount={}, reason={}", order.getOrderNo(), refundAmount, reason);
		// TODO: 实现微信支付退款
		return null;
	}

}