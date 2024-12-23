package zw.co.titus.tasky.auth;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.titus.tasky.auth.user.UserRegistrationService;

import java.security.Principal;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "authorization")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return authenticationService.signInUser(signInRequest);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                                            Principal principal) {
        updatePasswordRequest.setUsername(principal.getName());
        return ResponseEntity.ok(userRegistrationService.changePassword(updatePasswordRequest));
    }
}
