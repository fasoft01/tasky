package zw.co.titus.tasky.auth;

import lombok.*;
import zw.co.titus.tasky.auth.user.User;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AuthValidationResponse {
    private  boolean isTokenValid;
    private  String username;
    private User userDetails;
}