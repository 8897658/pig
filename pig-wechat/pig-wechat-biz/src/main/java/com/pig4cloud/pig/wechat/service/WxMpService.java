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
 * 公众号服务接口
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface WxMpService {

	/**
	 * 处理公众号消息
	 * @param appId AppID
	 * @param requestBody 请求体
	 * @param signature 签名
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @param openid OpenID
	 * @return 响应内容
	 */
	String handleMessage(String appId, String requestBody, String signature, String timestamp, String nonce,
			String openid);

	/**
	 * 发送模板消息
	 * @param appId AppID
	 * @param openId OpenID
	 * @param templateId 模板ID
	 * @param data 模板数据
	 * @return 发送结果
	 */
	boolean sendTemplateMessage(String appId, String openId, String templateId, Map<String, Object> data);

	/**
	 * 同步用户信息
	 * @param appId AppID
	 * @param openId OpenID
	 * @return 用户信息
	 */
	WxUser syncUserInfo(String appId, String openId);

	/**
	 * 获取授权URL
	 * @param appId AppID
	 * @param redirectUri 回调地址
	 * @param scope 授权范围
	 * @param state 状态参数
	 * @return 授权URL
	 */
	String getAuthorizationUrl(String appId, String redirectUri, String scope, String state);

}