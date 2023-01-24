package contexts.player.infrastructure.controller;

import contexts.IntegrationTestsBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlayerPostControllerTest extends IntegrationTestsBase {

    @Test
    void registerPlayerOK() throws Exception {
        PlayerPostRequest playerPostRequest = PlayerPostRequestMother.basic();

        MvcResult mvcResult =
            assertRequestWithBody(
                "POST",
                "/api/v1/player",
                playerPostRequest,
                HttpStatus.CREATED.value()
            );
        PlayerPostResponse playerPostResponse =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PlayerPostResponse.class);
        assertThat(playerPostResponse.getName()).isEqualTo(playerPostRequest.getName());
    }
}