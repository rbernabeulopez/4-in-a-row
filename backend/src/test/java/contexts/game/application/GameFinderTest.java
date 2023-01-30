package contexts.game.application;

import contexts.UnitTestsBase;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerFinder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class GameFinderTest extends UnitTestsBase {
    @InjectMocks
    private GameFinder gameFinder;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerFinder playerFinder;

    @Test
    void shouldFindGame() {
        long playerId = 1L;
        Game gameExpected = GameMother.basicWithIdAndPlayer(1L, playerId);

//        when(gameRepository.save(any())).thenReturn(gameExpected);
//        when(playerFinder.findPlayer(playerId)).thenReturn(gameExpected.getPlayers().get(0));

        given(gameRepository.findById(gameExpected.getId())).willReturn(Optional.of(gameExpected));

        // WHEN
        Game actualGame =
                assertDoesNotThrow(() -> gameFinder.findGame(gameExpected.getId()));

        // THEN
        assertEquals(gameExpected, actualGame);
        verify(gameRepository, times(1)).findById(gameExpected.getId());

    }
}