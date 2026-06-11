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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.gateway.service.DynamicRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>
 * 动态路由服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2026-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicRouteServiceImpl implements DynamicRouteService {

	private final RouteDefinitionWriter routeDefinitionWriter;

	private final RouteDefinitionLocator routeDefinitionLocator;

	private final ApplicationEventPublisher publisher;

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Boolean> add(RouteDefinition routeDefinition) {
		if (routeDefinition == null || !StringUtils.hasText(routeDefinition.getId())) {
			return Mono.just(false);
		}

		log.info("Adding route: {}", routeDefinition.getId());
		return routeDefinitionWriter.save(Mono.just(routeDefinition))
			.then(Mono.fromRunnable(() -> {
				publisher.publishEvent(new RefreshRoutesEvent(this));
				log.info("Route added and refreshed: {}", routeDefinition.getId());
			}))
			.thenReturn(true)
			.onErrorResume(e -> {
				log.error("Failed to add route: {}", routeDefinition.getId(), e);
				return Mono.just(false);
			});
	}

	@Override
	public Mono<Boolean> update(RouteDefinition routeDefinition) {
		if (routeDefinition == null || !StringUtils.hasText(routeDefinition.getId())) {
			return Mono.just(false);
		}

		log.info("Updating route: {}", routeDefinition.getId());
		return delete(routeDefinition.getId())
			.flatMap(success -> {
				if (success) {
					return add(routeDefinition);
				}
				return Mono.just(false);
			})
			.onErrorResume(e -> {
				log.error("Failed to update route: {}", routeDefinition.getId(), e);
				return Mono.just(false);
			});
	}

	@Override
	public Mono<Boolean> delete(String routeId) {
		if (!StringUtils.hasText(routeId)) {
			return Mono.just(false);
		}

		log.info("Deleting route: {}", routeId);
		return routeDefinitionWriter.delete(Mono.just(routeId))
			.then(Mono.fromRunnable(() -> {
				publisher.publishEvent(new RefreshRoutesEvent(this));
				log.info("Route deleted and refreshed: {}", routeId);
			}))
			.thenReturn(true)
			.onErrorResume(e -> {
				log.error("Failed to delete route: {}", routeId, e);
				return Mono.just(false);
			});
	}

	@Override
	public Mono<Boolean> refreshRoutes() {
		log.info("Refreshing all routes");
		return Mono.fromRunnable(() -> {
			publisher.publishEvent(new RefreshRoutesEvent(this));
			log.info("All routes refreshed");
		})
		.thenReturn(true)
		.onErrorResume(e -> {
			log.error("Failed to refresh routes", e);
			return Mono.just(false);
		});
	}

	@Override
	public Flux<RouteDefinition> getRoutes() {
		return routeDefinitionLocator.getRouteDefinitions()
			.doOnNext(route -> log.debug("Route: id={}, uri={}", route.getId(), route.getUri()));
	}

	/**
	 * 解析路由定义JSON字符串
	 * @param routeDefinitionJson 路由定义JSON
	 * @return RouteDefinition 对象
	 */
	public Mono<RouteDefinition> parseRouteDefinition(String routeDefinitionJson) {
		if (!StringUtils.hasText(routeDefinitionJson)) {
			return Mono.empty();
		}

		return Mono.fromCallable(() -> objectMapper.readValue(routeDefinitionJson, RouteDefinition.class))
			.onErrorResume(e -> {
				log.error("Failed to parse route definition: {}", routeDefinitionJson, e);
				return Mono.empty();
			});
	}

	/**
	 * 批量加载路由配置
	 * @param routeDefinitions 路由定义列表
	 * @return 加载结果
	 */
	public Mono<Boolean> loadRoutes(List<RouteDefinition> routeDefinitions) {
		if (routeDefinitions == null || routeDefinitions.isEmpty()) {
			return Mono.just(true);
		}

		log.info("Loading {} routes", routeDefinitions.size());
		return Flux.fromIterable(routeDefinitions)
			.flatMap(this::add)
			.all(result -> result)
			.doOnSuccess(success -> log.info("Routes loaded: {}", success))
			.onErrorResume(e -> {
				log.error("Failed to load routes", e);
				return Mono.just(false);
			});
	}

}