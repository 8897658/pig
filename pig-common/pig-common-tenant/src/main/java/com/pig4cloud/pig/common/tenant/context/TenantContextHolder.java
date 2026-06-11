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

package com.pig4cloud.pig.common.tenant.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 租户上下文持有者
 * <p>
 * 使用 TTL (Transmittable ThreadLocal) 实现租户信息的线程隔离，
 * 确保在异步线程池中也能正确传递租户信息
 *
 * @author lengleng
 * @date 2026-06-11
 */
public final class TenantContextHolder {

	/**
	 * 租户ID的上下文持有者，使用 TTL 支持线程池传递
	 */
	private static final ThreadLocal<String> TENANT_ID_HOLDER = new TransmittableThreadLocal<>();

	/**
	 * 私有构造函数，防止实例化
	 */
	private TenantContextHolder() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * 设置当前租户ID
	 * @param tenantId 租户ID
	 */
	public static void setTenantId(String tenantId) {
		TENANT_ID_HOLDER.set(tenantId);
	}

	/**
	 * 获取当前租户ID
	 * @return 当前租户ID，如果未设置则返回 null
	 */
	public static String getTenantId() {
		return TENANT_ID_HOLDER.get();
	}

	/**
	 * 清除当前租户ID
	 */
	public static void clear() {
		TENANT_ID_HOLDER.remove();
	}

	/**
	 * 判断当前是否设置了租户ID
	 * @return true 表示已设置租户ID
	 */
	public static boolean hasTenant() {
		return TENANT_ID_HOLDER.get() != null;
	}

}