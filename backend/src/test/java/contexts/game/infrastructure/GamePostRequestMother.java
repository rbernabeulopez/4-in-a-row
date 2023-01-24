package contexts.game.infrastructure;

public class GamePostRequestMother {
    public static GamePostRequest basicWithPlayerId(long playerId) {
        GamePostRequest gamePostRequest = new GamePostRequest();
        gamePostRequest.setPlayer1Id(playerId);

        return gamePostRequest;
    }
}
