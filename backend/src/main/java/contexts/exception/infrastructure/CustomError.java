package contexts.exception.infrastructure;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CustomError {
    LocalDateTime timestamp;
    Integer httpCode;
    String message;
}
