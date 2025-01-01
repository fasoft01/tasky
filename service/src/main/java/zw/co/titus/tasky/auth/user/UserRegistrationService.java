package zw.co.titus.tasky.auth.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.titus.tasky.auth.SignUpRequest;
import zw.co.titus.tasky.auth.UpdatePasswordRequest;


public interface UserRegistrationService {
    User register(SignUpRequest signUpRequest);
    User updateDetails(Long id,UserUpdateRequest userUpdateRequest);

    User getByUsername(String username);

    Page<User> getUsers(Pageable pageable);
    User deleteUser(Long userId);
    User getUserProfile(String name);

    User changePassword(UpdatePasswordRequest updatePasswordRequest);
}
