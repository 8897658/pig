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
package com.pig4cloud.pig.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.pay.api.entity.PayOrder;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付订单服务
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface PayOrderService extends IService<PayOrder> {

	/**
	 * 创建支付订单
	 * @param channel 支付渠道
	 * @param amount 金额
	 * @param subject 订单标题
	 * @param body 订单描述
	 * @return 支付参数
	 */
	Map<String, Object> createOrder(Integer channel, BigDecimal amount, String subject, String body);

	/**
	 * 处理支付回调
	 * @param channel 支付渠道
	 * @param notifyData 回调数据
	 * @return 处理结果
	 */
	boolean handleNotify(Integer channel, String notifyData);

	/**
	 * 查询订单状态
	 * @param orderNo 商户订单号
	 * @return 订单状态
	 */
	Integer queryStatus(String orderNo);

	/**
	 * 发起退款
	 * @param orderNo 订单号
	 * @param refundAmount 退款金额
	 * @param reason 退款原因
	 * @return 退款单号
	 */
	String refund(String orderNo, BigDecimal refundAmount, String reason);

}