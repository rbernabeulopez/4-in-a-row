package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GameModifier {

    private GameFinder gameFinder;

    private PlayerFinder playerFinder;

    public Player joinGame(long gameId, long playerId) {
        log.info("Joining game {} with player {}", gameId, playerId);
        Game game = gameFinder.findGame(gameId);
        Player player = playerFinder.findPlayer(playerId);

        game.checkPlayerBelongs(player);

        return game.getStartingPlayer();
    }
}
