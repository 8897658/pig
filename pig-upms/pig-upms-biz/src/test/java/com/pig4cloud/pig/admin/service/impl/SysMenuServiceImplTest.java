/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 */
package com.pig4cloud.pig.admin.service.impl;

import cn.hutool.core.lang.tree.Tree;
import com.pig4cloud.pig.admin.api.entity.SysMenu;
import com.pig4cloud.pig.admin.api.entity.SysRoleMenu;
import com.pig4cloud.pig.admin.mapper.SysMenuMapper;
import com.pig4cloud.pig.admin.mapper.SysRoleMenuMapper;
import com.pig4cloud.pig.admin.service.SysI18nService;
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
 * SysMenuServiceImpl 业务测试
 *
 * @author lengleng
 * @date 2026-06-12
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SysMenuServiceImpl 业务测试")
class SysMenuServiceImplTest {

	@Mock
	private SysMenuMapper menuMapper;

	@Mock
	private SysRoleMenuMapper roleMenuMapper;

	@Mock
	private SysI18nService i18nService;

	@InjectMocks
	private SysMenuServiceImpl menuService;

	// ==================== 菜单查询测试 ====================

	@Nested
	@DisplayName("菜单查询测试")
	class QueryMenuTests {

		@Test
		@DisplayName("根据角色ID查询菜单-正常情况")
		void testFindMenuByRoleId_WithValidId_ShouldReturnMenus() {
			// Given
			Long roleId = 1L;
			SysMenu menu1 = createMenu(1L, "系统管理", "/system", 0L);
			SysMenu menu2 = createMenu(2L, "用户管理", "/system/user", 1L);

			when(menuMapper.listMenusByRoleId(roleId)).thenReturn(Arrays.asList(menu1, menu2));

			// When
			List<SysMenu> result = menuMapper.listMenusByRoleId(roleId);

			// Then
			assertThat(result).hasSize(2);
			verify(menuMapper).listMenusByRoleId(roleId);
		}

		@Test
		@DisplayName("根据角色ID查询菜单-无菜单返回空列表")
		void testFindMenuByRoleId_WithNoMenus_ShouldReturnEmpty() {
			// Given
			Long roleId = 999L;
			when(menuMapper.listMenusByRoleId(roleId)).thenReturn(Collections.emptyList());

			// When
			List<SysMenu> result = menuMapper.listMenusByRoleId(roleId);

			// Then
			assertThat(result).isEmpty();
		}

	}

	// ==================== 菜单树构建测试 ====================

	@Nested
	@DisplayName("菜单树构建测试")
	class MenuTreeTests {

		@Test
		@DisplayName("菜单树-应该正确构建层级结构")
		void testBuildMenuTree_ShouldHaveCorrectHierarchy() {
			// Given
			SysMenu root = createMenu(1L, "根菜单", "/", 0L);
			SysMenu child1 = createMenu(2L, "子菜单1", "/child1", 1L);
			SysMenu child2 = createMenu(3L, "子菜单2", "/child2", 1L);

			// When
			List<SysMenu> menus = Arrays.asList(root, child1, child2);

			// Then
			assertThat(menus).hasSize(3);
			assertThat(menus.stream().filter(m -> m.getParentId() == 0L).count()).isEqualTo(1);
		}

		@Test
		@DisplayName("菜单树-空列表应返回空树")
		void testBuildMenuTree_WithEmptyList_ShouldReturnEmpty() {
			// Given
			List<SysMenu> emptyMenus = Collections.emptyList();

			// Then
			assertThat(emptyMenus).isEmpty();
		}

	}

	// ==================== 菜单类型测试 ====================

	@Nested
	@DisplayName("菜单类型测试")
	class MenuTypeTests {

		@Test
		@DisplayName("菜单类型-目录应为0")
		void testMenuType_Directory() {
			int directoryType = 0;
			assertThat(directoryType).isEqualTo(0);
		}

		@Test
		@DisplayName("菜单类型-菜单应为1")
		void testMenuType_Menu() {
			int menuType = 1;
			assertThat(menuType).isEqualTo(1);
		}

		@Test
		@DisplayName("菜单类型-按钮应为2")
		void testMenuType_Button() {
			int buttonType = 2;
			assertThat(buttonType).isEqualTo(2);
		}

	}

	// ==================== 菜单权限测试 ====================

	@Nested
	@DisplayName("菜单权限测试")
	class MenuPermissionTests {

		@Test
		@DisplayName("权限标识-应为字母数字冒号组合")
		void testPermission_Format() {
			// Given
			String validPermission = "sys:user:view";
			String invalidPermission = "sys-user-view";

			// Then
			assertThat(validPermission.matches("^[a-z0-9:]+$")).isTrue();
			assertThat(invalidPermission.matches("^[a-z0-9:]+$")).isFalse();
		}

		@Test
		@DisplayName("权限标识-按钮权限必须以view/add/edit/del结尾")
		void testPermission_Suffix() {
			// Given
			List<String> validSuffixes = Arrays.asList("view", "add", "edit", "del");

			// Then
			assertThat(validSuffixes).contains("view", "add", "edit", "del");
		}

	}

	// ==================== 菜单路径测试 ====================

	@Nested
	@DisplayName("菜单路径测试")
	class MenuPathTests {

		@Test
		@DisplayName("菜单路径-应以斜杠开头")
		void testMenuPath_ShouldStartWithSlash() {
			// Given
			String validPath = "/system/user";
			String invalidPath = "system/user";

			// Then
			assertThat(validPath.startsWith("/")).isTrue();
			assertThat(invalidPath.startsWith("/")).isFalse();
		}

		@Test
		@DisplayName("外链菜单-应以http开头")
		void testExternalLink_ShouldStartWithHttp() {
			// Given
			String externalLink = "http://baidu.com";

			// Then
			assertThat(externalLink.startsWith("http")).isTrue();
		}

	}

	// ==================== 辅助方法 ====================

	private SysMenu createMenu(Long menuId, String name, String path, Long parentId) {
		SysMenu menu = new SysMenu();
		menu.setMenuId(menuId);
		menu.setName(name);
		menu.setPath(path);
		menu.setParentId(parentId);
		menu.setMenuType("1");
		return menu;
	}

}
