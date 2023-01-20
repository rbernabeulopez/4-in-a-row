package contexts.player.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import contexts.player.domain.exception.InvalidValueException;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class PlayerPassword {
    @JsonValue
    private String password;

    public PlayerPassword(String password) {
        if (password.length() < 1 || password.length() > 20) {
            throw new InvalidValueException("Password must be between 8 and 20 characters");
        }
        this.password = password;
    }
}
