package contexts.game.application;

import contexts.exception.domain.EntityNotFoundException;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.game.domain.repository.GameRepository;
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
    private GameRepository gameRepository;

    public Game findGame(long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game with gameId " + gameId + " not found"));
    private PlayerFinder playerFinder;
    private GameRepository gameRepository;

    public List<Game> findPlayerGames(long id) {
        log.info("Searching player with id {}", id);
        Player player = playerFinder.findPlayer(id);
        return player.getGames();
    }


    public Game findGame(long id) {
        log.info("Searching game with id {}", id);
        return gameRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Game with id " + id + " not found")
        );
    }
}
