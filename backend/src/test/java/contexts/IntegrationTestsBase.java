package contexts;

import com.fasterxml.jackson.databind.ObjectMapper;
import contexts.game.domain.repository.GameRepository;
import contexts.player.domain.repository.PlayerRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Ignore
public class IntegrationTestsBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PlayerRepository playerRepository;

    @Autowired
    protected GameRepository gameRepository;

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
        playerRepository.deleteAll();
    }


    protected MvcResult assertRequest(
            String method,
            String endpoint,
            Integer expectedStatusCode) throws Exception {
        return mockMvc.perform(request(HttpMethod.valueOf(method), endpoint))
                .andExpect(status().is(expectedStatusCode))
                .andReturn();
    }

    protected MvcResult assertRequestWithBody(
            String method,
            String endpoint,
            Object body,
            Integer expectedStatusCode) throws Exception {
        return mockMvc.perform(request(HttpMethod.valueOf(method), endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().is(expectedStatusCode))
                .andReturn();
    }
}
