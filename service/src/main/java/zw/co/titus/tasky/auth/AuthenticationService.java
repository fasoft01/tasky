package zw.co.titus.tasky.auth;


import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> signInUser(SignInRequest signInRequest);
}
