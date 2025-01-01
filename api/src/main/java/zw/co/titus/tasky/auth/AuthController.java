package zw.co.titus.tasky.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.titus.tasky.auth.user.User;
import zw.co.titus.tasky.auth.user.UserRegistrationService;

import java.security.Principal;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@Slf4j
//@SecurityRequirement(name = "authorization")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signInUser(signInRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok( userRegistrationService.register(signUpRequest));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                            Principal principal) {
        updatePasswordRequest.setUsername(principal.getName());
        return ResponseEntity.ok(userRegistrationService.changePassword(updatePasswordRequest));
    }
}
