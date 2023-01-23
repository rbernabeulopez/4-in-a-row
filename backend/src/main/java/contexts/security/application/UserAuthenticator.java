package contexts.security.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import contexts.security.infrastructure.JwtConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class UserAuthenticator {

    private AuthenticationManager authenticationManager;
    private JwtConfig jwtConfig;

    public String authenticate(UsernamePasswordAuthenticationToken authenticationToken) {
        log.info("Authenticating user {}", authenticationToken.getName());
        Authentication authenticate = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        return getTokenJwt(authenticate);
    }

    private String getTokenJwt(Authentication authenticate) {
        int tokenExpirationTime = this.jwtConfig.getExpiration();
        Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + tokenExpirationTime);

        return JWT.create()
            .withSubject(authenticate.getName())
            .withIssuedAt(createdDate)
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC512(this.jwtConfig.getKey()));
    }
}