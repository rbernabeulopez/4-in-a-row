package contexts.player.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@EqualsAndHashCode
@ToString
public class PlayerName {
    @JsonValue
    private final String name;

    public PlayerName(String name) {
        Objects.requireNonNull(name, "Player name cannot be null");
        this.name = name;
    }
}
