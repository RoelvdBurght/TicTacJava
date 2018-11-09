package rgburght.gmail.com.tictactoe;

import android.service.quicksettings.Tile;
import android.util.Log;
import android.widget.GridLayout;

import java.io.Serializable;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private TileState[][] board;

    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private Boolean gameOver;

    public Game() {
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = TileState.BLANK;
            }
        }
        playerOneTurn = true;
        gameOver = false;
    }

    public TileState choose(int row, int column) {
        TileState state = board[row][column];

        if (state == TileState.BLANK) {
            if (playerOneTurn) {
                state = TileState.CROSS;
                board[row][column] = state;
                playerOneTurn = false;
            }
            else {
                state = TileState.CIRCLE;
                board[row][column] = state;
                playerOneTurn = true;
            }
        }
        else {
            state = TileState.INVALID;
        }

        return state;
    }

    public GameState won() {
        int rowCountX = 0;
        int colCountX = 0;

        int rowCountO = 0;
        int colCountO = 0;
        // Loopt over hele bord heen en checkt zowel kolomen als rijen voor drie op een rij.
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == TileState.CROSS) {
                    rowCountX ++;
                }
                if (board[i][j] == TileState.CIRCLE) {
                    rowCountO++;
                }
                if (board[j][i] == TileState.CROSS) {
                    colCountX ++;
                }
                if (board[j][i] == TileState.CIRCLE) {
                    colCountO++;
                }
            }

            // Na over een rij geloopt te hebben wordt gecheckt of er 3x eenzelfde symbool stond.
            if (rowCountX == 3 || colCountX == 3) {
                return GameState.PLAYER_ONE;
            }
            rowCountX = 0;
            colCountX = 0;
            if (rowCountO == 3 || colCountO ==3) {
                return GameState.PLAYER_TWO;
            }
            rowCountO = 0;
            colCountO = 0;

        }

        // Check de diagonalen.
        TileState b00 = board[0][0];
        TileState b02 = board[0][2];
        TileState b11 = board[1][1];
        TileState b20 = board[2][0];
        TileState b22 = board[2][2];

        if (b00 == TileState.CROSS && b11 == TileState.CROSS && b22  == TileState.CROSS) {
            return GameState.PLAYER_ONE;
        }
        if (b02 == TileState.CROSS && b11 == TileState.CROSS && b20 == TileState.CROSS) {
            return GameState.PLAYER_ONE;
        }
        if (b00 == TileState.CIRCLE && b11 == TileState.CIRCLE && b22  == TileState.CIRCLE) {
            return GameState.PLAYER_TWO;
        }
        if (b02 == TileState.CIRCLE && b11 == TileState.CIRCLE && b20 == TileState.CIRCLE) {
            return GameState.PLAYER_TWO;
        }

        return GameState.IN_PROGRESS;
    }
}