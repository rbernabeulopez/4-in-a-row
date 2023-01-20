package contexts.game.domain.entity;

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

    @ManyToMany(mappedBy = "games", fetch = FetchType.EAGER)
    private List<Player> players;

    @ManyToOne
    private Player winner;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<Movement> movements;

    private boolean finished;
}
