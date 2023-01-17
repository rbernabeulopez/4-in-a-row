package contexts.player.domain.entities;

import contexts.game.domain.entity.Game;
import jakarta.persistence.*;
import lombok.*;
import contexts.player.domain.vo.PlayerIpAddress;
import contexts.player.domain.vo.PlayerName;
import contexts.player.domain.vo.PlayerPassword;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private PlayerName name;

    @Embedded
    private PlayerPassword password;

    @Embedded
    private PlayerIpAddress ipAddress;

    @ManyToMany
    private List<Game> games;

    @OneToMany(mappedBy = "winner", fetch = FetchType.EAGER)
    private List<Game> wonGames;
}
