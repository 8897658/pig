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
import com.pig4cloud.pig.common.datapermission.handler.DataPermissionHandler;
import com.pig4cloud.pig.common.datapermission.properties.DataPermissionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 数据权限自动配置类
 *
 * @author lengleng
 * @since 4.0.0
 */
@Slf4j
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
	 * 数据权限拦截器
	 * <p>
	 * 注：此拦截器需要在 MybatisPlusInterceptor 中正确排序。 数据权限拦截器应该在多租户、动态表名插件之后， 分页、乐观锁插件之前。
	 * </p>
	 * @param handler 数据权限处理器
	 * @return DataPermissionInterceptor
	 */
	@Bean
	@ConditionalOnMissingBean(DataPermissionInterceptor.class)
	public DataPermissionInterceptor dataPermissionInterceptor(DataPermissionHandler handler) {
		return new DataPermissionInterceptor(handler);
	}

	/**
	 * 将 DataPermissionInterceptor 添加到 MybatisPlusInterceptor
	 * <p>
	 * 注意：此方法会在 pig-common-data 的 MybatisPlusInterceptor 创建之后， 通过 @ConditionalOnBean
	 * 确保已有拦截器存在。 使用 @Autowired 注入拦截器并将其添加到 interceptor 链中。
	 * </p>
	 * @param interceptor MyBatis Plus 拦截器
	 * @param dataPermissionInterceptor 数据权限拦截器
	 */
	@Autowired(required = false)
	@ConditionalOnBean(MybatisPlusInterceptor.class)
	public void configureDataPermissionInterceptor(MybatisPlusInterceptor interceptor,
			DataPermissionInterceptor dataPermissionInterceptor) {
		if (interceptor != null) {
			log.info("Adding DataPermissionInterceptor to MybatisPlusInterceptor chain");
			// 数据权限拦截器应该添加在第一位（多租户之后）
			interceptor.getInterceptors().add(0, dataPermissionInterceptor);
		}
	}

}
