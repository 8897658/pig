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
package com.pig4cloud.pig.gateway.service.impl;

import com.pig4cloud.pig.gateway.service.DynamicRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 动态路由服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2026-06-11
 */
@Service
@RequiredArgsConstructor
public class DynamicRouteServiceImpl implements DynamicRouteService {

	private static final Logger log = LoggerFactory.getLogger(DynamicRouteServiceImpl.class);

	private final RouteDefinitionWriter routeDefinitionWriter;

	private final RouteDefinitionLocator routeDefinitionLocator;

	private final ApplicationEventPublisher publisher;

	@Override
	public Mono<Boolean> add(RouteDefinition routeDefinition) {
		return routeDefinitionWriter.save(Mono.just(routeDefinition)).then(Mono.fromRunnable(() -> {
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("添加路由成功: {}", routeDefinition.getId());
		})).thenReturn(true).onErrorResume(e -> {
			log.error("添加路由失败: {}", routeDefinition.getId(), e);
			return Mono.just(false);
		});
	}

	@Override
	public Mono<Boolean> update(RouteDefinition routeDefinition) {
		return delete(routeDefinition.getId()).flatMap(success -> {
			if (success) {
				return add(routeDefinition);
			}
			return Mono.just(false);
		});
	}

	@Override
	public Mono<Boolean> delete(String routeId) {
		return routeDefinitionWriter.delete(Mono.just(routeId)).then(Mono.fromRunnable(() -> {
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("删除路由成功: {}", routeId);
		})).thenReturn(true).onErrorResume(e -> {
			log.error("删除路由失败: {}", routeId, e);
			return Mono.just(false);
		});
	}

	@Override
	public Mono<Boolean> refreshRoutes() {
		return Mono.fromRunnable(() -> {
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("刷新路由成功");
		}).thenReturn(true);
	}

	@Override
	public Flux<RouteDefinition> getRoutes() {
		return routeDefinitionLocator.getRouteDefinitions();
	}

}