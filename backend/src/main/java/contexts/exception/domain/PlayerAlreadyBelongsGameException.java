package contexts.exception.domain;

public class PlayerAlreadyBelongsGameException extends RuntimeException {
    public PlayerAlreadyBelongsGameException(long gameId) {
        super(String.format("You already belongs to game with id %d", gameId));
    }
}
