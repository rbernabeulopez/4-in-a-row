package contexts.player.infrastructure.controller;

import contexts.game.domain.entity.Game;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import contexts.player.infrastructure.PlayerHistoryMapper;
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
    private PlayerHistoryMapper mapper;

    @GetMapping("{playerId}/games")
    public List<PlayerHistoryGameResponse> getPlayerGameHistory(@PathVariable long playerId) {
        List<PlayerHistoryGameResponse> response = new ArrayList<>();
        List<MovementResponse> movements = new ArrayList<>();

        List<Game> games = playerFinder.findGames(playerId);

        Player player2 = mapper.getOpponent(games, playerId);
        PlayerResponse opponent = player2 != null ? new PlayerResponse( // Mapping opponent to PlayerResponse if it exists, else null
                player2.getId(), player2.getName()) : null;

        // Set movements by iterating over games[].movements[] and adding each movement to the movements list
        mapper.getMovements(games)
                .forEach(movement -> movements.add(
                        new MovementResponse( // Mapping movement to MovementResponse
                                movement.getId(),
                                new PlayerResponse( // Mapping player to PlayerResponse
                                        movement.getPlayer().getId(),
                                        movement.getPlayer().getName()
                                ),
                                movement.getRow(),
                                movement.getCol(),
                                movement.getCreatedAt()
                        )));

        // Create response, which is a list of the games played by the player, setting the opponent, the winner and the movements of each game
        games.forEach(game -> response.add(
                new PlayerHistoryGameResponse( // Mapping game to PlayerHistoryGameResponse
                        game.getId(),
                        opponent,
                        game.getWinner() != null ?
                                new PlayerResponse( // Mapping winner to PlayerResponse if it exists, else null
                                        game.getWinner().getId(),
                                        game.getWinner().getName()
                                )
                                : null,
                        movements
                )));

        return response;
    }
}

@Data
@AllArgsConstructor
class PlayerHistoryGameResponse {
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
