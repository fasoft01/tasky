package zw.co.titus.tasky.auth.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.co.titus.tasky.auth.SignUpRequest;
import zw.co.titus.tasky.auth.UpdatePasswordRequest;
import zw.co.titus.tasky.exceptions.InvalidArgumentException;
import zw.co.titus.tasky.exceptions.RecordNotFoundException;
import zw.co.titus.tasky.user.UserAccountRepository;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService{
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(SignUpRequest signUpRequest) {
        if (userAccountRepository.existsByUsername(signUpRequest.getUsername()))
            throw new InvalidArgumentException("username is already taken");

        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        return userAccountRepository.saveAndFlush(User
                .builder()
                .fullName(signUpRequest.getFullName())
                .password(hashedPassword)
                .username(signUpRequest.getUsername())
                .build());
    }

    @Override
    public User updateDetails(Long id, UserUpdateRequest userUpdateRequest) {
        log.info("Starting update process for user ID: {}", id);

        User user = userAccountRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        log.info("User found with ID: {}", id);

        log.info("Updating details for user ID: {}", id);
        user.setFullName(userUpdateRequest.getFullName());
        log.info("User details updated for user ID: {}", id);

        return userAccountRepository.saveAndFlush(user);
    }


    @Override
    public User getByUsername(String username) {
        User user = userAccountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username " + username));
        log.info("user: {}", user.toString());
        return user;
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userAccountRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public User deleteUser(Long userId) {
        User user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        userAccountRepository.delete(user);
        return user;
    }

    @Override
    public User getUserProfile(String name) {
        return getByUsername(name);
    }


    @Override
    public User changePassword(UpdatePasswordRequest updatePasswordRequest) {
        log.info("Starting password change process for username: {}", updatePasswordRequest.getUsername());

        var user = getByUsername(updatePasswordRequest.getUsername());
        if (user == null) {
            log.error("User not found for username: {}", updatePasswordRequest.getUsername());
            throw new RecordNotFoundException("User not found");
        }
        log.info("User retrieved successfully for username: {}", updatePasswordRequest.getUsername());

        updateUserPassword(user, updatePasswordRequest.getPassword(), updatePasswordRequest.getConfirmPassword());
        log.info("Password updated successfully ");

        log.info("Password change process completed successfully for username: {}", updatePasswordRequest.getUsername());
        return user;
    }

    private void updateUserPassword(User user, String oldPassword, String newPassword) {
        log.info("Updating password for username: {}", user.getUsername());

        if(!Objects.equals(oldPassword,newPassword)){
            throw new InvalidArgumentException("Passwords Do not match, Confirm password.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userAccountRepository.saveAndFlush(user);

        log.info("Password updated successfully for username: {}", user.getUsername());
    }



}
