package contexts.player.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import contexts.player.domain.exception.InvalidValueException;

@Getter
@EqualsAndHashCode
@ToString
public class PlayerPassword {
    @JsonValue
    private final String password;

    public PlayerPassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            throw new InvalidValueException("Password must be between 8 and 20 characters");
        }
        this.password = password;
    }
}
