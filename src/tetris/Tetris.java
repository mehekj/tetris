package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


/**
 * Main class that handles the graphic elements and game logic. Creates random pieces
 * that move down the 2D board according to the timeline and can also be manipulated
 * with keyboard input. Checks if rows are full and clears them. Ends the game when
 * a piece hits the top.
 */
public class Tetris {
    private Pane _gamePane;
    private Square[][] _board;
    private Piece _currPiece;
    private boolean _paused;
    private Timeline _timeline;
    private Label _pauseText;

    /**
     * Sets initial state of game to unpaused, sets up KeyHandler, creates 2D array
     * for the logical representation of the game board, and generates the first piece.
     * Takes the game pane as a parameter to add all the Nodes to.
     */
    public Tetris(Pane gamePane) {
        _paused = false;
        _pauseText = new Label("Game is paused. Press P to resume.");

        _gamePane = gamePane;
        _gamePane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyHandler());
        _gamePane.setFocusTraversable(true);

        _board = new Square[Constants.BOARD_ROWS + 2 * Constants.BORDER][Constants.BOARD_COLS + 2 * Constants.BORDER];
        this.setBoard();

        _currPiece = this.generatePiece();

        this.setupTimeline();
    }

    /**
     * Creates Keyframe and Timeline.
     */
    private void setupTimeline() {
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.DURATION), new TimeHandler());
        _timeline = new Timeline(kf);
        _timeline.setCycleCount(Animation.INDEFINITE);
        _timeline.play();
    }

    /**
     * Sets up the initial state of the board with the playable space being empty
     * and the border of Squares along the sides.
     */
    private void setBoard() {
        for (int i = 0; i < _board.length; i++) {
            for (int j = 0; j < _board[i].length; j++) {
                if (i < Constants.BORDER || i >= Constants.BOARD_ROWS + Constants.BORDER || j < Constants.BORDER || j >= Constants.BOARD_COLS + Constants.BORDER) {
                    _board[i][j] = new Square(i, j, Color.BURLYWOOD);
                    _gamePane.getChildren().add(_board[i][j].getSquare());
                }
            }
        }
    }

    /**
     * Returns a randomly generated Piece of the 7 standard Tetris piece configurations.
     */
    private Piece generatePiece() {
        int randomNum = (int) (Math.random() * 7);
        int[][] coords;
        Color color;

        switch (randomNum) {
            case 0:
                coords = Constants.I_PIECE_COORDS;
                color = Color.TOMATO;
                break;
            case 1:
                coords = Constants.T_PIECE_COORDS;
                color = Color.LIGHTSALMON;
                break;
            case 2:
                coords = Constants.J_PIECE_COORDS;
                color = Color.GOLD;
                break;
            case 3:
                coords = Constants.L_PIECE_COORDS;
                color = Color.YELLOWGREEN;
                break;
            case 4:
                coords = Constants.O_PIECE_COORDS;
                color = Color.SKYBLUE;
                break;
            case 5:
                coords = Constants.S_PIECE_COORDS;
                color = Color.ORCHID;
                break;
            default:
                coords = Constants.Z_PIECE_COORDS;
                color = Color.PINK;
                break;
        }

        return new Piece(_gamePane, _board, coords, color);
    }

    /**
     * Checks if there are any full rows in the board and graphically removes
     * the Squares in any full row then moves all above rows down.
     */
    public void clearLines() {
        for (int i = Constants.BORDER; i < _board.length - Constants.BORDER; i++) {
            if (rowIsFull(i)) {
                for (int j = Constants.BORDER; j < _board[i].length - Constants.BORDER; j++) {
                    _gamePane.getChildren().remove(_board[i][j].getSquare());
                }
                this.dropRowsAbove(i);
            }
        }
    }

    /**
     * Checks every Square in the parameter row and returns whether or not the row
     * is full.
     */
    private boolean rowIsFull(int row) {
        for (int i = Constants.BORDER; i < _board[row].length - Constants.BORDER; i++) {
            if (_board[row][i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Takes the most recently cleared row as a parameter. Goes through every row
     * from the row above it to the top and shift it doesn one row.
     */
    private void dropRowsAbove(int row) {
        for (int i = row; i > Constants.BORDER; i--) {
            for (int j = Constants.BORDER; j < _board[i].length - Constants.BORDER; j++) {
                Square fallingSquare = _board[i - 1][j];
                if (fallingSquare != null) {
                    fallingSquare.setRow(fallingSquare.getRow() + 1);
                }

                _board[i][j] = fallingSquare;
            }
        }
    }

    /**
     * Checks if a piece has hit the top of the board and if so ends the game.
     * Stops the timeline, disables keyboard input, and displays a game over message.
     */
    private void checkGameOver() {
        for (int i = Constants.BORDER; i < _board[Constants.BORDER].length - Constants.BORDER; i++) {
            if (_board[Constants.BORDER][i] != null) {
                _timeline.stop();
                _gamePane.setOnKeyPressed(null);

                Label gameOver = new Label("Game Over");
                gameOver.setTranslateX(Constants.GAME_OVER_X);
                gameOver.setTranslateY(Constants.SCENE_HEIGHT / 2.0);
                gameOver.setFont(new Font(Constants.GAME_OVER_FONT));
                _gamePane.getChildren().add(gameOver);
            }
        }

    }

    /**
     * Pauses and unpauses the game by stopping the timeline and changing the
     * state of _paused which enables or disables keyboard input. Also displays
     * and hides a pause message.
     */
    private void togglePause() {
        if (!_paused) {
            _timeline.pause();
            _gamePane.getChildren().add(_pauseText);
        }
        else {
            _timeline.play();
            _gamePane.getChildren().remove(_pauseText);
        }
        _paused = !_paused;
    }

    /**
     * On every Timeline tick checks if the game is over. If not it continues and
     * moves the current Piece in play down by one row. If the piece can no longer
     * move it generates a new piece. It checks for any full rows that need to
     * be cleared.
     */
    private class TimeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            checkGameOver();

            if (!_currPiece.moveY()) {
                _currPiece = generatePiece();
            }

            clearLines();
        }
    }

    /**
     * Called on keypress. Moves the current Piece left, right, or down one square
     * according to the respective key. Rotates 90 degrees counter clockwise
     * when UP is pressed. Drops the piece to the bottom when SPACE is pressed.
     * Pauses and unpauses the game when P is pressed. When the game is paused
     * no other responses to keyboard input are enabled.
     */
    private class KeyHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (!_paused) {
                if (event.getCode() == KeyCode.LEFT) {
                    _currPiece.moveX(-1);
                }
                else if (event.getCode() == KeyCode.RIGHT) {
                    _currPiece.moveX(1);
                }
                else if (event.getCode() == KeyCode.DOWN) {
                    _currPiece.moveY();
                }
                else if (event.getCode() == KeyCode.SPACE) {
                    // moves _currPiece down by one row at a time until it no longer can
                    while (_currPiece.moveY());
                }
                else if (event.getCode() == KeyCode.UP) {
                    _currPiece.rotate();
                }
            }

            if (event.getCode() == KeyCode.P) {
                togglePause();
            }

            event.consume();
        }
    }
}
