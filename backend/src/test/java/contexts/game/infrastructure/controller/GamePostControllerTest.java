package contexts.game.infrastructure.controller;

import contexts.IntegrationTestsBase;
import contexts.player.application.PlayerCreator;
import contexts.player.application.PlayerMother;
import contexts.player.domain.entities.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class GamePostControllerTest extends IntegrationTestsBase {
    @Autowired
    private PlayerCreator playerCreator;

    @Test
    void createGameOK() throws Exception {
        Player player = PlayerMother.basicWithId(1L);
        Player playerDB = playerCreator.createPlayer(
                player.getName(),
                player.getPassword(),
                player.getIpAddress()
        );
        GamePostRequest gamePostRequest = GamePostRequestMother.basicWithPlayerId(playerDB.getId());

        MvcResult mvcResult = assertRequestWithBody(
                "POST",
                "/api/v1/game",
                gamePostRequest,
                HttpStatus.CREATED.value()
        );

        GamePostResponse gamePostResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GamePostResponse.class);
        assertThat(gamePostResponse).isNotNull();
    }
}