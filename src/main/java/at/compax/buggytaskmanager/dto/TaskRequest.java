package at.compax.buggytaskmanager.dto;

import java.time.LocalDate;

import at.compax.buggytaskmanager.constants.enums.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

  @NotBlank(message = "Title is mandatory")
  private String title;

  private String description;

  @FutureOrPresent(message = "Due date must be in the present or future")
  private LocalDate dueDate;

  private Priority priority;
}