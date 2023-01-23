package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GameFinder {

    private PlayerFinder playerFinder;

    public List<Game> findPlayerGames(long id) {
        log.info("Searching player with id {}", id);
        Player player = playerFinder.findPlayer(id);
        return player.getGames();
    }
}
