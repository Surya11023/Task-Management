package com.app.taskmanagement.service;

import java.util.List;

import com.app.taskmanagement.payload.TaskDto;

public interface TaskService {

	public TaskDto saveTask(long userid, TaskDto taskDto);
	
	public List<TaskDto> getAllTasks(long userid);
	
	public TaskDto getTask(long userid, long taskid);
	
	public void deleteTask(long userid, long taskid);
	
}
