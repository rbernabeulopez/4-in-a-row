package contexts.player.infrastructure.controller;

public class PlayerPostRequestMother {
    public static PlayerPostRequest basic() {
        PlayerPostRequest playerPostRequest = new PlayerPostRequest();
        playerPostRequest.setName("name");
        playerPostRequest.setPassword("password");
        return playerPostRequest;
    }
}
