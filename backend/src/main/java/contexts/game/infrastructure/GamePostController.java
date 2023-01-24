package contexts.game.infrastructure;

import contexts.game.application.GameCreator;
import contexts.game.domain.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/game")
public class GamePostController {
    private GameCreator gameCreator;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GamePostResponse createGame(@RequestBody GamePostRequest gamePostRequest) {
        Game game = gameCreator.createGame(
                gamePostRequest.getPlayer1Id()
        );

        return new GamePostResponse(
                game.getId()
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GamePostRequest {
    private Long player1Id;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class GamePostResponse {
    private long id;
}
