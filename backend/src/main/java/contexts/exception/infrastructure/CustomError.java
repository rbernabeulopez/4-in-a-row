package contexts.exception.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class CustomError {
    String message;
    Integer httpCode;
    LocalDateTime timestamp;
}
