package contexts.game.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import contexts.game.application.GameCreator;
import contexts.game.application.GameFinder;
import contexts.game.domain.entity.Game;
import contexts.player.application.PlayerCreator;
import contexts.player.domain.entities.Player;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GameGetControllerTest {

    @Autowired
    private GameFinder gameFinder;
    @Autowired
    private GameCreator gameCreator;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PlayerCreator playerCreator;
    @Autowired
    private HttpServletRequest httpServletRequest;


    @Test
    void getGame() throws Exception {
        // GIVEN
        Player player = playerCreator.createPlayer("name", "pass", httpServletRequest.getRemoteAddr());
        Game game = gameCreator.createGame(player.getId());
        // WHEN
        MvcResult mvcResult =
                mockMvc.perform(get("/api/v1/game/" + game.getId())
                ).andExpect(status().isOk()).andReturn();
        // THEN
        Game actualGame = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Game.class);
//        assertThat(actualGame).isEqualTo(game);
    }
}