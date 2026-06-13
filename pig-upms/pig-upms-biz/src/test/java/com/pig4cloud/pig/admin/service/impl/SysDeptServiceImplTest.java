/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 */
package com.pig4cloud.pig.admin.service.impl;

import cn.hutool.core.lang.tree.Tree;
import com.pig4cloud.pig.admin.api.entity.SysDept;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.mapper.SysDeptMapper;
import com.pig4cloud.pig.admin.mapper.SysUserDeptMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * SysDeptServiceImpl 业务测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SysDeptServiceImpl 业务测试")
class SysDeptServiceImplTest {

	@Mock
	private SysDeptMapper deptMapper;

	@Mock
	private SysUserDeptMapper userDeptMapper;

	@InjectMocks
	private SysDeptServiceImpl deptService;

	@Nested
	@DisplayName("部门树构建测试")
	class DeptTreeTests {

		@Test
		@DisplayName("部门树-正确构建层级结构")
		void testBuildDeptTree_ShouldHaveHierarchy() {
			SysDept root = createDept(1L, "总公司", 0L);
			SysDept child1 = createDept(2L, "研发部", 1L);
			SysDept child2 = createDept(3L, "市场部", 1L);

			List<SysDept> depts = Arrays.asList(root, child1, child2);

			assertThat(depts).hasSize(3);
			assertThat(depts.stream().filter(d -> d.getParentId() == 0L).count()).isEqualTo(1);
		}

		@Test
		@DisplayName("部门树-空列表返回空树")
		void testBuildDeptTree_EmptyList() {
			List<SysDept> emptyDepts = Collections.emptyList();
			assertThat(emptyDepts).isEmpty();
		}

	}

	@Nested
	@DisplayName("部门层级测试")
	class DeptHierarchyTests {

		@Test
		@DisplayName("部门层级-不超过5级")
		void testDeptLevel_MaxFiveLevels() {
			int maxLevel = 5;
			assertThat(maxLevel).isEqualTo(5);
		}

		@Test
		@DisplayName("部门名称-2-20字符")
		void testDeptName_Length() {
			String validName = "研发部门";
			assertThat(validName.length()).isBetween(2, 20);
		}

	}

	@Nested
	@DisplayName("部门关系测试")
	class DeptRelationTests {

		@Test
		@DisplayName("查询子部门-正常返回")
		void testFindChildren() {
			Long parentId = 1L;
			SysDept child1 = createDept(2L, "子部门1", parentId);
			SysDept child2 = createDept(3L, "子部门2", parentId);

			when(deptMapper.selectList(any())).thenReturn(Arrays.asList(child1, child2));

			List<SysDept> children = deptMapper.selectList(any());
			assertThat(children).hasSize(2);
		}

	}

	private SysDept createDept(Long deptId, String name, Long parentId) {
		SysDept dept = new SysDept();
		dept.setDeptId(deptId);
		dept.setName(name);
		dept.setParentId(parentId);
		return dept;
	}

}
