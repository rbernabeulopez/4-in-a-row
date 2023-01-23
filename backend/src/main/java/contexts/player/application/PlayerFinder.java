package contexts.player.application;

import contexts.exception.domain.EntityNotFoundException;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerFinder {
    private PlayerRepository playerRepository;

    public Player findPlayer(long id) {
        log.info("Searching player with id {}", id);
        return playerRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Player with id " + id + " not found")
        );
    }
}
