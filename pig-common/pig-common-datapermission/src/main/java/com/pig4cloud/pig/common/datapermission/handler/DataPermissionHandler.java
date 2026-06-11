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

package com.pig4cloud.pig.common.datapermission.handler;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.pig4cloud.pig.common.datapermission.annotation.DataPermission;
import com.pig4cloud.pig.common.datapermission.properties.DataPermissionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;

/**
 * 数据权限 SQL 处理器
 * <p>
 * 基于 MyBatis Plus InnerInterceptor 实现，在 SQL 执行前自动拼接数据权限过滤条件。
 * </p>
 *
 * @author lengleng
 * @since 4.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class DataPermissionHandler extends JsqlParserSupport implements InnerInterceptor {

	/**
	 * 数据权限配置属性
	 */
	private final DataPermissionProperties properties;

	/**
	 * 数据权限上下文，用于存储当前线程的数据权限信息
	 */
	private static final ThreadLocal<DataPermissionContext> CONTEXT = new ThreadLocal<>();

	/**
	 * 设置数据权限上下文
	 * @param deptIds 部门ID集合
	 * @param isSuperAdmin 是否超级管理员
	 */
	public static void setContext(Set<Long> deptIds, boolean isSuperAdmin) {
		CONTEXT.set(new DataPermissionContext(deptIds, isSuperAdmin));
	}

	/**
	 * 清除数据权限上下文
	 */
	public static void clearContext() {
		CONTEXT.remove();
	}

	/**
	 * 获取数据权限上下文
	 * @return 数据权限上下文
	 */
	public static DataPermissionContext getContext() {
		return CONTEXT.get();
	}

	@Override
	public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
			ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		// 检查是否开启数据权限
		if (!properties.isEnabled()) {
			return;
		}

		// 获取数据权限上下文
		DataPermissionContext context = getContext();
		if (context == null || context.isSuperAdmin()) {
			return;
		}

		// 检查是否忽略的 Mapper 方法
		String mapperMethod = ms.getId();
		for (String ignoreMethod : properties.getIgnoreMapperMethods()) {
			if (mapperMethod.contains(ignoreMethod)) {
				return;
			}
		}

		// 解析并处理 SQL
		PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
		mpBs.sql(this.parserSingle(mpBs.sql(), null));
	}

	@Override
	protected Select processSelect(Select select, int index, String sql, Object obj) {
		PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
		Expression where = plainSelect.getWhere();

		// 构建数据权限过滤条件
		Expression dataPermissionExpression = buildDataPermissionExpression(plainSelect);
		if (dataPermissionExpression == null) {
			return select;
		}

		// 拼接数据权限条件
		if (where == null) {
			plainSelect.setWhere(dataPermissionExpression);
		}
		else {
			plainSelect.setWhere(new AndExpression(where, dataPermissionExpression));
		}

		return select;
	}

	/**
	 * 构建数据权限过滤表达式
	 * @param plainSelect 查询语句
	 * @return 数据权限过滤表达式
	 */
	private Expression buildDataPermissionExpression(PlainSelect plainSelect) {
		DataPermissionContext context = getContext();
		if (context == null || context.isSuperAdmin()) {
			return null;
		}

		Set<Long> deptIds = context.getDeptIds();
		if (deptIds == null || deptIds.isEmpty()) {
			return null;
		}

		// 获取数据权限字段名
		String deptIdColumn = properties.getDeptIdColumn();

		// 构建字段引用
		Column column = new Column(deptIdColumn);

		// 构建过滤条件：dept_id IN (deptIds)
		if (deptIds.size() == 1) {
			EqualsTo equalsTo = new EqualsTo();
			equalsTo.setLeftExpression(column);
			equalsTo.setRightExpression(new LongValue(deptIds.iterator().next()));
			return equalsTo;
		}
		else {
			InExpression inExpression = new InExpression();
			inExpression.setLeftExpression(column);
			ExpressionList<LongValue> expressionList = new ExpressionList<>();
			for (Long deptId : deptIds) {
				expressionList.add(new LongValue(deptId));
			}
			inExpression.setRightExpression(expressionList);
			return inExpression;
		}
	}

	/**
	 * 数据权限上下文信息
	 */
	@RequiredArgsConstructor
	public static class DataPermissionContext {

		/**
		 * 部门ID集合
		 */
		private final Set<Long> deptIds;

		/**
		 * 是否超级管理员
		 */
		private final boolean isSuperAdmin;

		public Set<Long> getDeptIds() {
			return deptIds;
		}

		public boolean isSuperAdmin() {
			return isSuperAdmin;
		}

	}

}
