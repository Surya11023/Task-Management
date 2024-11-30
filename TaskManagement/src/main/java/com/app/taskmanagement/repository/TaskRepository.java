package com.app.taskmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.taskmanagement.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUsersId(long userid);

}
