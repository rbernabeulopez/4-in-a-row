package contexts.exception.domain;

public class PlayerDoesNotBelongGameException extends RuntimeException {
    public PlayerDoesNotBelongGameException(long gameId) {
        super(String.format("You don't belong to game %d", gameId));
    }
}
