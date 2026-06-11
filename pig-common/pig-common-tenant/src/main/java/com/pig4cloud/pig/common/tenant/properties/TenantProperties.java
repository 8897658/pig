/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
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

package com.pig4cloud.pig.common.tenant.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户配置属性
 * <p>
 * 配置前缀: pig.tenant
 *
 * @author lengleng
 * @date 2026-06-11
 */
@ConfigurationProperties(prefix = "pig.tenant")
public class TenantProperties {

	/**
	 * 是否开启多租户
	 */
	private boolean enabled = false;

	/**
	 * 租户ID列名，默认为 tenant_id
	 */
	private String column = "tenant_id";

	/**
	 * 需要忽略的表名列表（不进行租户过滤）
	 */
	private List<String> ignoreTables = new ArrayList<>();

	/**
	 * 获取是否开启多租户
	 * @return true 表示开启
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 设置是否开启多租户
	 * @param enabled 是否开启
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 获取租户ID列名
	 * @return 列名
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * 设置租户ID列名
	 * @param column 列名
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 获取忽略的表名列表
	 * @return 表名列表
	 */
	public List<String> getIgnoreTables() {
		return ignoreTables;
	}

	/**
	 * 设置忽略的表名列表
	 * @param ignoreTables 表名列表
	 */
	public void setIgnoreTables(List<String> ignoreTables) {
		this.ignoreTables = ignoreTables;
	}

}