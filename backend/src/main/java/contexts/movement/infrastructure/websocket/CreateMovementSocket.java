package contexts.movement.infrastructure.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateMovementSocket {
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("make-movement")
    public void joinGame() {
        simpMessagingTemplate.convertAndSend("/game-notifications/1", "Hello");
    }
}

@Getter
@Setter
class CreateMovementRequest {
    private long gameId;
    private long playerId;
    private int row;
    private int col;
}

@Getter
@Setter
class CreateMovementResponse {
    private long playerId;
    private Long winnerId;
    private boolean isGameFinished;
    private int row;
    private int col;
}
