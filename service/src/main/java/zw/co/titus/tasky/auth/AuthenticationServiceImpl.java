package zw.co.titus.tasky.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zw.co.titus.tasky.auth.user.User;
import zw.co.titus.tasky.exceptions.InvalidArgumentException;
import zw.co.titus.tasky.user.UserAccountRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserAccountRepository userRepository;



    @Override
    public ResponseEntity<?> signInUser(SignInRequest signInRequest) {
        userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(
                ()-> new InvalidArgumentException("Invalid User Credentials"));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        JwtResponse res = new JwtResponse();
        res.setToken(jwt);
        return  ResponseEntity.ok(res);
    }
}
