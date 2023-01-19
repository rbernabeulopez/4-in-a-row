package contexts.game.infrastructure;

import contexts.game.application.GameCreator;
import contexts.game.domain.entity.Game;
import contexts.player.domain.entities.Player;
import contexts.movement.domain.entity.Movement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("api/v1")
public class GamePostController {
    @Autowired
    private GameCreator gameCreator;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("game")
    public GamePostResponse createGame(@RequestBody GamePostRequest gamePostRequest) {
        Game game = gameCreator.createGame(
                gamePostRequest.getPlayersId(),
                gamePostRequest.getWinner(),
                gamePostRequest.getMovementsId(),
                gamePostRequest.isFinished()
        );

        return new GamePostResponse(      // TODO: Simple Output Vs. Full Output
                game.getId(),
                game.getPlayers().stream().map(Player::getId).collect(Collectors.toList()),
                game.getWinner().getId(),
                game.getMovements().stream().map(Movement::getId).collect(Collectors.toList()),
                game.isFinished()
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GamePostRequest {
    private List<Long> playersId;
    private Long winner;
    private List<Long> movementsId;
    private boolean isFinished = false;
}

@Data
@AllArgsConstructor
class GamePostResponse {
    private long id;
    private List<Long> players;
    private Long winner;
    private List<Long> movements;
    private boolean finished;
}
