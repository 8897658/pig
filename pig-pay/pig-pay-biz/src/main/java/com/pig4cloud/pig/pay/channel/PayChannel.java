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

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付渠道接口
 * <p>
 * 统一支付渠道适配器，支持微信支付、支付宝、银联等
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface PayChannel {

	/**
	 * 获取渠道编码
	 * @return 渠道编码
	 */
	int getChannelCode();

	/**
	 * 创建支付订单
	 * @param order 支付订单
	 * @param config 渠道配置
	 * @return 支付参数（用于前端调起支付）
	 */
	Map<String, Object> createPayment(PayOrder order, PayChannelConfig config);

	/**
	 * 处理支付回调
	 * @param notifyData 回调数据
	 * @param config 渠道配置
	 * @return 订单号
	 */
	String handleNotify(String notifyData, PayChannelConfig config);

	/**
	 * 查询订单状态
	 * @param orderNo 商户订单号
	 * @param config 渠道配置
	 * @return 订单状态
	 */
	Integer queryOrderStatus(String orderNo, PayChannelConfig config);

	/**
	 * 发起退款
	 * @param order 原订单
	 * @param refundAmount 退款金额
	 * @param reason 退款原因
	 * @param config 渠道配置
	 * @return 退款单号
	 */
	String refund(PayOrder order, BigDecimal refundAmount, String reason, PayChannelConfig config);

}