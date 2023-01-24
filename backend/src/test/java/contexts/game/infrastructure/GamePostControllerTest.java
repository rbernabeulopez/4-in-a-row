package contexts.game.infrastructure;

import contexts.IntegrationTestsBase;
import contexts.player.application.PlayerMother;
import contexts.player.domain.entities.Player;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GamePostControllerTest extends IntegrationTestsBase {

    @Test
    void createGameOK() throws Exception {
        Player player = playerRepository.save(PlayerMother.basicWithId(1L));
        GamePostRequest gamePostRequest = GamePostRequestMother.basicWithPlayerId(player.getId());

        MvcResult mvcResult = assertRequestWithBody(
                "POST",
                "/api/v1/game",
                gamePostRequest,
                HttpStatus.CREATED.value()
        );

        GamePostResponse gamePostResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GamePostResponse.class);
        assertThat(gamePostResponse.getId()).isNotNull();
    }
}