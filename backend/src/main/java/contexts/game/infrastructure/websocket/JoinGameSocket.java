package contexts.game.infrastructure.websocket;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class JoinGameSocket {
    // private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("join-game")
    @SendTo("/game-notifications")
    public JoinGameRequest joinGame(@Payload JoinGameRequest request) {
        log.info("Received join game request: {}", request);
        return request;
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
class JoinGameRequest {
    private String gameId;
    private String playerId;
}