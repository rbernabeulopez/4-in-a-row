package contexts.movement.domain.repository;

import contexts.game.domain.entity.Game;
import contexts.movement.domain.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> getByGameId(Long gameId);
    List<Movement> findByGameIdAndCol(Long gameId, int col);
}
