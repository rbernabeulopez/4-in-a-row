package contexts.player.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import contexts.player.domain.entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
