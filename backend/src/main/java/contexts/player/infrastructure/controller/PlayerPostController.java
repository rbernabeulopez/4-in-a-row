package contexts.player.infrastructure.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import contexts.player.application.PlayerCreator;
import contexts.player.domain.entities.Player;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class PlayerPostController {
    private PlayerCreator playerCreator;

    private HttpServletRequest httpServletRequest;

    @ResponseStatus(HttpStatus.CREATED)
     @PostMapping("player")
    public PlayerPostResponse registerPlayer(@RequestBody PlayerPostRequest playerPostRequest) {
        Player player = playerCreator.createPlayer(
             playerPostRequest.getName(),
             playerPostRequest.getPassword(),
             httpServletRequest.getRemoteAddr()
        );

        return new PlayerPostResponse(
            player.getId(),
            player.getName().getName(),
            player.getIpAddress().getIpAddress()
        );
    }
}

@Getter
@Setter
class PlayerPostRequest {
    private String name;
    private String password;
}

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class PlayerPostResponse {
    private long id;
    private String name;
    private String ipAddress;
}