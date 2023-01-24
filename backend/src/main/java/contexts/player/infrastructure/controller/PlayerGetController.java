package contexts.player.infrastructure.controller;

import contexts.game.domain.entity.Game;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/player")
public class PlayerGetController {
    private PlayerFinder playerFinder;

    @GetMapping("{playerId}/games")
    public List<PlayerGetResponse> getPlayerGameHistory(@PathVariable long playerId) {
        List<PlayerGetResponse> response = new ArrayList<>();
        List<MovementResponse> movements = new ArrayList<>();

        List<Game> games = playerFinder.findGames(playerId);

        Player opponent = games.stream()
            .flatMap(game -> game.getPlayers().stream())
            .filter(player -> player.getId() != playerId)
            .findFirst()
            .orElse(null);

        // Set movements
        games.stream()
            .flatMap(game -> game.getMovements().stream())
            .forEach(movement -> movements.add(
                        new MovementResponse(
                            movement.getId(),
                                new PlayerResponse(
                                        movement.getPlayer().getId(),
                                        movement.getPlayer().getName()
                                ),
                                movement.getRow(),
                                movement.getCol(),
                                movement.getCreatedAt()
                        )));

        // Create response
        games.forEach(game -> response.add(
                new PlayerGetResponse(
                        game.getId(),
                        opponent != null ? new PlayerResponse(opponent.getId(), opponent.getName()) : null,
                        game.getWinner() != null ? new PlayerResponse(game.getWinner().getId(), game.getWinner().getName()) : null,
                        movements
                )));

        return response;
    }
}

@Data
@AllArgsConstructor
class PlayerGetResponse {
    private long id;
    private PlayerResponse opponent;
    private PlayerResponse winner;
    private List<MovementResponse> movements;
}

@Data
@AllArgsConstructor
class PlayerResponse {
    private long id;
    private String name;
}

@Data
@AllArgsConstructor
class MovementResponse {
    private long id;
    private PlayerResponse player;
    private int row;
    private int col;
    private LocalDateTime createdAt;
}
