package contexts.movement.application;
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
    private MovementRepository movementRepository;

    public List<Movement> findMovementsById(long gameId) {
        log.info("Searching movements for game with id {}", gameId);
        return movementRepository.getByGameId(gameId);
    }

    public Movement addMovement(Movement movement){
        log.info("Saving movement with id {}", movement.getId());
        return movementRepository.save(movement);
    };


}
