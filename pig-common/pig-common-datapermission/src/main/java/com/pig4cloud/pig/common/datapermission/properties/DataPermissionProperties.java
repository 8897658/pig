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

package com.pig4cloud.pig.common.datapermission.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据权限配置属性
 *
 * @author lengleng
 * @since 4.0.0
 */
@Getter
@Setter
@ConfigurationProperties(DataPermissionProperties.PREFIX)
public class DataPermissionProperties {

	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "security.data.permission";

	/**
	 * 是否开启数据权限，默认开启
	 */
	private boolean enabled = true;

	/**
	 * 数据权限字段名称，默认为 dept_id
	 */
	private String deptIdColumn = "dept_id";

	/**
	 * 默认是否包含子部门数据权限，默认不包含
	 */
	private boolean includeChildren = false;

	/**
	 * 超级管理员角色编码，拥有所有数据权限
	 */
	private String superAdminRole = "ROLE_ADMIN";

	/**
	 * 忽略数据权限的 Mapper 方法名称列表
	 */
	private List<String> ignoreMapperMethods = new ArrayList<>();

	/**
	 * 忽略数据权限的表名列表
	 */
	private List<String> ignoreTables = new ArrayList<>();

}
