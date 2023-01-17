package contexts.player.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import contexts.player.domain.vo.PlayerIpAddress;
import contexts.player.domain.vo.PlayerName;
import contexts.player.domain.vo.PlayerPassword;


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
}
