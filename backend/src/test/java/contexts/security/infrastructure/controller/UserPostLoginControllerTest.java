package contexts.security.infrastructure.controller;

import contexts.IntegrationTestsBase;
import contexts.player.application.PlayerMother;
import contexts.player.domain.entities.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserPostLoginControllerTest extends IntegrationTestsBase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldReturn200WhenCorrectLogin() throws Exception {
        Player user = PlayerMother.basicWithoutId();
        String passwordNotEncoded = user.getPassword();
        user.setPassword(passwordEncoder.encode(passwordNotEncoded));
        playerRepository.save(user);

        LoginRequest loginRequest = new LoginRequest(user.getName(), passwordNotEncoded);

        assertRequestWithBody(
                "POST",
                "/api/v1/login",
                loginRequest,
                HttpStatus.OK.value()
        );
    }


    @Test
    void shouldReturn401WhenIncorrectLogin() throws Exception {
        Player userNotSaved = PlayerMother.basicWithoutId();

        LoginRequest loginRequest = new LoginRequest(userNotSaved.getName(), userNotSaved.getPassword());

        assertRequestWithBody(
                "POST",
                "/api/v1/login",
                loginRequest,
                HttpStatus.UNAUTHORIZED.value()
        );
    }
}