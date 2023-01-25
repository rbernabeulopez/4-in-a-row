package contexts.movement.infrastructure.controller;

import contexts.game.application.GameFinder;
import contexts.game.domain.entity.Game;
import contexts.movement.application.MovementFinder;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class MovementPostController {

    private GameFinder gameFinder;
    private MovementFinder movementFinder;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/game")
    public void joinGame(@Payload JoinGameRequest joinGameRequest) {
        simpMessagingTemplate.convertAndSend("/game-notifications/" + joinGameRequest.getGameId(), joinGameRequest);
    }

//    @MessageMapping("/game")
//    @SendTo("/topic/game")
//    public ResponseEntity<Game> gamePlay (@RequestBody Movement movement){
//        log.info("movement: {}", movement);
//        Game game = gameFinder.findGame(movement.getId());
//        game.setMovements(movementFinder.findMovementsById(movement.getId()));
//
//        //notify a player that the other player made a move
//        simpMessagingTemplate.convertAndSend("/topic/game-progress" + game.getId(), game);
//        return ResponseEntity.ok(game);
//    }
}
@Getter
@Setter
class JoinGameRequest {
    private long playerId;
    private long gameId;
}
