package contexts.player.application;

import contexts.UnitTestCase;
import contexts.exception.domain.EntityNotFoundException;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerFinderTest extends UnitTestCase {
    @InjectMocks
    private PlayerFinder playerFinder;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void shouldReturnPlayer() {
        Player expectedPlayer = PlayerMother.basicWithId(1L);
        when(playerRepository.findById(expectedPlayer.getId())).thenReturn(Optional.of(expectedPlayer));

        Player actualPlayer =
                assertDoesNotThrow(() -> playerFinder.findPlayer(expectedPlayer.getId()));

        assertEquals(expectedPlayer, actualPlayer);
        verify(playerRepository, times(1)).findById(expectedPlayer.getId());
    }

    @Test
    public void shouldThrowExceptionWhenPlayerNotFound() {
        when(playerRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException actualException =
            assertThrows(EntityNotFoundException.class, () -> playerFinder.findPlayer(1L));

        assertEquals("Player with id " + 1L + " not found", actualException.getMessage());
        verify(playerRepository, times(1)).findById(1L);
    }
}