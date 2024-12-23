package zw.co.titus.tasky.auth;


import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import zw.co.titus.tasky.auth.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
@Slf4j
public class JwtUtil {
    @Value("${authentication.signing-key}")
    private String jwtSecret;

    @Value("${authentication.jwt-expiration-time}")
    private int jwtExpirationMs;
    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }
    private Date extractExpiration(String token){
        return getClaims(token).getExpiration();
    }
     public boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
     }

    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        Map<String, Object> roles= new HashMap<>();
        roles.put("authorities", userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        Map<String, Object> additionalInfo= new HashMap<>();
        additionalInfo.put("fullName",userPrincipal.getFullName());
        roles.put("roles", List.of());

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(authentication));
        log.info("claims : {}", claims);
        return Jwts.builder()
                .setClaims(claims)
                .addClaims(roles)
                .addClaims(additionalInfo)
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
