package contexts.game.domain.entity;

import contexts.exception.domain.GameIsFullException;
import contexts.exception.domain.PlayerAlreadyBelongsGameException;
import contexts.exception.domain.PlayerDoesNotBelongGameException;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(mappedBy = "games",fetch = FetchType.EAGER)
    private List<Player> players;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player winner;

    @OneToMany(mappedBy = "game",fetch = FetchType.EAGER)
    private List<Movement> movements;

    private boolean finished;

    private static final int MAX_PLAYERS = 2;

    public void joinPlayer(Player player) {
        if (players.size() == MAX_PLAYERS) {
            throw new GameIsFullException(this.id);
        }
        if (players.contains(player)) {
            throw new PlayerAlreadyBelongsGameException(this.id);
        }
        players.add(player);
    }

    public void checkPlayerBelongs(Player player) {
        if(!players.contains(player)) {
            throw new PlayerDoesNotBelongGameException(this.id);
        }
    }

    public Player getStartingPlayer() {
        return (players.size() == MAX_PLAYERS) ? players.get(0) : null;
    }
}
