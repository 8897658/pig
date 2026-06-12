/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
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

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

/**
 * priority 标头配置
 *
 * @author lengleng
 * @date 2025/04/16
 */
@ControllerAdvice
public class PriorityHeaderInitBinder {

	/**
	 * 初始化绑定器
	 * <p>
	 * 注意: addHeaderPredicate 方法在 Spring Framework 6.2+ 中可用。 当前项目使用 Spring Boot 4.0.6
	 * (Spring Framework 6.1.13)，此功能暂时禁用。 升级到 Spring Boot 4.1+ (Spring Framework 6.2+)
	 * 后可启用。
	 * @param binder 绑定规则
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// TODO: 升级 Spring Framework 6.2 后启用
		// binder.addHeaderPredicate(header -> false);
	}

}
