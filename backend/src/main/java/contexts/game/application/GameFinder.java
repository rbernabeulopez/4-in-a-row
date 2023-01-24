package contexts.game.application;

import contexts.exception.domain.EntityNotFoundException;
import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GameFinder {
    private GameRepository gameRepository;
    public Game findGame(long id) {
        return gameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Game with id " + id + " not found"));
    }
}
