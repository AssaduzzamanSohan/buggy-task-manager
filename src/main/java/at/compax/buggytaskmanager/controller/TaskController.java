package at.compax.buggytaskmanager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.compax.buggytaskmanager.constants.enums.Priority;
import at.compax.buggytaskmanager.dto.TaskRequest;
import at.compax.buggytaskmanager.dto.TaskResponse;
import at.compax.buggytaskmanager.model.Task;
import at.compax.buggytaskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @GetMapping
  @Operation(summary = "Get all tasks")
  public ResponseEntity<List<TaskResponse>> getAllTasks() {
    return ResponseEntity.ok(taskService.getAllTasks());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get task by ID")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.getTaskById(id));
  }

  @PostMapping
  @Operation(summary = "Create a new task")
  public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequest));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update an existing task")
  public ResponseEntity<TaskResponse> updateTask(
      @PathVariable Long id,
      @RequestBody TaskRequest taskRequest) {
    return ResponseEntity.ok(taskService.updateTask(id, taskRequest));
  }

  @PatchMapping("/{id}/complete")
  @Operation(summary = "Mark a task as completed")
  public ResponseEntity<TaskResponse> markTaskAsCompleted(@PathVariable Long id) {
    return ResponseEntity.ok(taskService.markTaskAsCompleted(id));
  }

  @GetMapping("/completed")
  @Operation(summary = "Get all completed tasks")
  public ResponseEntity<List<TaskResponse>> getCompletedTasks() {
    return ResponseEntity.ok(taskService.getCompletedTasks());
  }

  @GetMapping("/priority/{priority}")
  @Operation(summary = "Get tasks by priority")
  public ResponseEntity<List<TaskResponse>> getTasksByPriority(
      @PathVariable Priority priority) {
    return ResponseEntity.ok(taskService.getTasksByPriority(priority));
  }
}