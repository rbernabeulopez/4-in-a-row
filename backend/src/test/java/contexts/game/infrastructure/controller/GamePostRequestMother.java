package contexts.game.infrastructure.controller;

public class GamePostRequestMother {
    public static GamePostRequest basicWithPlayerId(long playerId) {
        GamePostRequest gamePostRequest = new GamePostRequest();
        gamePostRequest.setPlayer1Id(playerId);

        return gamePostRequest;
    }
}
