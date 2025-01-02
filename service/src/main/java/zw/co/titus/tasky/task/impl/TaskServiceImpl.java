package zw.co.titus.tasky.task.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import zw.co.titus.tasky.exceptions.RecordNotFoundException;
import zw.co.titus.tasky.task.Task;
import zw.co.titus.tasky.task.TaskRepository;
import zw.co.titus.tasky.task.TaskService;
import zw.co.titus.tasky.task.TaskSpecifications;
import zw.co.titus.tasky.task.dto.TaskRequest;
import zw.co.titus.tasky.auth.user.User;
import zw.co.titus.tasky.user.UserAccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserAccountRepository userRepository;

    @Override
    public Task create(TaskRequest request, String username) {
        log.info("Creating task for user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + username));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .isSynced(request.getIsSynced())
                .deadline(request.getDeadline())
                .lastUpdated(LocalDate.now())
                .user(user)
                .build();

        task = taskRepository.save(task);

        log.info("Task created with ID: {}", task.getId());
        return task;
    }

    @Override
    public Task getById(String id) {
        log.info("Fetching task with ID: {}", id);
        return findById(id);
    }

    private Task findById(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RecordNotFoundException("Task not found with ID: " + taskId));
    }

    @Override
    public Task update(String id, TaskRequest request) {
        log.info("Updating task with ID: {}", id);

        Task task = findById(id);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setIsSynced(request.getIsSynced());
        task.setDeadline(request.getDeadline());
        task.setLastUpdated(LocalDate.now());

        task = taskRepository.save(task);

        log.info("Task updated with ID: {}", task.getId());
        return task;
    }

    @Override
    public Task delete(String id) {
        log.info("Deleting task with ID: {}", id);

        Task task = findById(id);
        taskRepository.delete(task);

        log.info("Task with ID: {} deleted successfully", id);
        return task;
    }

    public Page<Task> getAll(String title, String description,
            LocalDate deadlineStartDate, LocalDate deadlineEndDate,
            LocalDate lastUpdated,
            String username, Pageable pageable) {

        Specification<Task> spec = Specification.where(TaskSpecifications.assignedToUsername(username))
                .and(TaskSpecifications.titleContains(title))
                .and(TaskSpecifications.descriptionContains(description))
                .and(TaskSpecifications.lastUpdatedAfter(lastUpdated))
                .and(TaskSpecifications.deadlineBetween(deadlineStartDate, deadlineEndDate));

        return taskRepository.findAll(spec, pageable);
    }
}
