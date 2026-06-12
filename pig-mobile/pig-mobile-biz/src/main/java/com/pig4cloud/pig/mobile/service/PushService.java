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
package com.pig4cloud.pig.mobile.service;

import com.pig4cloud.pig.mobile.api.dto.PushMessageDTO;

/**
 * 推送服务接口
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface PushService {

	/**
	 * 发送推送消息
	 * @param message 推送消息
	 * @return 是否成功
	 */
	boolean send(PushMessageDTO message);

	/**
	 * 发送推送消息到指定设备
	 * @param deviceId 设备ID
	 * @param message 推送消息
	 * @return 是否成功
	 */
	boolean sendToDevice(String deviceId, PushMessageDTO message);

}