package contexts.game.application;

import contexts.game.domain.entity.Game;
import contexts.game.domain.repository.GameRepository;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import contexts.player.domain.vo.PlayerIpAddress;
import contexts.player.domain.vo.PlayerName;
import contexts.player.domain.vo.PlayerPassword;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GameFinder {

    private PlayerRepository playerRepository;

    public List<Game> findPlayerGames(long id) {
        log.info("Searching player with id {}", id);
        Player player = playerRepository.findById(id).orElseThrow();
        return player.getGames();
    }
}
