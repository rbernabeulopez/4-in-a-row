package contexts.player.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import contexts.player.domain.entities.Player;
import contexts.player.domain.vo.PlayerIpAddress;
import contexts.player.domain.vo.PlayerName;
import contexts.player.domain.vo.PlayerPassword;
import contexts.player.domain.repository.PlayerRepository;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerCreator {
    private PlayerRepository playerRepository;

    public Player createPlayer(String name, String password, String ipAddress) {
        log.info("Creating contexts.player with name {} from ipAddress {}", name, ipAddress);
        Player player = Player.builder()
                .name(new PlayerName(name))
                .password(new PlayerPassword(password))
                .ipAddress(new PlayerIpAddress(ipAddress))
                .build();
        return playerRepository.save(player);
    }

}
