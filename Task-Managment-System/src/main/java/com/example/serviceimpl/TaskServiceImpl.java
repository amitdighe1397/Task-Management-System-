package com.example.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.repository.UserRepository;
import com.example.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Task createTask(Task task, Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent()) {
			task.setUser(userOptional.get());
			task.setCreatedAt(LocalDateTime.now());
			task.setUpdatedAt(LocalDateTime.now());
			return taskRepository.save(task);
		} else {
			throw new RuntimeException("User not found");
		}
	}

	@Override
	public Task updateTask(Long taskId, Task updatedTask, Long userId) {
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if (taskOptional.isPresent() && taskOptional.get().getUser().getId().equals(userId)) {
			Task task = taskOptional.get();
			task.setTitle(updatedTask.getTitle());
			task.setDescription(updatedTask.getDescription());
			task.setStatus(updatedTask.getStatus());
			task.setPriority(updatedTask.getPriority());
			task.setDueDate(updatedTask.getDueDate());
			task.setUpdatedAt(LocalDateTime.now());
			return taskRepository.save(task);
		} else {
			throw new RuntimeException("Task not found or you are not authorized to update this task");
		}
	}

	@Override
	public void deleteTask(Long taskId, Long userId) {
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		if (taskOptional.isPresent() && taskOptional.get().getUser().getId().equals(userId)) {
			taskRepository.delete(taskOptional.get());
		} else {
			throw new RuntimeException("Task not found or you are not authorized to delete this task");
		}
	}

	@Override
	public List<Task> getTasksByUser(Long userId) {
		return taskRepository.findByUserId(userId);
	}

}
