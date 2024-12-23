package zw.co.titus.tasky;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;
import zw.co.titus.tasky.task.Audit;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Configurable
public class AppAuditEventListener {
    private static final String DEFAULT_USER = "N/A";

    @PrePersist
    public void persist(Object obj){
        final String USER = getLoggedUser();
        try {
            Audit audit = getAudit(obj);
            if (Objects.isNull(audit)) audit = new Audit();
            audit.setCreatedBy(USER);
            audit.setModifiedDate(LocalDateTime.now());
            audit.setCreatedDate(LocalDate.now());
            audit.setModifiedBy(USER);
            Method setAudit =  getSetAuditMethod(obj);
            setAudit.invoke(obj,audit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method getSetAuditMethod(Object obj) throws Exception{
        return obj.getClass().getMethod("setAudit",Audit.class);
    }

    @PreUpdate
    void update(Object obj){
        try {
            Audit audit = getAudit(obj);
            if (Objects.isNull(audit)){
                audit = new Audit();
                audit.setCreatedBy(getLoggedUser());
                audit.setCreatedDate(LocalDate.now());
            }
            audit.setModifiedBy(getLoggedUser());
            audit.setModifiedDate(LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Audit getAudit(Object obj) throws Exception{
        Method getAudit = obj.getClass().getMethod("getAudit");
        return (Audit) getAudit.invoke(obj);
    }

    private static String getLoggedUser(){

        if( SecurityContextHolder.getContext().getAuthentication() == null ){
            return DEFAULT_USER;
        }else{
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
    }
}
