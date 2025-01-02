package zw.co.titus.tasky.task;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Embeddable
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Audit {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastUpdated;
    private String createdBy;
    private String modifiedBy;


    public Audit() {
        setCreatedDate( LocalDate.now());
        setLastUpdated(LocalDateTime.now());
    }

    public Audit(LocalDate createdDate, LocalDateTime lastUpdated) {
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
}

