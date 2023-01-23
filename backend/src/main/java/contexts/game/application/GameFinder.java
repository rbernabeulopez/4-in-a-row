package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GameFinder {

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;

    public List<Game> findPlayerGames(long id) {
        log.info("Searching player with id {}", id);
        Player player = playerRepository.findById(id).orElseThrow();
        return gameRepository.getByPlayers(player);
    }
}
