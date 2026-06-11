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

package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.entity.SysTenant;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 租户远程服务接口
 *
 * @author lengleng
 * @date 2026-06-11
 */
@FeignClient(contextId = "remoteTenantService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteTenantService {

	/**
	 * 根据租户编码查询租户信息
	 * @param code 租户编码
	 * @return R<SysTenant>
	 */
	@NoToken
	@GetMapping("/tenant/code/{code}")
	R<SysTenant> getByCode(@PathVariable("code") String code);

	/**
	 * 根据租户域名查询租户信息
	 * @param domain 租户域名
	 * @return R<SysTenant>
	 */
	@NoToken
	@GetMapping("/tenant/domain/{domain}")
	R<SysTenant> getByDomain(@PathVariable("domain") String domain);

	/**
	 * 根据租户ID查询租户信息
	 * @param id 租户ID
	 * @return R<SysTenant>
	 */
	@NoToken
	@GetMapping("/tenant/details/{id}")
	R<SysTenant> getById(@PathVariable("id") Long id);

}
