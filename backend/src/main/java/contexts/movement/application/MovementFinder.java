package contexts.movement.application;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.movement.domain.entity.Movement;
import contexts.movement.domain.repository.MovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MovementFinder {

    private GameRepository gameRepository;
    private MovementRepository movementRepository;

    public List<Movement> findMovementsById(long id) {
        log.info("Searching movements for game with id {}", id);
        Game game = gameRepository.findById(id).orElseThrow();
        return movementRepository.getByGameId(game);
    }

}
