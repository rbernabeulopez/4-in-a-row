package contexts.game.infrastructure.controller;

import contexts.game.domain.entity.Game;
import contexts.game.application.GameFinder;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Histórico de partidas
@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class GameGetController {

    private GameFinder gameFinder;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{id}/history")
    public List<GameGetResponse> getHistory(@PathVariable long id) {
        List<Game> games = gameFinder.findPlayerGames(id);
        return games.stream().map(GameGetMapper.INSTANCE::gameToGameGetResponse).toList();
    }
}


@Getter
@Setter
class GameGetResponse {
    private long id;
    private Player winner;
    private List<Player> players;
    private List<Movement> movements;
    private boolean finished;

}

@Mapper
interface GameGetMapper {
    GameGetMapper INSTANCE = Mappers.getMapper(GameGetMapper.class);

    GameGetResponse gameToGameGetResponse(Game game);
}