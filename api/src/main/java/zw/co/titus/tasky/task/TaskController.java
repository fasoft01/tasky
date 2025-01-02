package zw.co.titus.tasky.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.titus.tasky.task.dto.TaskRequest;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "authorization")
@Tag(name = TaskSwaggerConstants.TAG, description = TaskSwaggerConstants.TAG_DESCRIPTION)
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = TaskSwaggerConstants.CREATE_SUMMARY, description = TaskSwaggerConstants.CREATE_DESCRIPTION)
    @PostMapping
    public ResponseEntity<Task> create(@RequestBody TaskRequest request, Principal principal) {
        return ResponseEntity.ok(taskService.create(request,principal.getName()));
    }

    @Operation(summary = TaskSwaggerConstants.GET_BY_ID_SUMMARY, description = TaskSwaggerConstants.GET_BY_ID_DESCRIPTION)
    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(
            @Parameter(description = TaskSwaggerConstants.ID_DESCRIPTION, required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @Operation(summary = TaskSwaggerConstants.UPDATE_SUMMARY, description = TaskSwaggerConstants.UPDATE_DESCRIPTION)
    @PutMapping("/{id}")
    public ResponseEntity<Task> update(
            @Parameter(description = TaskSwaggerConstants.ID_DESCRIPTION, required = true)
            @PathVariable String id,
            @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @Operation(summary = TaskSwaggerConstants.DELETE_SUMMARY, description = TaskSwaggerConstants.DELETE_DESCRIPTION)
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(
            @Parameter(description = TaskSwaggerConstants.ID_DESCRIPTION, required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(taskService.delete(id));
    }

    @Operation(summary = TaskSwaggerConstants.GET_ALL_SUMMARY, description = TaskSwaggerConstants.GET_ALL_DESCRIPTION)
    @GetMapping
    public ResponseEntity<Page<Task>> getAll(
            @Parameter(description = TaskSwaggerConstants.TITLE_DESCRIPTION)
            @RequestParam @Nullable String title,
            @Parameter(description = TaskSwaggerConstants.DESCRIPTION_DESCRIPTION)
            @RequestParam @Nullable String description,
            @Parameter(description = TaskSwaggerConstants.DEADLINE_DESCRIPTION)
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadlineStartDate,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadlineEndDate,
            @Parameter(description = TaskSwaggerConstants.PAGE_NUMBER_DESCRIPTION)
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @Parameter(description = TaskSwaggerConstants.PAGE_SIZE_DESCRIPTION)
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "Last modified date")
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastUpdated,
            Principal principal) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        return ResponseEntity.ok(taskService.getAll(title, description, deadlineStartDate, deadlineEndDate,lastUpdated,principal.getName() ,pageable));
    }

}
