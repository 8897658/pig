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

package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysTenant;
import com.pig4cloud.pig.admin.service.SysTenantService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.HasPermission;
import com.pig4cloud.pig.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * 租户管理控制器
 *
 * @author lengleng
 * @date 2026-06-11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Tag(description = "tenant", name = "租户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysTenantController {

	private final SysTenantService sysTenantService;

	/**
	 * 通过ID查询租户信息
	 * @param id 租户ID
	 * @return R
	 */
	@Inner
	@GetMapping("/details/{id}")
	public R<SysTenant> getById(@PathVariable Long id) {
		return R.ok(sysTenantService.getById(id));
	}

	/**
	 * 根据租户编码查询租户信息
	 * @param code 租户编码
	 * @return R
	 */
	@Inner
	@GetMapping("/code/{code}")
	public R<SysTenant> getByCode(@PathVariable String code) {
		return R.ok(sysTenantService.getByCode(code));
	}

	/**
	 * 根据租户域名查询租户信息
	 * @param domain 租户域名
	 * @return R
	 */
	@Inner
	@GetMapping("/domain/{domain}")
	public R<SysTenant> getByDomain(@PathVariable String domain) {
		return R.ok(sysTenantService.getByDomain(domain));
	}

	/**
	 * 分页查询租户信息
	 * @param page 分页对象
	 * @param sysTenant 查询条件
	 * @return R
	 */
	@GetMapping("/page")
	@HasPermission("sys_tenant_view")
	public R getTenantPage(@ParameterObject Page page, @ParameterObject SysTenant sysTenant) {
		return R.ok(sysTenantService.page(page, Wrappers.<SysTenant>lambdaQuery()
			.like(StrUtil.isNotBlank(sysTenant.getName()), SysTenant::getName, sysTenant.getName())
			.like(StrUtil.isNotBlank(sysTenant.getCode()), SysTenant::getCode, sysTenant.getCode())));
	}

	/**
	 * 查询租户信息
	 * @param query 查询条件
	 * @return R
	 */
	@GetMapping("/details")
	public R getDetails(@ParameterObject SysTenant query) {
		return R.ok(sysTenantService.getOne(Wrappers.query(query), false));
	}

	/**
	 * 添加租户
	 * @param sysTenant 租户信息
	 * @return R
	 */
	@SysLog("添加租户")
	@PostMapping
	@HasPermission("sys_tenant_add")
	public R save(@Valid @RequestBody SysTenant sysTenant) {
		return R.ok(sysTenantService.save(sysTenant));
	}

	/**
	 * 修改租户
	 * @param sysTenant 租户信息
	 * @return R
	 */
	@SysLog("修改租户")
	@PutMapping
	@HasPermission("sys_tenant_edit")
	public R update(@Valid @RequestBody SysTenant sysTenant) {
		return R.ok(sysTenantService.updateById(sysTenant));
	}

	/**
	 * 删除租户
	 * @param ids ID数组
	 * @return R
	 */
	@SysLog("删除租户")
	@DeleteMapping
	@HasPermission("sys_tenant_del")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysTenantService.removeBatchByIds(CollUtil.toList(ids)));
	}

}
