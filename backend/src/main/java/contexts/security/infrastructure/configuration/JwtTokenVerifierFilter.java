package contexts.security.infrastructure.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import contexts.exception.domain.InvalidCredentialsException;
import contexts.security.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final String secretJwtKey;
    private final ObjectMapper objectMapper;

    private void callBackend(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLoginError(HttpServletResponse response) throws IOException {
        InvalidCredentialsException errorResponse = new InvalidCredentialsException();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
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
            handleLoginError(response);
            return;
        }
    }
}
