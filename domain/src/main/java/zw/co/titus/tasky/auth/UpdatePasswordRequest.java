package zw.co.titus.tasky.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdatePasswordRequest {
    private String password;
    private String confirmPassword;
    @JsonIgnore
    private String username;
}
