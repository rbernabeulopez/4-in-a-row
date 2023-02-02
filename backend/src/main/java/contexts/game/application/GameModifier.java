package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class GameModifier {

    private GameFinder gameFinder;

    private PlayerFinder playerFinder;

    private GameRepository gameRepository;

    private PlayerRepository playerRepository;

    public Pair<Player, Optional<Long>> joinGame(long gameId, String playerName) {
        log.info("Joining game {} with playerToJoin {}", gameId, playerName);
        Game game = gameFinder.findGame(gameId);
        Player playerToJoin = playerFinder.findByName(playerName);

        game.joinPlayer(playerToJoin);
        gameRepository.save(game);
        playerRepository.save(playerToJoin);

        Player startingPlayer = game.getStartingPlayer();
        Optional<Long> startingPlayerId = Optional.ofNullable(startingPlayer).map(Player::getId);
        return Pair.of(playerToJoin, startingPlayerId);
    }

    public void setWinner(Game game, Player winner) {
        game.setWinner(!winner.equals(new Player()) ? winner : null);
        game.setFinished(true);
        gameRepository.save(game);
    }
}
