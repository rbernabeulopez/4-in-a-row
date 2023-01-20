package contexts.game.infrastructure;

import contexts.game.application.GameCreator;
import contexts.game.domain.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
class GamePostResponse {
    private long id;
}
