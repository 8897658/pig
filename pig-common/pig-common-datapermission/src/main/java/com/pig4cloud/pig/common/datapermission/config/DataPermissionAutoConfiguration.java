/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.common.datapermission.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.pig4cloud.pig.common.datapermission.handler.DataPermissionHandler;
import com.pig4cloud.pig.common.datapermission.properties.DataPermissionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限自动配置类
 *
 * @author lengleng
 * @since 4.0.0
 */
@RequiredArgsConstructor
@AutoConfiguration
@EnableConfigurationProperties(DataPermissionProperties.class)
@ConditionalOnProperty(prefix = DataPermissionProperties.PREFIX, name = "enabled", havingValue = "true",
		matchIfMissing = true)
public class DataPermissionAutoConfiguration {

	private final DataPermissionProperties properties;

	/**
	 * 数据权限处理器
	 * @return DataPermissionHandler
	 */
	@Bean
	@ConditionalOnMissingBean
	public DataPermissionHandler dataPermissionHandler() {
		return new DataPermissionHandler(properties);
	}

	/**
	 * MyBatis Plus 拦截器配置
	 * <p>
	 * 注意：如果项目中已有 MybatisPlusInterceptor，请将 DataPermissionHandler
	 * 添加到已有的拦截器中，而不是创建新的拦截器。
	 * </p>
	 * @param handler 数据权限处理器
	 * @return MybatisPlusInterceptor
	 */
	@Bean
	@ConditionalOnMissingBean(MybatisPlusInterceptor.class)
	public MybatisPlusInterceptor mybatisPlusInterceptor(DataPermissionHandler handler) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		List<InnerInterceptor> innerInterceptors = new ArrayList<>();

		// 添加数据权限拦截器
		DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
		dataPermissionInterceptor.setDataPermissionHandler((loggedInUser -> {
			// 这里可以获取当前用户的部门ID信息
			// 实际使用时，需要结合 SecurityUtils 获取当前用户信息
			DataPermissionHandler.DataPermissionContext context = DataPermissionHandler.getContext();
			if (context == null || context.isSuperAdmin()) {
				return null;
			}
			return null; // 返回 null 表示不使用默认的数据权限处理
		}));

		innerInterceptors.add(dataPermissionInterceptor);
		interceptor.setInterceptors(innerInterceptors);

		return interceptor;
	}

}
