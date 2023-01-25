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

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/player")
public class PlayerGetController {
    private PlayerFinder playerFinder;

    @GetMapping("{playerId}/games")
    public List<PlayerHistoryGameResponse> getPlayerGameHistory(@PathVariable long playerId) {
        List<PlayerHistoryGameResponse> response = new ArrayList<>();

        List<Game> games = playerFinder.findGames(playerId);

        List<Player> players = games.stream().flatMap(game -> game.getPlayers().stream()).toList();
        List<PlayerResponse> playersResponse = players.stream().map(player -> new PlayerResponse(player.getId(), player.getName())).toList();


        // Create response, which is a list of the games played by the player, setting the opponent, the winner and the movements of each game
        games.forEach(game -> response.add(
                new PlayerHistoryGameResponse( // Mapping game to PlayerHistoryGameResponse
                        game.getId(),
                        playersResponse,
                        game.getWinner() != null ?
                                new PlayerResponse( // Mapping winner to PlayerResponse if it exists, else null
                                        game.getWinner().getId(),
                                        game.getWinner().getName()
                                )
                                : null
                )));

        return response;
    }
}

@Data
@AllArgsConstructor
class PlayerHistoryGameResponse {
    private long id;
    private List<PlayerResponse> players;
    private PlayerResponse winner;
}

@Data
@AllArgsConstructor
class PlayerResponse {
    private long id;
    private String name;
}
