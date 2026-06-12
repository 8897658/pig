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
package com.pig4cloud.pig.pay.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付渠道枚举
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Getter
@AllArgsConstructor
public enum PayChannelEnum {

	/**
	 * 微信支付
	 */
	WECHAT(1, "微信支付"),

	/**
	 * 支付宝
	 */
	ALIPAY(2, "支付宝"),

	/**
	 * 银联
	 */
	UNIONPAY(3, "银联");

	private final int code;

	private final String desc;

	public static PayChannelEnum fromCode(int code) {
		for (PayChannelEnum channel : values()) {
			if (channel.getCode() == code) {
				return channel;
			}
		}
		return null;
	}

}
