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

import com.pig4cloud.pig.mobile.api.vo.DeviceVO;
import com.pig4cloud.pig.mobile.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备服务实现
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

	@Override
	public boolean register(DeviceVO device) {
		log.info("注册设备: deviceId={}, deviceType={}", device.getDeviceId(), device.getDeviceType());
		// TODO: 实现设备注册逻辑
		return true;
	}

	@Override
	public List<DeviceVO> listByUserId(Long userId) {
		log.info("获取用户设备列表: userId={}", userId);
		// TODO: 实现获取用户设备列表
		return new ArrayList<>();
	}

	@Override
	public boolean kickOut(Long userId, String deviceId) {
		log.info("踢下线: userId={}, deviceId={}", userId, deviceId);
		// TODO: 实现踢下线逻辑
		return true;
	}

}