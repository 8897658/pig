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
package com.pig4cloud.pig.mobile.service.impl;

import com.pig4cloud.pig.mobile.api.dto.PushMessageDTO;
import com.pig4cloud.pig.mobile.service.PushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 推送服务实现
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushServiceImpl implements PushService {

	@Override
	public boolean send(PushMessageDTO message) {
		log.info("发送推送消息: userId={}, title={}", message.getUserId(), message.getTitle());
		// TODO: 实现推送逻辑（极光推送）
		return true;
	}

	@Override
	public boolean sendToDevice(String deviceId, PushMessageDTO message) {
		log.info("发送推送消息到设备: deviceId={}, title={}", deviceId, message.getTitle());
		// TODO: 实现设备推送逻辑
		return true;
	}

}