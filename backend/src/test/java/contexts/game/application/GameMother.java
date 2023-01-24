package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.player.application.PlayerMother;

import java.util.List;

public class GameMother {
    public static Game basicWithId(Long id) {
        return Game.builder()
            .id(id)
                .players(List.of(PlayerMother.basicWithId(1L)))
            .build();
    }
}
