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

package com.pig4cloud.pig.process.service.impl;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.common.tenant.context.TenantContextHolder;
import com.pig4cloud.pig.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 流程服务实现类
 * <p>
 * 基于 Flowable 引擎实现流程的启动、任务处理、查询等核心功能， 支持多租户数据隔离。
 *
 * @author lengleng
 * @date 2025-06-11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

	private final RepositoryService repositoryService;

	private final RuntimeService runtimeService;

	private final TaskService taskService;

	private final HistoryService historyService;

	private final ProcessEngine processEngine;

	/**
	 * 获取当前租户ID
	 * @return 租户ID
	 */
	private String getCurrentTenantId() {
		String tenantId = TenantContextHolder.getTenantId();
		return StrUtil.isBlank(tenantId) ? "default" : tenantId;
	}

	/**
	 * 获取当前用户ID
	 * @return 用户ID
	 */
	private String getCurrentUserId() {
		try {
			var user = SecurityUtils.getUser();
			return user != null ? String.valueOf(user.getId()) : "system";
		}
		catch (Exception e) {
			log.warn("获取当前用户ID失败", e);
			return "system";
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Deployment> deploy(String name, String bpmnXml) {
		try {
			String tenantId = getCurrentTenantId();

			Deployment deployment = repositoryService.createDeployment()
				.name(name)
				.tenantId(tenantId)
				.addInputStream(name + ".bpmn20.xml",
						new ByteArrayInputStream(bpmnXml.getBytes(StandardCharsets.UTF_8)))
				.deploy();

			log.info("流程部署成功: name={}, deploymentId={}, tenantId={}", name, deployment.getId(), tenantId);
			return R.ok(deployment);
		}
		catch (Exception e) {
			log.error("流程部署失败: name={}", name, e);
			return R.failed("流程部署失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<ProcessInstance> startProcess(String processDefinitionKey, String businessKey,
			Map<String, Object> variables) {
		try {
			String tenantId = getCurrentTenantId();
			String userId = getCurrentUserId();

			// 设置流程发起人
			if (variables == null) {
				variables = new HashMap<>();
			}
			variables.put("initiator", userId);

			ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
				.processDefinitionKey(processDefinitionKey)
				.businessKey(businessKey)
				.variables(variables)
				.tenantId(tenantId)
				.start();

			log.info("流程启动成功: processDefinitionKey={}, businessKey={}, processInstanceId={}, tenantId={}",
					processDefinitionKey, businessKey, processInstance.getId(), tenantId);

			return R.ok(processInstance);
		}
		catch (Exception e) {
			log.error("流程启动失败: processDefinitionKey={}, businessKey={}", processDefinitionKey, businessKey, e);
			return R.failed("流程启动失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> completeTask(String taskId, Map<String, Object> variables) {
		try {
			String userId = getCurrentUserId();

			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				return R.failed("任务不存在: " + taskId);
			}

			// 设置审批人
			if (variables == null) {
				variables = new HashMap<>();
			}
			variables.put("approver", userId);

			taskService.complete(taskId, variables);

			log.info("任务完成: taskId={}, userId={}", taskId, userId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("任务完成失败: taskId={}", taskId, e);
			return R.failed("任务完成失败: " + e.getMessage());
		}
	}

	@Override
	public R<List<Task>> getTodoTasks(String userId) {
		try {
			String tenantId = getCurrentTenantId();

			List<Task> tasks = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.taskTenantId(tenantId)
				.orderByTaskCreateTime()
				.desc()
				.list();

			log.info("获取待办任务: userId={}, count={}, tenantId={}", userId, tasks.size(), tenantId);
			return R.ok(tasks);
		}
		catch (Exception e) {
			log.error("获取待办任务失败: userId={}", userId, e);
			return R.failed("获取待办任务失败: " + e.getMessage());
		}
	}

	@Override
	public R<List<Map<String, Object>>> getDoneTasks(String userId) {
		try {
			String tenantId = getCurrentTenantId();

			List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
				.taskAssignee(userId)
				.taskTenantId(tenantId)
				.finished()
				.orderByHistoricTaskInstanceEndTime()
				.desc()
				.list();

			List<Map<String, Object>> result = new ArrayList<>();
			for (HistoricTaskInstance task : historicTasks) {
				Map<String, Object> taskMap = new HashMap<>();
				taskMap.put("taskId", task.getId());
				taskMap.put("taskName", task.getName());
				taskMap.put("processInstanceId", task.getProcessInstanceId());
				taskMap.put("processDefinitionId", task.getProcessDefinitionId());
				taskMap.put("startTime", task.getStartTime());
				taskMap.put("endTime", task.getEndTime());
				taskMap.put("deleteReason", task.getDeleteReason());
				result.add(taskMap);
			}

			log.info("获取已办任务: userId={}, count={}, tenantId={}", userId, result.size(), tenantId);
			return R.ok(result);
		}
		catch (Exception e) {
			log.error("获取已办任务失败: userId={}", userId, e);
			return R.failed("获取已办任务失败: " + e.getMessage());
		}
	}

	@Override
	public R<BpmnModel> getBpmnModel(String processDefinitionId) {
		try {
			BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
			if (bpmnModel == null) {
				return R.failed("流程定义不存在: " + processDefinitionId);
			}
			return R.ok(bpmnModel);
		}
		catch (Exception e) {
			log.error("获取流程定义模型失败: processDefinitionId={}", processDefinitionId, e);
			return R.failed("获取流程定义模型失败: " + e.getMessage());
		}
	}

	@Override
	public R<ProcessInstance> getProcessInstance(String processInstanceId) {
		try {
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();

			if (processInstance == null) {
				return R.failed("流程实例不存在: " + processInstanceId);
			}
			return R.ok(processInstance);
		}
		catch (Exception e) {
			log.error("获取流程实例失败: processInstanceId={}", processInstanceId, e);
			return R.failed("获取流程实例失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> deleteProcessInstance(String processInstanceId, String deleteReason) {
		try {
			runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
			log.info("删除流程实例: processInstanceId={}, reason={}", processInstanceId, deleteReason);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("删除流程实例失败: processInstanceId={}", processInstanceId, e);
			return R.failed("删除流程实例失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> suspendProcessInstance(String processInstanceId) {
		try {
			runtimeService.suspendProcessInstanceById(processInstanceId);
			log.info("挂起流程实例: processInstanceId={}", processInstanceId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("挂起流程实例失败: processInstanceId={}", processInstanceId, e);
			return R.failed("挂起流程实例失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> activateProcessInstance(String processInstanceId) {
		try {
			runtimeService.activateProcessInstanceById(processInstanceId);
			log.info("激活流程实例: processInstanceId={}", processInstanceId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("激活流程实例失败: processInstanceId={}", processInstanceId, e);
			return R.failed("激活流程实例失败: " + e.getMessage());
		}
	}

	@Override
	public R<Object> getProcessVariable(String processInstanceId, String variableName) {
		try {
			Object value = runtimeService.getVariable(processInstanceId, variableName);
			return R.ok(value);
		}
		catch (Exception e) {
			log.error("获取流程变量失败: processInstanceId={}, variableName={}", processInstanceId, variableName, e);
			return R.failed("获取流程变量失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> setProcessVariables(String processInstanceId, Map<String, Object> variables) {
		try {
			runtimeService.setVariables(processInstanceId, variables);
			log.info("设置流程变量: processInstanceId={}", processInstanceId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("设置流程变量失败: processInstanceId={}", processInstanceId, e);
			return R.failed("设置流程变量失败: " + e.getMessage());
		}
	}

	@Override
	public R<Task> getTask(String taskId) {
		try {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				return R.failed("任务不存在: " + taskId);
			}
			return R.ok(task);
		}
		catch (Exception e) {
			log.error("获取任务详情失败: taskId={}", taskId, e);
			return R.failed("获取任务详情失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> claimTask(String taskId, String userId) {
		try {
			taskService.claim(taskId, userId);
			log.info("认领任务: taskId={}, userId={}", taskId, userId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("认领任务失败: taskId={}, userId={}", taskId, userId, e);
			return R.failed("认领任务失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> unclaimTask(String taskId) {
		try {
			taskService.unclaim(taskId);
			log.info("取消认领任务: taskId={}", taskId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("取消认领任务失败: taskId={}", taskId, e);
			return R.failed("取消认领任务失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> transferTask(String taskId, String userId) {
		try {
			taskService.setAssignee(taskId, userId);
			log.info("转办任务: taskId={}, userId={}", taskId, userId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("转办任务失败: taskId={}, userId={}", taskId, userId, e);
			return R.failed("转办任务失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> delegateTask(String taskId, String userId) {
		try {
			taskService.delegateTask(taskId, userId);
			log.info("委派任务: taskId={}, userId={}", taskId, userId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("委派任务失败: taskId={}, userId={}", taskId, userId, e);
			return R.failed("委派任务失败: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R<Boolean> rejectTask(String taskId) {
		try {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				return R.failed("任务不存在: " + taskId);
			}

			// 获取当前活动节点
			String currentActivityId = task.getTaskDefinitionKey();
			String processInstanceId = task.getProcessInstanceId();

			// 获取上一个用户任务节点
			List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.finished()
				.orderByHistoricTaskInstanceEndTime()
				.desc()
				.list();

			if (historicTasks.isEmpty()) {
				return R.failed("无法驳回：没有找到上一个任务节点");
			}

			// 找到上一个用户任务
			String targetActivityId = null;
			for (HistoricTaskInstance historicTask : historicTasks) {
				if (!historicTask.getTaskDefinitionKey().equals(currentActivityId)) {
					targetActivityId = historicTask.getTaskDefinitionKey();
					break;
				}
			}

			if (targetActivityId == null) {
				return R.failed("无法驳回：没有找到可驳回的目标节点");
			}

			// 执行驳回操作
			runtimeService.createChangeActivityStateBuilder()
				.processInstanceId(processInstanceId)
				.moveActivityIdTo(currentActivityId, targetActivityId)
				.changeState();

			log.info("驳回任务成功: taskId={}, from={}, to={}", taskId, currentActivityId, targetActivityId);
			return R.ok(true);
		}
		catch (Exception e) {
			log.error("驳回任务失败: taskId={}", taskId, e);
			return R.failed("驳回任务失败: " + e.getMessage());
		}
	}

}
