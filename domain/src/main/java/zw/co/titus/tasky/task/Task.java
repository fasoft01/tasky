package zw.co.titus.tasky.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.titus.tasky.AppAuditEventListener;
import zw.co.titus.tasky.auth.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AppAuditEventListener.class)
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    @NotNull(message = "Deadline is required")
    @FutureOrPresent(message = "Deadline must be in the present or future")
    private LocalDateTime deadline;

    @NotNull(message = "Last updated date is required")
    private LocalDateTime lastUpdated;

    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Task must be assigned to a user")
    private User user;
}
