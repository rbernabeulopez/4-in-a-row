package contexts.game.infrastructure.controller;

import contexts.game.application.GameModifier;
import contexts.player.domain.entities.Player;
import contexts.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/game")
public class GamePatchController {

    private GameModifier gameModifier;
    private SimpMessagingTemplate simpMessagingTemplate;

    @PatchMapping("join")
    public void joinGame(@AuthenticationPrincipal User user, @RequestBody GamePatchRequest request) {
        Pair<Player, Optional<Long>> players = gameModifier.joinGame(request.getGameId(), user.getUsername());
        GamePatchResponse gamePatchResponse = new GamePatchResponse(players.getFirst(), players.getSecond());
        simpMessagingTemplate.convertAndSend("/game-notifications/" + request.getGameId(), gamePatchResponse);
    }
}

@Getter
@Setter
class GamePatchRequest {
    private long gameId;
}

@NoArgsConstructor
@Getter
@Setter
class GamePatchResponse {
    private GamePatchPlayerResponse joinedPlayer;
    private long startingPlayerId;

    public GamePatchResponse(Player joinedPlayer, Optional<Long> optionalFirstPlayerId) {
        this.joinedPlayer = new GamePatchPlayerResponse(joinedPlayer);
        this.startingPlayerId = optionalFirstPlayerId.orElse(-1L);
    }
}

@Getter
@Setter
class GamePatchPlayerResponse {
    private long playerId;
    private String name;

    public GamePatchPlayerResponse(Player firstPlayer) {
        this.playerId = firstPlayer.getId();
        this.name = firstPlayer.getName();
    }
}