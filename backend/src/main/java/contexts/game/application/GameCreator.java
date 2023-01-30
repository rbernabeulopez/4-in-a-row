package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerCreator;
import contexts.player.application.PlayerFinder;
import contexts.player.application.PlayerUpdater;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GameCreator {

    private GameRepository gameRepository;
    private PlayerFinder playerFinder;
    private PlayerCreator playerCreator;
    private PlayerUpdater playerUpdater;

    public Game createGame(Long player1Id) {

        Player player = playerFinder.findPlayer(player1Id);
        log.info("Creating game for player {}", player);

        return gameRepository.save(
                Game.builder()
                        .players(List.of(player))
                        .build()
        );
    }

    public void updatePlayer(Game gameSaved) {
        gameSaved.getPlayers().forEach(player -> playerUpdater.addGame(gameSaved, player.getId()));
    }
}
