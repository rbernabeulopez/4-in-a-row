package contexts.security.infrastructure;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import contexts.security.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final String secretJwtKey;

    private void callBackend(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || authorizationHeader.isBlank() || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretJwtKey);
            DecodedJWT decodedJwt = JWT.require(algorithm).build().verify(token);

            String username = decodedJwt.getSubject();

            User userApp = new User(username);
            Authentication usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userApp, null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            callBackend(request, response, filterChain);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
    }
}
