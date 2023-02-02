package contexts.movement.application;
import contexts.movement.domain.entity.Movement;
import contexts.movement.domain.repository.MovementRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MovementFinder {
    private MovementRepository movementRepository;

    public List<Movement> findMovementsById(long gameId) {
        log.info("Searching movements for game with id {}", gameId);
        return movementRepository.getByGameId(gameId);
    }

    public List<Movement> findMovementByGameIdAndColumn(long gameId, int column){
        log.info("movements from column {} and gameId {}",column, gameId);
        return movementRepository.findByGameIdAndCol(gameId, column);
    }

    public Movement findLastMovement(long gameId){
        List<Movement> listMovements = findMovementsById(gameId);
        return  listMovements.size() != 0 ? listMovements.get(listMovements.size()-1) : null;
    }

}
