package contexts.game.infrastructure;

import contexts.IntegrationTestCase;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import contexts.player.domain.vo.PlayerIpAddress;
import contexts.player.domain.vo.PlayerName;
import contexts.player.domain.vo.PlayerPassword;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

class GamePostControllerTest extends IntegrationTestCase {
    @Autowired
    private PlayerRepository playerRepository;
    private HttpServletRequest httpServletRequest;


    @Test
    void createGameOK() throws Exception {
        GamePostRequest gamePostRequest = GamePostRequestMother.basic();
        Player player = playerRepository.save(Player.builder()
                .name(new PlayerName("test"))
                .password(new PlayerPassword("testtest"))
                .ipAddress(new PlayerIpAddress("0:0:0:0:0:0:0:1"))
                .build());

        MvcResult mvcResult = assertRequestWithBody(
                "POST",
                "/api/v1/game",
                gamePostRequest,
                HttpStatus.CREATED.value()
        );

        GamePostResponse gamePostResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GamePostResponse.class);
    }
}