/*
 * Copyright (c) 2018-2026, lengleng All rights reserved.
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
package com.pig4cloud.pig.common.core.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * 监控指标配置
 * <p>
 * 配置 Prometheus 指标采集，用于系统可观测性
 * <p>
 * 通过 application.yml 配置: management: metrics: tags: application:
 * ${spring.application.name} env: ${spring.profiles.active:default}
 *
 * @author lengleng
 * @date 2026-06-13
 */
@Configuration
@ConditionalOnClass(MeterRegistry.class)
public class MonitoringConfig {

	// 指标配置通过 application.yml 管理，避免硬编码

}