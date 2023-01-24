package contexts.game.infrastructure;

public class GamePostRequestMother {
    public static GamePostRequest basic() {
        GamePostRequest gamePostRequest = new GamePostRequest();
        gamePostRequest.setPlayer1Id(1L);

        return gamePostRequest;
    }
}
