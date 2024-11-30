package com.app.taskmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
	
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   
   @Column(name = "taskName", nullable = false)
   private String taskName;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "user_id")
   private Users users;
}
