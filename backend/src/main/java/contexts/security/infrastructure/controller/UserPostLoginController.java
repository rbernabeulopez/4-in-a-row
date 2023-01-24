package contexts.security.infrastructure.controller;

import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import contexts.security.application.UserAuthenticator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class UserPostLoginController {
    private final UserAuthenticator userAuthenticator;

    private PlayerFinder playerFinder;

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getName(),
                loginRequest.getPassword()
        );

        try {
            String token = userAuthenticator.authenticate(authenticationToken);
            response.addHeader("Authorization", "Bearer " + token);
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        Player player = playerFinder.findByName(loginRequest.getName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(player.getId());
        loginResponse.setName(player.getName());
        return loginResponse;
    }

}


@AllArgsConstructor
@NoArgsConstructor
@Data
class LoginRequest {
    private String name;
    private String password;
}

@Data
class LoginResponse {
    private long id;
    private String name;
}
