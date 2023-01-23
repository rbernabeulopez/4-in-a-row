package contexts.game.domain.repository;

import contexts.game.domain.entity.Game;
import contexts.player.domain.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> getByPlayers(Player player);

}
