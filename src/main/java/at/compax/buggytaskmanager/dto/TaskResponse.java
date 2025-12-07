package at.compax.buggytaskmanager.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import at.compax.buggytaskmanager.constants.enums.Priority;
import lombok.Data;

@Data
public class TaskResponse {
  private Long id;
  private String title;
  private String description;
  private boolean completed;
  private LocalDate dueDate;
  private Priority priority;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}