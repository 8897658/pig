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

package com.pig4cloud.pig.common.datapermission.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * <p>
 * 标注在 Mapper 接口的方法上，用于控制数据权限的开启和关闭。 标注后，在执行 SQL 时会自动拼接数据权限过滤条件。
 * </p>
 *
 * @author lengleng
 * @since 4.0.0
 */
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermission {

	/**
	 * 是否开启数据权限，默认开启
	 * @return true 开启，false 关闭
	 */
	boolean enabled() default true;

	/**
	 * 数据权限字段名称，默认为 dept_id
	 * @return 字段名称
	 */
	String deptIdColumn() default "dept_id";

	/**
	 * 别名，用于多表查询时的表别名，默认为空
	 * @return 表别名
	 */
	String alias() default "";

	/**
	 * 是否包含子部门数据权限，默认不包含
	 * @return true 包含子部门，false 仅本部门
	 */
	boolean includeChildren() default false;

}
