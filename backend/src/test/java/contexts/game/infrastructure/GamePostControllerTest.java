package contexts.game.infrastructure;

import contexts.IntegrationTestsBase;
import contexts.player.application.PlayerMother;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

class GamePostControllerTest extends IntegrationTestsBase {
    @Autowired
    private PlayerRepository playerRepository;
    private HttpServletRequest httpServletRequest;


    @Test
    void createGameOK() throws Exception {
        GamePostRequest gamePostRequest = GamePostRequestMother.basic();
        Player player = playerRepository.save(PlayerMother.basicWithId(1L));

        MvcResult mvcResult = assertRequestWithBody(
                "POST",
                "/api/v1/game",
                gamePostRequest,
                HttpStatus.CREATED.value()
        );

        GamePostResponse gamePostResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GamePostResponse.class);
    }
}