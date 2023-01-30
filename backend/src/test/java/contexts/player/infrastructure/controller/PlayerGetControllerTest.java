package contexts.player.infrastructure.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PlayerGetControllerTest {
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
    void getPlayerGameHistory() throws Exception {
        // GIVEN
        Player player = playerCreator.createPlayer("name", "pass", httpServletRequest.getRemoteAddr());
        // WHEN
        MvcResult mvcResult =
                mockMvc.perform(get("/api/v1/player/" + player.getId()+ "/games")
                ).andExpect(status().isOk()).andReturn();
        // THEN
        List<Game> actualGames = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
//        List<Game> expectedPersons = List.of(game1,game2);
//        assertThat(actualGames).isEqualTo(expectedPersons);
    }
}