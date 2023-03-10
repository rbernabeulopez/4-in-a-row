package contexts.player.application;

import contexts.exception.domain.EntityNotFoundException;
import contexts.exception.domain.InvalidValueException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerCreator {
    private PlayerRepository playerRepository;
    private PasswordEncoder passwordEncoder;

    private boolean isValidIp(String ip) {
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public Player createPlayer(String name, String password, String ipAddress) {
        log.info("Creating contexts.player with name {} from ipAddress {}", name, ipAddress);

        if(name == null) {
            throw new InvalidValueException("Player name cannot be null");
        }


        if(playerRepository.existsByName(name)){
            throw new InvalidValueException("The player has already created");
        }

        if (password.length() < 4 || password.length() > 20) {
            throw new InvalidValueException("Password must be between 4 and 20 characters");
        }

        if (!isValidIp(ipAddress)) {
            throw new InvalidValueException("Invalid IP address");
        }

        Player player = Player.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .ipAddress(ipAddress)
                .build();
        return playerRepository.save(player);
    }

}
