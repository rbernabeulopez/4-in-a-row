package contexts.game.infrastructure.controller;

import contexts.game.application.GameFinder;
import contexts.game.domain.entity.Game;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game")
public class GameGetController {

    private GameFinder gameFinder;


    @GetMapping("{id}")
    public GameGetResponse getGame(@PathVariable long id) {
        Game game = gameFinder.findGame(id);

        List<MovementResponse> movements = new ArrayList<>();
        PlayerResponse winner = GameGetMapper.INSTANCE.playerToPlayerResponse(game.getWinner());
        List<PlayerResponse> players = game.getPlayers().stream().map(
                GameGetMapper.INSTANCE::playerToPlayerResponse
        ).toList();
        game.getMovements().forEach(movement -> movements.add(
                GameGetMapper.INSTANCE.movementToMovementResponse(movement)
        ));
    //Obtener datos principales de carga desde este endpoints
        return new GameGetResponse(
                game.getId(),
                winner,
                players,
                movements,
                game.isFinished()
                );
    }
}


@Data
@AllArgsConstructor
class GameGetResponse {
    private long id;
    private PlayerResponse winner;
    private List<PlayerResponse> players;
    private List<MovementResponse> movements;
    private boolean finished;
}

@Data
class PlayerResponse {
    private long id;
    private String name;
}

@Data
class MovementResponse {
    private long id;
    private int row;
    private int col;
    private PlayerResponse player;
}

@Mapper
interface GameGetMapper {
    GameGetMapper INSTANCE = Mappers.getMapper(GameGetMapper.class);

    GameGetResponse gameToGameGetResponse(Game game);
    PlayerResponse playerToPlayerResponse(Player player);
    MovementResponse movementToMovementResponse(Movement movement);
}