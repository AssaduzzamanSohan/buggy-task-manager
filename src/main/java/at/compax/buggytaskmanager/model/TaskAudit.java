package at.compax.buggytaskmanager.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "task_audit")
public class TaskAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "task_id")
  private Task task;

  private String action;

  private LocalDateTime timestamp;

  public TaskAudit() {
  }

  public TaskAudit(Task task, String action) {
    this.task = task;
    this.action = action;
    this.timestamp = LocalDateTime.now();
  }
}