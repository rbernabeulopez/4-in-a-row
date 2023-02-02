package contexts.player.application;

import contexts.UnitTestsBase;
import contexts.exception.domain.InvalidValueException;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerCreatorTest extends UnitTestsBase {

    @InjectMocks
    private PlayerCreator playerCreator;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreatePlayer() {
        Player expectedPlayer = PlayerMother.basicWithId(0L);

        when(passwordEncoder.encode(expectedPlayer.getPassword())).thenReturn(expectedPlayer.getPassword());
        when(playerRepository.save(any())).thenReturn(expectedPlayer);

        Player actualPlayer =
            assertDoesNotThrow(() -> playerCreator.createPlayer(
                expectedPlayer.getName(),
                expectedPlayer.getPassword(),
                expectedPlayer.getIpAddress()
            ));

        assertEquals(expectedPlayer.getName(), actualPlayer.getName());
        assertEquals(expectedPlayer.getPassword(), actualPlayer.getPassword());
        assertEquals(expectedPlayer.getIpAddress(), actualPlayer.getIpAddress());

        verify(passwordEncoder, times(1)).encode(expectedPlayer.getPassword());
        verify(playerRepository, times(1)).save(expectedPlayer);
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenNameIsNull() {
        Player player = PlayerMother.basicWithNullName();

        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> {
            playerCreator.createPlayer(
                    player.getName(),
                    player.getPassword(),
                    player.getIpAddress()
            );
        });

        assertEquals("Player name cannot be null", exception.getMessage());
        verify(passwordEncoder, never()).encode(anyString());
        verify(playerRepository, never()).save(player);
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenPasswordIsShort() {
        Player player = PlayerMother.basicWithShortPassword();

        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> {
            playerCreator.createPlayer(
                    player.getName(),
                    player.getPassword(),
                    player.getIpAddress()
            );
        });

        assertEquals("Password must be between 4 and 20 characters", exception.getMessage());
        verify(passwordEncoder, never()).encode(anyString());
        verify(playerRepository, never()).save(player);
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenPasswordIsLong() {
        Player player = PlayerMother.basicWithLongPassword();

        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> {
            playerCreator.createPlayer(
                    player.getName(),
                    player.getPassword(),
                    player.getIpAddress()
            );
        });

        assertEquals("Password must be between 4 and 20 characters", exception.getMessage());
        verify(passwordEncoder, never()).encode(anyString());
        verify(playerRepository, never()).save(player);
    }

    @Test
    void shouldThrowInvalidValueExceptionWhenIpAddressIsInvalid() {
        Player player = PlayerMother.basicWithInvalidIpAddress();

        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> {
            playerCreator.createPlayer(
                    player.getName(),
                    player.getPassword(),
                    player.getIpAddress()
            );
        });

        assertEquals("Invalid IP address", exception.getMessage());
        verify(passwordEncoder, never()).encode(anyString());
        verify(playerRepository, never()).save(player);
    }
}