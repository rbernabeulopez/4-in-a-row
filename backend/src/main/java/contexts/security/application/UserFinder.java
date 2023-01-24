package contexts.security.application;

import contexts.player.domain.entities.Player;
import contexts.player.domain.repository.PlayerRepository;
import contexts.security.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserFinder implements UserDetailsService {

    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String name) {
        Player user = playerRepository.findByName(name).orElse(null);
        if(user == null) {
            throw new RuntimeException("User not found");
        }

        return new User(user);
    }
}
