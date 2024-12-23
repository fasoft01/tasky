package zw.co.titus.tasky.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.titus.tasky.auth.JwtUtil;
import zw.co.titus.tasky.user.UserAccountRepository;

@RestController
@RequestMapping("/jwt")
@RequiredArgsConstructor
@Slf4j
public class JwtTokenController {
    private  final JwtUtil jwtUtil;
    private final UserAccountRepository userRepository;
    @PostMapping("/validate-token")
    public ResponseEntity<AuthValidationResponse> validateJwtToken(@RequestBody String authToken) {
        if(authToken.startsWith("%22") )
            authToken= authToken.substring(3);

        if( authToken.endsWith("%22="))
            authToken= authToken.replace("%22=","").trim();



        return ResponseEntity.ok(AuthValidationResponse
                .builder()
                        .isTokenValid(jwtUtil.validateJwtToken(authToken))
                        .userDetails(userRepository.findByUsername(jwtUtil.getUserNameFromJwtToken(authToken)).get())
                        .username(jwtUtil.getUserNameFromJwtToken(authToken))
                .build());
    }
}
