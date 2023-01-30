package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.player.application.PlayerMother;

import java.util.ArrayList;
import java.util.List;

public class GameMother {
    public static Game basicWithIdAndPlayer(Long id, Long playerId) {
        return Game.builder()
            .id(id)
            .players(List.of(PlayerMother.basicWithId(playerId)))
            .build();
    }

    public static Game basicWithId(long id) {
        return Game.builder()
            .id(id)
            .players(new ArrayList<>())
            .build();
    }

    public static Game fullWithId(long id) {
        return Game.builder()
            .id(id)
            .players(List.of(PlayerMother.basicWithId(1L), PlayerMother.basicWithId(2L)))
            .build();
    }
}
