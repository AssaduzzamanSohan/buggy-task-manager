package at.compax.buggytaskmanager.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.compax.buggytaskmanager.constants.enums.Priority;
import at.compax.buggytaskmanager.dto.TaskRequest;
import at.compax.buggytaskmanager.dto.TaskResponse;
import at.compax.buggytaskmanager.model.Task;
import at.compax.buggytaskmanager.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  private int completionCounter = 0;

  public List<TaskResponse> getAllTasks() {
    return taskRepository.findAll().stream()
        .map(this::convertToResponse)
        .toList();
  }

  public Task getTaskById(Long id) {
    return taskRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
  }

  public TaskResponse createTask(TaskRequest taskRequest) {
    Task task = new Task();
    task.setTitle(taskRequest.getTitle());
    task.setDescription(taskRequest.getDescription());
    task.setDueDate(taskRequest.getDueDate());
    task.setPriority(taskRequest.getPriority());

    task.addAuditLog("CREATED");
    Task savedTask = taskRepository.save(task);
    return convertToResponse(savedTask);
  }

  public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
    Task task = new Task();
    task.setId(id);
    task.setTitle(taskRequest.getTitle());
    task.setDescription(taskRequest.getDescription());
    task.setDueDate(taskRequest.getDueDate());
    task.setPriority(taskRequest.getPriority());
    task.setUpdatedAt(java.time.LocalDateTime.now());

    task.addAuditLog("UPDATED");
    Task savedTask = taskRepository.save(task);
    return convertToResponse(savedTask);
  }

  @Transactional
  public TaskResponse markTaskAsCompleted(Long id) {
    Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

    completionCounter++;
    log.info("TaskId = {}, completionCounter = {}", id, completionCounter);

    // Simulate some processing delay to exacerbate race condition
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    task.setCompleted(true);
    task.addAuditLog("COMPLETED - Counter: " + completionCounter);

    Task savedTask = taskRepository.save(task);
    return convertToResponse(savedTask);
  }

  public List<TaskResponse> getCompletedTasks() {
    return taskRepository.findByCompleted(true).stream()
        .map(this::convertToResponse)
        .toList();
  }

  public List<TaskResponse> getTasksByPriority(Priority priority) {
    return taskRepository.findByPriorityOrderByDueDateAsc(priority).stream()
        .map(this::convertToResponse)
        .toList();
  }

  private TaskResponse convertToResponse(Task task) {
    TaskResponse response = new TaskResponse();
    response.setId(task.getId());
    response.setTitle(task.getTitle());
    response.setDescription(task.getDescription());
    response.setCompleted(task.isCompleted());
    response.setDueDate(task.getDueDate());
    response.setPriority(task.getPriority());
    response.setCreatedAt(task.getCreatedAt());
    response.setUpdatedAt(task.getUpdatedAt());
    return response;
  }
}