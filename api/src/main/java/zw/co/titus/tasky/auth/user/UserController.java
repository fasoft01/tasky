package zw.co.titus.tasky.auth.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.titus.tasky.auth.SignUpRequest;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
//@Slf4j
//@SecurityRequirement(name = "authorization")
public class UserController {
    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok( userRegistrationService.register(signUpRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateDetails(@PathVariable Long id, @RequestBody UserUpdateRequest signUpRequest) {
        return ResponseEntity.ok( userRegistrationService.updateDetails(id,signUpRequest));
    }

    @GetMapping("profile")
    public ResponseEntity<User> getProfile(Principal principal) {
        return ResponseEntity.ok( userRegistrationService.getUserProfile(principal.getName()));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        return ResponseEntity.ok(userRegistrationService.getUsers(pageable));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userRegistrationService.deleteUser(id));
    }
}
