package contexts.game.infrastructure.websocket;

import contexts.exception.domain.EntityNotFoundException;
import contexts.exception.infrastructure.CustomError;
import contexts.game.application.GameModifier;
import contexts.player.domain.entities.Player;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class JoinGameSocket {
    private SimpMessagingTemplate simpMessagingTemplate;

    private GameModifier gameModifier;

    @MessageMapping("join-game")
    public void joinGame(@Payload JoinGameRequest request) {
        try {
            Player firstPlayer = gameModifier.joinGame(request.getGameId(), request.getPlayerId());
            JoinGameResponse joinGameResponse = new JoinGameResponse(request.getPlayerId(), firstPlayer);
            simpMessagingTemplate.convertAndSend("/game-notifications/" + request.getGameId(), joinGameResponse);
        } catch (EntityNotFoundException entityNotFoundException) {
            CustomError error = new CustomError(entityNotFoundException.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
            simpMessagingTemplate.convertAndSend("/personal-notifications/" + request.getPlayerId(), error);
        } catch (Exception e) {
            CustomError error = new CustomError(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
            simpMessagingTemplate.convertAndSend("/personal-notifications/" + request.getPlayerId(), error);
        }
    }
}

@Getter
@Setter
class JoinGameRequest {
    private long gameId;
    private long playerId;
}

@NoArgsConstructor
@Getter
@Setter
class JoinGameResponse {
    private long joinedPlayerId;
    private JoinGamePlayerResponse startingPlayerId;

    public JoinGameResponse(long joinedPlayerId, Player firstPlayer) {
        this.joinedPlayerId = joinedPlayerId;
        this.startingPlayerId = new JoinGamePlayerResponse(firstPlayer);
    }
}

@Getter
@Setter
class JoinGamePlayerResponse {
    private long playerId;
    private String name;

    public JoinGamePlayerResponse(Player firstPlayer) {
        this.playerId = firstPlayer.getId();
        this.name = firstPlayer.getName();
    }
}