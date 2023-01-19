package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameCreator {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public Game createGame(
            List<Long> playersId, Long winnerId, List<Long> movementsId, boolean finished
    ) {
        List<Optional<Player>> players = playersId.stream().map(playerRepository::findById).collect(Collectors.toList());
        List<Optional<Movement>> movements = List.of(); // TODO: MovementRepository
        Player winner = playerRepository.findById(winnerId).orElse(null); // TODO: Not found exceptions

        return gameRepository.save(Game.builder()
                .players(players.stream().map(Optional::get).collect(Collectors.toList()))
                .winner(winner)
                .movements(movements.stream().map(Optional::get).collect(Collectors.toList()))
                .finished(finished)
                .build());
    }
}
