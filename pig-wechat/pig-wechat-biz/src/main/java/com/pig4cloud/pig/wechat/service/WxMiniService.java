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
package com.pig4cloud.pig.wechat.service;

import com.pig4cloud.pig.wechat.api.entity.WxUser;

import java.util.Map;

/**
 * 小程序服务接口
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface WxMiniService {

	/**
	 * 小程序登录
	 * @param appId AppID
	 * @param code 登录码
	 * @return 登录结果（包含session_key、openid等）
	 */
	Map<String, Object> login(String appId, String code);

	/**
	 * 获取手机号
	 * @param appId AppID
	 * @param code 手机号code
	 * @return 手机号信息
	 */
	Map<String, Object> getPhoneNumber(String appId, String code);

	/**
	 * 发送订阅消息
	 * @param appId AppID
	 * @param openId OpenID
	 * @param templateId 模板ID
	 * @param data 模板数据
	 * @param page 跳转页面
	 * @return 发送结果
	 */
	boolean sendSubscribeMessage(String appId, String openId, String templateId, Map<String, Object> data, String page);

	/**
	 * 生成小程序码
	 * @param appId AppID
	 * @param scene 场景值
	 * @param page 页面路径
	 * @return 小程序码图片URL
	 */
	String generateQrCode(String appId, String scene, String page);

	/**
	 * 解密用户数据
	 * @param appId AppID
	 * @param sessionKey 会话密钥
	 * @param encryptedData 加密数据
	 * @param iv 初始向量
	 * @return 解密后的用户信息
	 */
	WxUser decryptUserInfo(String appId, String sessionKey, String encryptedData, String iv);

}