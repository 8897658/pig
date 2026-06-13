/*
 * Copyright (c) 2018-2026, lengleng All rights reserved.
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
package com.pig4cloud.pig.common.core.util;

import cn.hutool.core.util.StrUtil;

/**
 * 数据脱敏工具类
 * <p>
 * 用于日志输出、异常信息等场景的数据脱敏处理
 *
 * @author lengleng
 * @date 2026-06-13
 */
public final class DesensitizeUtil {

	private DesensitizeUtil() {
	}

	/**
	 * 手机号脱敏
	 * <p>
	 * 保留前3位和后4位，中间用*替代
	 * 示例: 13812345678 -> 138****5678
	 *
	 * @param phone 手机号
	 * @return 脱敏后的手机号
	 */
	public static String phone(String phone) {
		if (StrUtil.isBlank(phone) || phone.length() < 7) {
			return phone;
		}
		return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
	}

	/**
	 * 邮箱脱敏
	 * <p>
	 * 保留前3位和@后的域名，中间用*替代
	 * 示例: test@example.com -> tes***@example.com
	 *
	 * @param email 邮箱
	 * @return 脱敏后的邮箱
	 */
	public static String email(String email) {
		if (StrUtil.isBlank(email) || !email.contains("@")) {
			return email;
		}
		int atIndex = email.indexOf("@");
		if (atIndex <= 3) {
			return email;
		}
		return email.substring(0, 3) + "***" + email.substring(atIndex);
	}

	/**
	 * 身份证号脱敏
	 * <p>
	 * 保留前6位和后4位，中间用*替代
	 * 示例: 110101199001011234 -> 110101********1234
	 *
	 * @param idCard 身份证号
	 * @return 脱敏后的身份证号
	 */
	public static String idCard(String idCard) {
		if (StrUtil.isBlank(idCard) || idCard.length() < 10) {
			return idCard;
		}
		return idCard.substring(0, 6) + "********" + idCard.substring(idCard.length() - 4);
	}

	/**
	 * 银行卡号脱敏
	 * <p>
	 * 保留前4位和后4位，中间用*替代
	 * 示例: 6222021234567890 -> 6222************7890
	 *
	 * @param bankCard 银行卡号
	 * @return 脱敏后的银行卡号
	 */
	public static String bankCard(String bankCard) {
		if (StrUtil.isBlank(bankCard) || bankCard.length() < 8) {
			return bankCard;
		}
		int length = bankCard.length();
		int middleLength = length - 8;
		return bankCard.substring(0, 4) + StrUtil.repeat("*", middleLength) + bankCard.substring(length - 4);
	}

	/**
	 * 姓名脱敏
	 * <p>
	 * 保留第一个字符，其余用*替代
	 * 示例: 张三 -> 张*, 张三四 -> 张**
	 *
	 * @param name 姓名
	 * @return 脱敏后的姓名
	 */
	public static String name(String name) {
		if (StrUtil.isBlank(name)) {
			return name;
		}
		if (name.length() == 1) {
			return name;
		}
		return name.charAt(0) + StrUtil.repeat("*", name.length() - 1);
	}

	/**
	 * 地址脱敏
	 * <p>
	 * 保留前10个字符，其余用*替代
	 *
	 * @param address 地址
	 * @return 脱敏后的地址
	 */
	public static String address(String address) {
		if (StrUtil.isBlank(address) || address.length() <= 10) {
			return address;
		}
		return address.substring(0, 10) + "***";
	}

	/**
	 * 密码脱敏
	 * <p>
	 * 直接返回 ****，不暴露任何密码信息
	 *
	 * @param password 密码
	 * @return 脱敏后的密码
	 */
	public static String password(String password) {
		return "****";
	}

	/**
	 * 自定义脱敏
	 * <p>
	 * 保留前prefixLen位和后suffixLen位，中间用*替代
	 *
	 * @param str 待脱敏字符串
	 * @param prefixLen 前置保留长度
	 * @param suffixLen 后置保留长度
	 * @return 脱敏后的字符串
	 */
	public static String custom(String str, int prefixLen, int suffixLen) {
		if (StrUtil.isBlank(str) || str.length() <= prefixLen + suffixLen) {
			return str;
		}
		int middleLength = str.length() - prefixLen - suffixLen;
		return str.substring(0, prefixLen) + StrUtil.repeat("*", middleLength) + str.substring(str.length() - suffixLen);
	}

}