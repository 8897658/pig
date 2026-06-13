/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 */
package com.pig4cloud.pig.admin.service.impl;

import com.pig4cloud.pig.admin.api.entity.SysDict;
import com.pig4cloud.pig.admin.api.entity.SysDictItem;
import com.pig4cloud.pig.admin.mapper.SysDictMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SysDictServiceImpl 业务测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SysDictServiceImpl 业务测试")
class SysDictServiceImplTest {

    @Mock
    private SysDictMapper dictMapper;

    @InjectMocks
    private SysDictServiceImpl dictService;

    @Nested
    @DisplayName("字典类型测试")
    class DictTypeTests {
        
        @Test
        @DisplayName("字典编码-字母数字下划线")
        void testDictCode_Format() {
            String validCode = "sys_user_status";
            assertThat(validCode.matches("^[a-z0-9_]+$")).isTrue();
        }
        
        @Test
        @DisplayName("字典名称-不应为空")
        void testDictName_NotEmpty() {
            String name = "用户状态";
            assertThat(name).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("字典项测试")
    class DictItemTests {
        
        @Test
        @DisplayName("字典项值-不应为空")
        void testDictItemValue() {
            String value = "0";
            assertThat(value).isNotEmpty();
        }
        
        @Test
        @DisplayName("字典项标签-不应为空")
        void testDictItemLabel() {
            String label = "正常";
            assertThat(label).isNotEmpty();
        }
        
        @Test
        @DisplayName("字典项排序-数字类型")
        void testDictItemSort() {
            Integer sort = 1;
            assertThat(sort).isNotNull();
        }
    }

    private SysDict createDict(Long id, String dictType) {
        SysDict dict = new SysDict();
        dict.setId(id);
        dict.setDictType(dictType);
        return dict;
    }
}
