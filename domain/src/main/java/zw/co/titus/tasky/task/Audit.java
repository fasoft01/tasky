package zw.co.titus.tasky.task;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Embeddable;
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
    private LocalDateTime lastModified;
    private String createdBy;
    private String modifiedBy;


    public Audit() {
        setCreatedDate( LocalDate.now());
        setLastModified(LocalDateTime.now());
    }

    public Audit(LocalDate createdDate, LocalDateTime lastModified) {
        this.createdDate = createdDate;
        this.lastModified = lastModified;
    }
}

