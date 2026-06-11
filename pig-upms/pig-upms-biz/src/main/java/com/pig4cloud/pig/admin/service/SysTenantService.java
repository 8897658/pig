/*
 *
 *      Copyright (c) 2018-2026, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.pig4cloud.pig.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysTenant;

/**
 * 租户信息表 服务类
 *
 * @author lengleng
 * @date 2026-06-11
 */
public interface SysTenantService extends IService<SysTenant> {

	/**
	 * 根据租户编码查询租户信息
	 * @param code 租户编码
	 * @return 租户信息
	 */
	SysTenant getByCode(String code);

	/**
	 * 根据租户域名查询租户信息
	 * @param domain 租户域名
	 * @return 租户信息
	 */
	SysTenant getByDomain(String domain);

}
