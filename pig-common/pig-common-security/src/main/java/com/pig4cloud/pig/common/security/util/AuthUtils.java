/*
 *    Copyright (c) 2018-2026, lengleng All rights reserved.
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

package com.pig4cloud.pig.common.security.util;

import cn.hutool.core.codec.Base64;
import com.pig4cloud.pig.common.core.exception.BizException;
import com.pig4cloud.pig.common.core.exception.CommonErrorCode;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author lengleng
 * @date 2018/5/13 认证授权相关工具类
 */
@Slf4j
@UtilityClass
public class AuthUtils {

	private final String BASIC_ = "Basic ";

	/**
	 * 从header 请求中的clientId/clientsecect
	 * @param header header中的参数
	 * @throws BizException if the Basic header is not present or is not valid Base64
	 */
	@SneakyThrows
	public String[] extractAndDecodeHeader(String header) {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BizException(CommonErrorCode.TOKEN_INVALID, "Basic 认证令牌解码失败", e);
		}

		String token = new String(decoded, StandardCharsets.UTF_8);

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BizException(CommonErrorCode.TOKEN_INVALID, "无效的 Basic 认证令牌格式");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

	/**
	 * *从header 请求中的clientId/clientsecect
	 * @param request
	 * @return
	 */
	@SneakyThrows
	public String[] extractAndDecodeHeader(HttpServletRequest request) {
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith(BASIC_)) {
			throw new BizException(CommonErrorCode.TOKEN_INVALID, "请求头中client信息为空");
		}

		return extractAndDecodeHeader(header);
	}

}
