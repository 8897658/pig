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

package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysTenant;
import com.pig4cloud.pig.admin.mapper.SysTenantMapper;
import com.pig4cloud.pig.admin.service.SysTenantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 租户信息表 服务实现类
 *
 * @author lengleng
 * @date 2026-06-11
 */
@Service
@AllArgsConstructor
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

	/**
	 * 根据租户编码查询租户信息
	 * @param code 租户编码
	 * @return 租户信息
	 */
	@Override
	public SysTenant getByCode(String code) {
		return this.getOne(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getCode, code));
	}

	/**
	 * 根据租户域名查询租户信息
	 * @param domain 租户域名
	 * @return 租户信息
	 */
	@Override
	public SysTenant getByDomain(String domain) {
		return this.getOne(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getDomain, domain));
	}

}
