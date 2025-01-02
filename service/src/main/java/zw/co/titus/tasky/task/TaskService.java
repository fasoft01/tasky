package zw.co.titus.tasky.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.titus.tasky.task.dto.TaskRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TaskService {
    Task create(TaskRequest request, String name);

    Task getById(String id);

    Task update(String id, TaskRequest request);

    Task delete(String id);

    Page<Task> getAll(String title, String description, LocalDate deadline,
                      LocalDate deadlineEndDate, LocalDateTime lastUpdated, String name,
                      Pageable pageable);
}
