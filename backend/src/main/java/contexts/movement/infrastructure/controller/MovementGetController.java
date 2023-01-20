package contexts.movement.infrastructure.controller;
import contexts.game.domain.entity.Game;
import contexts.movement.application.MovementFinder;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class MovementGetController {
    private MovementFinder movementFinder;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{id}/movements")
    public List<MovementGetResponse> getMovementsById(@PathVariable long id) {
        List<Movement> movements = movementFinder.findMovementsById(id);
        return movements.stream().map(MovementGetMapper.INSTANCE::movementToMovementGetResponse).toList();
    }
}


@Getter
@Setter
class MovementGetResponse {
    private long id;
    private Player player;
    private Game game;
    private int row;
    private int col;
    private LocalDateTime createdAt;
}

@Mapper
interface MovementGetMapper {
    MovementGetMapper INSTANCE = Mappers.getMapper(MovementGetMapper.class);

    MovementGetResponse movementToMovementGetResponse(Movement movement);
}