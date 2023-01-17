package contexts.player.infrastructure.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import contexts.player.application.PlayerCreator;
import contexts.player.domain.entities.Player;

import java.util.UUID;

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
        return PlayerPostMapper.INSTANCE.playerToPlayerPostResponse(player);
    }
}

@Getter
@Setter
class PlayerPostRequest {
    private String name;
    private String password;
}

@Getter
@Setter
class PlayerPostResponse {
    private long id;
    private String name;
    private String ipAddress;
}

@Mapper
interface PlayerPostMapper {
    PlayerPostMapper INSTANCE = Mappers.getMapper(PlayerPostMapper.class);

    PlayerPostResponse playerToPlayerPostResponse(Player player);
}