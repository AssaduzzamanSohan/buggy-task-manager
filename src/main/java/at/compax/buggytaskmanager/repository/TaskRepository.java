package at.compax.buggytaskmanager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.compax.buggytaskmanager.constants.enums.Priority;
import at.compax.buggytaskmanager.model.Task;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findByCompleted(boolean completed);

  List<Task> findByPriorityOrderByDueDateAsc(Priority priority);

  @Modifying
  @Query("UPDATE Task t SET t.completed = true WHERE t.id = :id")
  void markAsCompleted(@Param("id") Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT t FROM Task t WHERE t.id = :id")
  Optional<Task> findByIdWithLock(@Param("id") Long id);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({
      @QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")
  })
  @Query("SELECT t FROM Task t WHERE t.id = :id")
  Optional<Task> findByIdWithLockNoWait(@Param("id") Long id);
}