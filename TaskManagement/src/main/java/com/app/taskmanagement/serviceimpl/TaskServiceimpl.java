package com.app.taskmanagement.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.taskmanagement.entity.Task;
import com.app.taskmanagement.entity.Users;
import com.app.taskmanagement.exception.APIException;
import com.app.taskmanagement.exception.TaskNotFound;
import com.app.taskmanagement.exception.UserNotFound;
import com.app.taskmanagement.payload.TaskDto;
import com.app.taskmanagement.repository.TaskRepository;
import com.app.taskmanagement.repository.UserRepository;
import com.app.taskmanagement.service.TaskService;

@Service
public class TaskServiceimpl implements TaskService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public TaskDto saveTask(long userid, TaskDto taskDto) {
		Users user = userRepository.findById(userid).orElseThrow(
				  () ->  new UserNotFound(String.format("User Id %d not found", userid))
				);
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		Task savedTask = taskRepository.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userid) {
		userRepository.findById(userid).orElseThrow(
				() -> new UserNotFound(String.format("User Id %d not found", userid))
				);
		List<Task>tasks = taskRepository.findAllByUsersId(userid);
		return tasks.stream().map(
				task -> modelMapper.map(task, TaskDto.class)
				).collect(Collectors.toList());
	
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
	    Users users = userRepository.findById(userid).orElseThrow(
	    		() -> new UserNotFound(String.format("user Id %d not found", userid))
	    		);
	    Task task = taskRepository.findById(taskid).orElseThrow(
	    		() -> new TaskNotFound(String.format("Task Id %d not found",taskid))
	    		);
	    if(users.getId() != task.getUsers().getId()) {
	    	throw new APIException(String.format("Task Id %d does not belongs to User Id %d", taskid, userid));
	    }
	    return modelMapper.map(task,  TaskDto.class);
	}

	@Override
	public void deleteTask(long userid, long taskid) {
		Users users = userRepository.findById(userid).orElseThrow(
	    		() -> new UserNotFound(String.format("user Id %d not found", userid))
	    		);
	    Task task = taskRepository.findById(taskid).orElseThrow(
	    		() -> new TaskNotFound(String.format("Task Id %d not found",taskid))
	    		);
	    if(users.getId() != task.getUsers().getId()) {
	    	throw new APIException(String.format("Task Id %d does not belongs to User Id %d", taskid, userid));
	    }
	    taskRepository.deleteById(taskid);
		
	}

}
