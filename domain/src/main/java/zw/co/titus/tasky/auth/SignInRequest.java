package zw.co.titus.tasky.auth;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
