package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameCreator {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository; // TODO: change to PlayerFinder

    public Game createGame(
            Long player1Id
    ) {
        Optional<Player> player = playerRepository.findById(player1Id);

        return gameRepository.save(Game.builder()
                .players(List.of(player.get()))
                .build());
    }
}
