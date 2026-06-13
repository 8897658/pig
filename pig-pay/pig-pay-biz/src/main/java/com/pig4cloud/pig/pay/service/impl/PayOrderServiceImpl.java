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
package com.pig4cloud.pig.pay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.exception.BizAssert;
import com.pig4cloud.pig.common.core.exception.CommonErrorCode;
import com.pig4cloud.pig.pay.api.entity.PayChannelConfig;
import com.pig4cloud.pig.pay.api.entity.PayOrder;
import com.pig4cloud.pig.pay.api.enums.PayChannelEnum;
import com.pig4cloud.pig.pay.channel.PayChannel;
import com.pig4cloud.pig.pay.mapper.PayOrderMapper;
import com.pig4cloud.pig.pay.service.PayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 支付订单服务实现
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {

	private final List<PayChannel> payChannels;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> createOrder(Integer channel, BigDecimal amount, String subject, String body) {
		// 生成订单号
		String orderNo = IdUtil.fastSimpleUUID();

		// 创建订单
		PayOrder order = new PayOrder();
		order.setOrderNo(orderNo);
		order.setChannel(channel);
		order.setAmount(amount);
		order.setSubject(subject);
		order.setBody(body);
		order.setStatus(0); // 待支付
		order.setExpireTime(LocalDateTime.now().plusHours(2)); // 2小时过期
		this.save(order);

		// 获取渠道配置
		PayChannelConfig config = getChannelConfig(channel);
		BizAssert.notNull(config, CommonErrorCode.PAY_CHANNEL_NOT_CONFIG, "支付渠道[" + channel + "]未配置");

		// 获取渠道实现
		PayChannel payChannel = getPayChannel(channel);
		BizAssert.notNull(payChannel, CommonErrorCode.PAY_CHANNEL_NOT_SUPPORT, "不支持的支付渠道[" + channel + "]");

		// 创建支付
		return payChannel.createPayment(order, config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean handleNotify(Integer channel, String notifyData) {
		PayChannelConfig config = getChannelConfig(channel);
		if (config == null) {
			log.warn("支付回调处理失败: 渠道[{}]配置不存在", channel);
			return false;
		}

		PayChannel payChannel = getPayChannel(channel);
		if (payChannel == null) {
			log.warn("支付回调处理失败: 渠道[{}]实现不存在", channel);
			return false;
		}

		String orderNo = payChannel.handleNotify(notifyData, config);
		if (orderNo != null) {
			// 更新订单状态
			PayOrder order = this.lambdaQuery().eq(PayOrder::getOrderNo, orderNo).one();
			if (order != null) {
				order.setStatus(2); // 支付成功
				order.setPayTime(LocalDateTime.now());
				this.updateById(order);
				return true;
			}
		}
		return false;
	}

	@Override
	public Integer queryStatus(String orderNo) {
		PayOrder order = this.lambdaQuery().eq(PayOrder::getOrderNo, orderNo).one();
		return order != null ? order.getStatus() : null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String refund(String orderNo, BigDecimal refundAmount, String reason) {
		PayOrder order = this.lambdaQuery().eq(PayOrder::getOrderNo, orderNo).one();
		BizAssert.notNull(order, CommonErrorCode.PAY_ORDER_NOT_FOUND, "订单[" + orderNo + "]不存在");

		PayChannelConfig config = getChannelConfig(order.getChannel());
		BizAssert.notNull(config, CommonErrorCode.PAY_CHANNEL_NOT_CONFIG, "支付渠道未配置");

		PayChannel payChannel = getPayChannel(order.getChannel());
		BizAssert.notNull(payChannel, CommonErrorCode.PAY_CHANNEL_NOT_SUPPORT, "不支持的支付渠道");

		return payChannel.refund(order, refundAmount, reason, config);
	}

	/**
	 * 获取渠道配置
	 */
	private PayChannelConfig getChannelConfig(Integer channel) {
		// TODO: 从数据库查询租户级渠道配置
		return null;
	}

	/**
	 * 获取渠道实现
	 */
	private PayChannel getPayChannel(Integer channel) {
		return payChannels.stream().filter(p -> p.getChannelCode() == channel).findFirst().orElse(null);
	}

}