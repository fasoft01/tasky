package zw.co.titus.tasky.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.titus.tasky.task.dto.TaskRequest;

import java.time.LocalDate;

public interface TaskService {
    Task create(TaskRequest request, String name);

    Task getById(Long id);

    Task update(Long id, TaskRequest request);

    Task delete(Long id);

    Page<Task> getAll(String title, String description, LocalDate deadline, LocalDate deadlineEndDate, LocalDate lastUpdated, String name, Pageable pageable);
}
