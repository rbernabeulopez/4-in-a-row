package contexts.movement.application;

import contexts.exception.domain.MovementException;
import contexts.game.domain.entity.Game;
import contexts.movement.domain.entity.Movement;
import contexts.movement.domain.repository.MovementRepository;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MovementCreator {

    private PlayerFinder playerFinder;
    private MovementRepository movementRepository;

    private MovementFinder movementFinder;

    public Player addMovement(Movement movement){
        log.info("Saving movement with id {}", movement.getId());
        List<Movement> listMovements = movementFinder.findMovementByGameIdAndColumn(movement.getGame().getId(), movement.getCol());
        checkMovement(listMovements, movement);
        playerFinder.checkPlayerTurn(movement.getGame().getId(), movement.getPlayer().getId());
        Movement createdMovement = movementRepository.save(movement);
        return createdMovement.getGame().checkWinner(createdMovement);
    };

    private void checkMovement(List<Movement> listMovements, Movement movement){
        for(int row = 5; row >= 0; row--){
            int finalRow = row;
            boolean isRowFull = listMovements.stream().anyMatch(movement1 -> movement1.getRow() == finalRow);
            if(!isRowFull && movement.getRow() == row){
                 return;
            } else if(!isRowFull) {
                throw new MovementException("Row is empty but movement.row not match");
            } else if(movement.getRow() == row) {
                throw new MovementException("Row is not empty");
            }
        }
    }


}
