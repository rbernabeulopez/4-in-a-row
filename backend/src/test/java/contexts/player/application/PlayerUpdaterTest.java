package contexts.player.application;

import contexts.UnitTestsBase;
import contexts.game.application.GameMother;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerUpdaterTest extends UnitTestsBase {
    @InjectMocks
    private PlayerUpdater playerUpdater;
    @Mock
    private PlayerFinder playerFinder;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameRepository gameRepository;

    @Test
    void shouldAddGameToPlayer() {
        Player expectedPlayer = PlayerMother.basicWithId(1L);
        when(playerRepository.save(expectedPlayer)).thenReturn(expectedPlayer);
        Game expectedGame = GameMother.basicWithId(1L);
        when (playerFinder.findPlayer(expectedPlayer.getId())).thenReturn(expectedPlayer);
        playerRepository.save(expectedPlayer);

        Player actualPlayer =
                assertDoesNotThrow(() -> playerFinder.findPlayer(expectedPlayer.getId()));
        actualPlayer.getGames().add(expectedGame);

        verify(playerRepository, times(1)).save(actualPlayer);

    }
}