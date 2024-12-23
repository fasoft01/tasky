package zw.co.titus.tasky.auth.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import zw.co.titus.tasky.exceptions.RecordNotFoundException;
import zw.co.titus.tasky.user.UserAccountRepository;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws RecordNotFoundException {
        return  userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RecordNotFoundException("User not found with username " + username));

    }
}
