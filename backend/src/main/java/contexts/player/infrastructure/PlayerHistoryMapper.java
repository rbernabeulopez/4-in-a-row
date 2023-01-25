package contexts.player.infrastructure;

import contexts.game.domain.entity.Game;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class PlayerHistoryMapper {
    public Player getOpponent(List<Game> games, long playerId) { // Get opponent by iterating games[].players[] and getting the player with different id or null if it doesn't exist
        return games.stream()
                .flatMap(game -> game.getPlayers().stream())
                .filter(player -> player.getId() != playerId)
                .findFirst()
                .orElse(null);
    }

    public List<Movement> getMovements(List<Game> games) {
        return games.stream()
                .flatMap(game -> game.getMovements().stream())
                .toList();
    }
}
