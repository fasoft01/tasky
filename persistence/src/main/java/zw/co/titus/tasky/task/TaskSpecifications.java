package zw.co.titus.tasky.task;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskSpecifications {

    public static Specification<Task> assignedToUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("user").get("username"), username);
    }

    public static Specification<Task> titleContains(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Task> deadlineBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null && endDate == null) {
                return criteriaBuilder.conjunction();
            }
            LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
            LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

            if (startDateTime != null && endDateTime != null) {
                return criteriaBuilder.between(root.get("deadline"), startDateTime, endDateTime);
            }
            if (startDateTime != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("deadline"), startDateTime);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("deadline"), endDateTime);
        };
    }

    public static Specification<Task> descriptionContains(String description) {
        return (root, query, criteriaBuilder) -> {
            if (description == null || description.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        };
    }

    public static Specification<Task> deadlineEquals(LocalDate deadline) {
        return (root, query, criteriaBuilder) -> {
            if (deadline == null) {
                return criteriaBuilder.conjunction();
            }
            LocalDateTime startOfDay = deadline.atStartOfDay();
            LocalDateTime endOfDay = deadline.atTime(23, 59, 59);
            return criteriaBuilder.between(root.get("deadline"), startOfDay, endOfDay);
        };
    }

    public static Specification<Task> lastUpdatedAfter(LocalDate lastUpdated) {
        return (root, query, criteriaBuilder) -> {
            if (lastUpdated == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdated"), lastUpdated);
        };
        }

}
