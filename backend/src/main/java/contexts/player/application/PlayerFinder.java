package contexts.player.application;

import contexts.exception.domain.EntityNotFoundException;
import contexts.exception.domain.MovementException;
import contexts.game.domain.entity.Game;
import contexts.movement.application.MovementFinder;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerFinder {
    private PlayerRepository playerRepository;
    private MovementFinder movementFinder;

    public Player findPlayer(long id) {
        log.info("Searching player with id {}", id);

        return playerRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Player with id " + id + " not found")
        );
    }

    public Player findByName(String name) {
        log.info("Searching player with name {}", name);

        return playerRepository.findByName(name).orElseThrow(
            () -> new EntityNotFoundException("Player with name " + name + " not found")
        );
    }

    public List<Game> findGames(long id) {
        log.info("Searching games of player with id {}", id);
        return findPlayer(id).getGames();
    }

    public void checkPlayerTurn(long gameId, long playerId){
       Movement lastMovement = movementFinder.findLastMovement(gameId);
       if(lastMovement != null && lastMovement.getPlayer().getId() == playerId){
           throw new MovementException("Player not turn");
       }
    }

}
