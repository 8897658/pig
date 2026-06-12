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
package com.pig4cloud.pig.wechat.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信账号类型枚举
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Getter
@AllArgsConstructor
public enum WxAccountTypeEnum {

	/**
	 * 公众号
	 */
	MP(1, "公众号"),

	/**
	 * 小程序
	 */
	MINI(2, "小程序");

	private final int code;

	private final String desc;

	public static WxAccountTypeEnum fromCode(int code) {
		for (WxAccountTypeEnum type : values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		return null;
	}

}