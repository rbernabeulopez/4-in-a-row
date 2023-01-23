package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameCreator {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerFinder playerFinder;

    public Game createGame(Long player1Id) {
        Player player = playerFinder.findPlayer(player1Id);

        return gameRepository.save(
                Game.builder()
                .players(List.of(player))
                .build()
        );
    }
}
