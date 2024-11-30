package com.app.taskmanagement.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class TaskDto {
     
	private long id;
	private String taskname;
}
