package contexts.exception.domain;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid session, please login again.");
    }
}
