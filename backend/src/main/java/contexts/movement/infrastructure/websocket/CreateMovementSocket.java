package contexts.movement.infrastructure.websocket;

import contexts.movement.domain.entity.Movement;
import lombok.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateMovementSocket {
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("make-movement")
    public void joinGame(@Payload CreateMovementRequest request) {
        //Hay que llamar a una función de servicio que verifique que el movimiento es correcto
        //te calcule si hay un ganador o empate y te devuelva un response.
        // te devolveria un PAIR get first, y PAIR get second para obtener dos datos
        // y si todo esta ok se guarda en base de datos

        CreateMovementResponse response = new CreateMovementResponse(request.getPlayerId(), null, false,
                request.getRow(), request.getCol());
        simpMessagingTemplate.convertAndSend("/game-notifications/"+ request.getGameId(), response );
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class CreateMovementRequest {
    private long gameId;
    private long playerId;
    private int row;
    private int col;
}

@Getter
@Setter
@AllArgsConstructor
class CreateMovementResponse {
    private long playerId;
    private Long winnerId;
    private boolean isGameFinished;
    private int row;
    private int col;
}
