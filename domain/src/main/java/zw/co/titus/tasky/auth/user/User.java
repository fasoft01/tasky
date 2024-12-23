package zw.co.titus.tasky.auth.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zw.co.titus.tasky.AppAuditEventListener;
import zw.co.titus.tasky.task.Audit;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tasky_user_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AppAuditEventListener.class)
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fullName;

    @Column
    private String username;

    @Column
    private String password;

    @Embedded
    private Audit audit;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
