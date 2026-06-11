/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
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

package com.pig4cloud.pig.process.service;

import com.pig4cloud.pig.common.core.util.R;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.List;
import java.util.Map;

/**
 * 流程服务接口
 * <p>
 * 提供流程启动、任务处理、流程查询等核心功能。
 *
 * @author lengleng
 * @date 2025-06-11
 */
public interface ProcessService {

	/**
	 * 部署流程定义
	 * @param name 流程名称
	 * @param bpmnXml BPMN XML 内容
	 * @return 部署结果
	 */
	R<Deployment> deploy(String name, String bpmnXml);

	/**
	 * 启动流程实例
	 * @param processDefinitionKey 流程定义KEY
	 * @param businessKey 业务KEY
	 * @param variables 流程变量
	 * @return 流程实例
	 */
	R<ProcessInstance> startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables);

	/**
	 * 完成任务
	 * @param taskId 任务ID
	 * @param variables 任务变量
	 * @return 操作结果
	 */
	R<Boolean> completeTask(String taskId, Map<String, Object> variables);

	/**
	 * 获取用户待办任务列表
	 * @param userId 用户ID
	 * @return 待办任务列表
	 */
	R<List<Task>> getTodoTasks(String userId);

	/**
	 * 获取用户已办任务列表
	 * @param userId 用户ID
	 * @return 已办任务列表
	 */
	R<List<Map<String, Object>>> getDoneTasks(String userId);

	/**
	 * 获取流程定义模型
	 * @param processDefinitionId 流程定义ID
	 * @return BPMN模型
	 */
	R<BpmnModel> getBpmnModel(String processDefinitionId);

	/**
	 * 查询流程实例
	 * @param processInstanceId 流程实例ID
	 * @return 流程实例
	 */
	R<ProcessInstance> getProcessInstance(String processInstanceId);

	/**
	 * 删除流程实例
	 * @param processInstanceId 流程实例ID
	 * @param deleteReason 删除原因
	 * @return 操作结果
	 */
	R<Boolean> deleteProcessInstance(String processInstanceId, String deleteReason);

	/**
	 * 挂起流程实例
	 * @param processInstanceId 流程实例ID
	 * @return 操作结果
	 */
	R<Boolean> suspendProcessInstance(String processInstanceId);

	/**
	 * 激活流程实例
	 * @param processInstanceId 流程实例ID
	 * @return 操作结果
	 */
	R<Boolean> activateProcessInstance(String processInstanceId);

	/**
	 * 获取流程变量
	 * @param processInstanceId 流程实例ID
	 * @param variableName 变量名
	 * @return 变量值
	 */
	R<Object> getProcessVariable(String processInstanceId, String variableName);

	/**
	 * 设置流程变量
	 * @param processInstanceId 流程实例ID
	 * @param variables 变量Map
	 * @return 操作结果
	 */
	R<Boolean> setProcessVariables(String processInstanceId, Map<String, Object> variables);

	/**
	 * 获取任务详情
	 * @param taskId 任务ID
	 * @return 任务详情
	 */
	R<Task> getTask(String taskId);

	/**
	 * 认领任务
	 * @param taskId 任务ID
	 * @param userId 用户ID
	 * @return 操作结果
	 */
	R<Boolean> claimTask(String taskId, String userId);

	/**
	 * 取消认领任务
	 * @param taskId 任务ID
	 * @return 操作结果
	 */
	R<Boolean> unclaimTask(String taskId);

	/**
	 * 转办任务
	 * @param taskId 任务ID
	 * @param userId 目标用户ID
	 * @return 操作结果
	 */
	R<Boolean> transferTask(String taskId, String userId);

	/**
	 * 委派任务
	 * @param taskId 任务ID
	 * @param userId 被委派人ID
	 * @return 操作结果
	 */
	R<Boolean> delegateTask(String taskId, String userId);

	/**
	 * 驳回任务到上一节点
	 * @param taskId 任务ID
	 * @return 操作结果
	 */
	R<Boolean> rejectTask(String taskId);

}
