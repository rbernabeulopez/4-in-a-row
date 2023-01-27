package contexts.game.application;

import contexts.UnitTestsBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import contexts.exception.domain.EntityNotFoundException;
import contexts.exception.domain.GameIsFullException;
import contexts.exception.domain.PlayerAlreadyBelongsGameException;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerFinder;
import contexts.player.application.PlayerMother;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.util.Pair;

import java.util.Optional;

class GameModifierTest extends UnitTestsBase {
    @InjectMocks
    private GameModifier gameModifier;

    @Mock
    private GameFinder gameFinder;

    @Mock
    private PlayerFinder playerFinder;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    void joinGameOK() {
        Game game = GameMother.basicWithId(1L);
        Player playerToJoin = PlayerMother.basicWithId(1L);

        when(gameFinder.findGame(game.getId())).thenReturn(game);
        when(playerFinder.findByName(playerToJoin.getName())).thenReturn(playerToJoin);

        Pair<Player, Optional<Long>> result =
            assertDoesNotThrow(() -> gameModifier.joinGame(game.getId(), playerToJoin.getName()));

        assertEquals(playerToJoin, result.getFirst());
        assertFalse(result.getSecond().isPresent());

        verify(gameFinder, times(1)).findGame(game.getId());
        verify(playerFinder, times(1)).findByName(playerToJoin.getName());
        verify(gameRepository, times(1)).save(any());
        verify(playerRepository, times(1)).save(any());
    }

    @Test
    void joinGameGameNotFound() {
        Game game = GameMother.basicWithId(1L);
        Player playerToJoin = PlayerMother.basicWithId(1L);
        EntityNotFoundException expectedException = new EntityNotFoundException("Game with id " + game.getId() + " not found");

        when(gameFinder.findGame(game.getId())).thenThrow(expectedException);

        EntityNotFoundException actualException =
            assertThrows(EntityNotFoundException.class, () -> gameModifier.joinGame(game.getId(), playerToJoin.getName()));

        assertEquals(expectedException.getMessage(), actualException.getMessage());
        verify(gameFinder, times(1)).findGame(game.getId());
        verify(playerFinder, never()).findByName(playerToJoin.getName());
        verify(gameRepository, never()).save(any());
        verify(playerRepository, never()).save(any());
    }

    @Test
    void joinGamePlayerNotFound() {
        Game game = GameMother.basicWithId(1L);
        Player playerToJoin = PlayerMother.basicWithId(1L);
        EntityNotFoundException expectedException = new EntityNotFoundException("Player with name " + playerToJoin.getName() + " not found");

        when(gameFinder.findGame(game.getId())).thenReturn(game);
        when(playerFinder.findByName(playerToJoin.getName())).thenThrow(expectedException);

        EntityNotFoundException actualException =
            assertThrows(EntityNotFoundException.class, () -> gameModifier.joinGame(game.getId(), playerToJoin.getName()));

        assertEquals(expectedException.getMessage(), actualException.getMessage());
        verify(gameFinder, times(1)).findGame(game.getId());
        verify(playerFinder, times(1)).findByName(playerToJoin.getName());
        verify(gameRepository, never()).save(any());
        verify(playerRepository, never()).save(any());
    }

    @Test
    void joinGameFull() {
        Game game = GameMother.fullWithId(1L);
        Player playerToJoin = PlayerMother.basicWithId(1L);
        GameIsFullException expectedException = new GameIsFullException(game.getId());

        when(gameFinder.findGame(game.getId())).thenReturn(game);
        when(playerFinder.findByName(playerToJoin.getName())).thenReturn(playerToJoin);

        GameIsFullException actualException =
            assertThrows(GameIsFullException.class, () -> gameModifier.joinGame(game.getId(), playerToJoin.getName()));

        assertEquals(expectedException.getMessage(), actualException.getMessage());
        verify(gameFinder, times(1)).findGame(game.getId());
        verify(playerFinder, times(1)).findByName(playerToJoin.getName());
        verify(gameRepository, never()).save(any());
        verify(playerRepository, never()).save(any());
    }

    @Test
    void joinGamePlayerAlreadyJoined() {
        Game game = GameMother.basicWithIdAndPlayer(1L, 1L);
        Player playerToJoin = PlayerMother.basicWithId(1L);
        PlayerAlreadyBelongsGameException expectedException = new PlayerAlreadyBelongsGameException(game.getId());

        when(gameFinder.findGame(game.getId())).thenReturn(game);
        when(playerFinder.findByName(playerToJoin.getName())).thenReturn(playerToJoin);

        PlayerAlreadyBelongsGameException actualException =
            assertThrows(PlayerAlreadyBelongsGameException.class, () -> gameModifier.joinGame(game.getId(), playerToJoin.getName()));

        assertEquals(expectedException.getMessage(), actualException.getMessage());
        verify(gameFinder, times(1)).findGame(game.getId());
        verify(playerFinder, times(1)).findByName(playerToJoin.getName());
        verify(gameRepository, never()).save(any());
        verify(playerRepository, never()).save(any());
    }
}