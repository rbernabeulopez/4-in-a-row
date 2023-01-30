package contexts.movement.infrastructure.websocket;

import contexts.game.application.GameFinder;
import contexts.game.domain.entity.Game;
import contexts.movement.application.MovementFinder;
import contexts.movement.domain.entity.Movement;
import contexts.player.application.PlayerFinder;
import contexts.player.domain.entities.Player;
import lombok.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.LocalTime.now;

@RestController
@AllArgsConstructor
public class CreateMovementSocket {
    private SimpMessagingTemplate simpMessagingTemplate;
    private MovementFinder movementFinder;
    private GameFinder gameFinder;
    private PlayerFinder playerFinder;

    @MessageMapping("make-movement")
    public void joinGame(@Payload CreateMovementRequest request) {
        //Hay que llamar a una función de servicio que verifique que el movimiento es correcto
        //te calcule si hay un ganador o empate y te devuelva un response.
        // te devolveria un PAIR get first, y PAIR get second para obtener dos datos
        // y si todo esta ok se guarda en base de datos
        Player player = playerFinder.findPlayer(request.getPlayerId());
        Game game = gameFinder.findGame(request.getGameId());

        Movement movement = Movement.builder()
                .player(player)
                .game(game)
                .row(request.getRow())
                .col(request.getCol())
                .createdAt(LocalDateTime.now())
                        .build();

        movementFinder.addMovement(movement);
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
