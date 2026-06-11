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
package com.pig4cloud.pig.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 动态路由服务接口
 * </p>
 *
 * @author lengleng
 * @since 2026-06-11
 */
public interface DynamicRouteService {

	/**
	 * 添加路由配置
	 * @param routeDefinition 路由定义
	 * @return 添加结果
	 */
	Mono<Boolean> add(RouteDefinition routeDefinition);

	/**
	 * 更新路由配置
	 * @param routeDefinition 路由定义
	 * @return 更新结果
	 */
	Mono<Boolean> update(RouteDefinition routeDefinition);

	/**
	 * 删除路由配置
	 * @param routeId 路由ID
	 * @return 删除结果
	 */
	Mono<Boolean> delete(String routeId);

	/**
	 * 刷新所有路由配置
	 * @return 刷新结果
	 */
	Mono<Boolean> refreshRoutes();

	/**
	 * 获取所有路由配置
	 * @return 路由配置列表
	 */
	Flux<RouteDefinition> getRoutes();

}