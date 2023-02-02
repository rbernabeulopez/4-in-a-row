package contexts.game.domain.entity;

import contexts.exception.domain.GameIsFullException;
import contexts.exception.domain.PlayerAlreadyBelongsGameException;
import contexts.exception.domain.PlayerDoesNotBelongGameException;
import contexts.movement.domain.entity.Movement;
import contexts.player.domain.entities.Player;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.Pair;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private long id;

    @ManyToMany(mappedBy = "games",fetch = FetchType.EAGER)
    private List<Player> players;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player winner;

    @OneToMany(mappedBy = "game",fetch = FetchType.EAGER)
    private List<Movement> movements;

    private boolean finished;

    private static final int MAX_PLAYERS = 2;

    public void joinPlayer(Player player) {
        if (players.size() == MAX_PLAYERS) {
            throw new GameIsFullException(this.id);
        }
        if (players.contains(player)) {
            throw new PlayerAlreadyBelongsGameException(this.id);
        }
        players.add(player);
        List<Game> games = player.getGames();
        games.add(this);
        player.setGames(games);
    }

    public void checkPlayerBelongs(Player player) {
        if(!players.contains(player)) {
            throw new PlayerDoesNotBelongGameException(this.id);
        }
    }

    public Player getStartingPlayer() {
        return (players.size() == MAX_PLAYERS) ? players.get(0) : null;
    }

    public Player checkWinner() {
        if (this.movements.size() == 42) {
            return new Player();
        }

        Player[][] board = new Player[6][7];
        for (Movement movement : this.movements) {
            board[movement.getRow()][movement.getCol()] = movement.getPlayer();
        }

        // Verificar filas
        // i = fila = 5
        // j = columna = 0
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != null &&
                    board[i][j] == board[i][j + 1] &&
                    board[i][j] == board[i][j + 2] &&
                    board[i][j] == board[i][j + 3]) {
                    return board[i][j];
                }
            }
        }

        // Verificar columnas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] != null &&
                    board[i][j] == board[i + 1][j] &&
                    board[i][j] == board[i + 2][j] &&
                    board[i][j] == board[i + 3][j]) {
                    return board[i][j];
                }
            }
        }

        // Verificar diagonales hacia la derecha
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != null &&
                    board[i][j] == board[i + 1][j + 1] &&
                    board[i][j] == board[i + 2][j + 2] &&
                    board[i][j] == board[i + 3][j + 3]) {
                    return board[i][j];
                }
            }
        }

        // Verificar diagonales hacia la izquierda
        for (int i = 0; i < 3; i++) {
            for (int j = 6; j >= 3; j--) {
                if (board[i][j] != null &&
                    board[i][j] == board[i + 1][j - 1] &&
                    board[i][j] == board[i + 2][j - 2] &&
                    board[i][j] == board[i + 3][j - 3]) {
                    return board[i][j];
                }
            }
        }
        return null;
    }
}
