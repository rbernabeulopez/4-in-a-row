package contexts.movement.domain.entity;

import contexts.game.domain.entity.Game;
import contexts.player.domain.entities.Player;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movement {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Game game;

    private int row;

    private int col;

    private LocalDateTime createdAt;
}
