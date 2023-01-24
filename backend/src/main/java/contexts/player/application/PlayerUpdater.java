package contexts.player.application;

import contexts.exception.domain.EntityNotFoundException;
import contexts.game.domain.entity.Game;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerUpdater {
    private PlayerRepository playerRepository;

    public void addGame(Game newGame, long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new EntityNotFoundException("Player with id " + playerId + " not found"));
        player.getGames().add(newGame);

        log.info("Adding game {} to player {}", newGame, player);

        playerRepository.save(player);
    }
}
