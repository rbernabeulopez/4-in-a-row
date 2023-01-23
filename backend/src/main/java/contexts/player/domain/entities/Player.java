package contexts.player.domain.entities;

import contexts.game.domain.entity.Game;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Player {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String password;

    private String ipAddress;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Game> games;

    @OneToMany(mappedBy = "winner", fetch = FetchType.EAGER)
    private List<Game> wonGames;
}
