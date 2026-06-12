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

import com.pig4cloud.pig.mobile.api.vo.DeviceVO;

import java.util.List;

/**
 * 设备服务接口
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface DeviceService {

	/**
	 * 注册设备
	 * @param device 设备信息
	 * @return 是否成功
	 */
	boolean register(DeviceVO device);

	/**
	 * 获取用户设备列表
	 * @param userId 用户ID
	 * @return 设备列表
	 */
	List<DeviceVO> listByUserId(Long userId);

	/**
	 * 踢下线
	 * @param userId 用户ID
	 * @param deviceId 设备ID
	 * @return 是否成功
	 */
	boolean kickOut(Long userId, String deviceId);

}