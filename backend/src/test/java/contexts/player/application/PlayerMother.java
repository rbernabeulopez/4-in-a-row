package contexts.player.application;

import contexts.player.domain.entities.Player;

import java.util.ArrayList;

public class PlayerMother {
    public static Player basicWithoutId() {
        return Player.builder()
                .name("Player 1")
                .password("12345678")
                .ipAddress("192.168.30.24")
                .build();
    }


    public static Player basicWithId(Long id) {
        return Player.builder()
            .id(id)
            .name("Player 1")
            .password("12345678")
            .ipAddress("192.168.30.24")
            .games(new ArrayList<>())
            .build();
    }

    public static Player basicWithNullName() {
        return Player.builder()
            .id(0L)
            .name(null)
            .password("12345678")
            .ipAddress("192.168.30.24")
            .build();
    }

    public static Player basicWithShortPassword() {
        return Player.builder()
                .id(0L)
                .name("Player 1")
                .password("12")
                .ipAddress("192.168.30.24")
                .build();
    }

    public static Player basicWithLongPassword() {
        return Player.builder()
            .id(0L)
            .name("Player 1")
            .password("123456789012345678901")
            .ipAddress("192.168.30.24")
            .build();
    }

    public static Player basicWithInvalidIpAddress() {
        return Player.builder()
            .id(0L)
            .name("Player 1")
            .password("12345678")
            .ipAddress("101.5. 40.1")
            .build();
    }
}
