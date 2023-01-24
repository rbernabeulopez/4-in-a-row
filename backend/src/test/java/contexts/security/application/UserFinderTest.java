package contexts.security.application;

import contexts.UnitTestsBase;
import contexts.player.application.PlayerMother;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserFinderTest extends UnitTestsBase {

    @InjectMocks
    private UserFinder userFinder;
    
    @Mock
    private PlayerRepository playerRepository;

    @Test
    void willReturnUser_WhenUserExists() {
        Player player = PlayerMother.basicWithoutId();
        when(playerRepository.findByName(player.getName())).thenReturn(Optional.of(player));

        UserDetails userDetails = userFinder.loadUserByUsername(player.getName());

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(player.getName());
        verify(playerRepository, times(1)).findByName(player.getName());
    }

    @Test
    void willReturnNull_WhenUserNotExists() {
        String email = "foo@foo.com";
        when(playerRepository.findByName(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userFinder.loadUserByUsername(email))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");

        verify(playerRepository, times(1)).findByName(email);
    }
}