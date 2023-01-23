package contexts.game.application;

import contexts.UnitTestsBase;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerFinder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameCreatorTest extends UnitTestsBase {
    @InjectMocks
    private GameCreator gameCreator;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerFinder playerFinder;

    @Test
    void shouldCreateGame() {
        Game gameExpected = GameMother.basicWithId(1L);

        when(gameRepository.save(any())).thenReturn(gameExpected);
        when(playerFinder.findPlayer(1L)).thenReturn(gameExpected.getPlayers().get(0));

        Game actualGame = assertDoesNotThrow(() -> gameCreator.createGame(gameExpected.getPlayers().get(0).getId()));

        assertEquals(gameExpected.getPlayers().get(0).getId(), actualGame.getPlayers().get(0).getId());
        assertEquals(gameExpected.getPlayers().get(0).getName(), actualGame.getPlayers().get(0).getName());
        gameRepository.save(gameExpected);

        verify(gameRepository).save(gameExpected);
    }
}