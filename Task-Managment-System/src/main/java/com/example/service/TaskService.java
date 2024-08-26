package com.example.service;

import java.time.LocalDate;
import java.util.List;

import com.example.model.Task;

public interface TaskService {

    Task createTask(Task task, Long userId);

    Task updateTask(Long taskId, Task task, Long userId);

    void deleteTask(Long taskId, Long userId);

    List<Task> getTasksByUser(Long long1);

    public List<Task> getTasks(String title, String status, LocalDate dueDate);

}
