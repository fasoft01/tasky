package zw.co.titus.tasky.auth.user;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String fullName;
    private String password;
    private String confirmPassword;
}
