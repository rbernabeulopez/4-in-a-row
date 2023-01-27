package contexts.exception.domain;

public class GameIsFullException extends RuntimeException {
    public GameIsFullException(long gameId) {
        super("Game with id " + gameId + " is full");
    }
}
