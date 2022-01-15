package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Pieces that the user controls to play the game. Can be moved left, right, and
 * down and rotate according to keyboard input. Moves down according to the timeline.
 * Based on parameters passed in from Tetris, randomly generates 1 of 7 types
 * of pieces. Pieces are added graphically upon instantiation but are added
 * logically to the board array when they fall as far down as possible and lock
 * into place so other pieces can then detect them.
 */

public class Piece {
    private Pane _gamePane;
    private Square[][] _board;
    private Square[] _pieceSquares;
    private boolean isSquare;

    /**
     * Takes the game pane as a parameter to add Squares to and takes the board
     * array so the Squares can be added when the piece is locked. Takes a set
     * of coordinates and a color randomly passed in from Tetris for the 7
     * configurations of pieces. Stores the Square components of the Piece in an
     * array and checks whether the piece is a square piece.
     */
    public Piece(Pane gamePane, Square[][] board, int[][] coords, Color color) {
        _gamePane = gamePane;
        _board = board;

        _pieceSquares = new Square[coords.length];
        this.makeSquares(coords, color);

        if (coords.equals(Constants.O_PIECE_COORDS)) {
            isSquare = true;
        }
        else {
            isSquare = false;
        }
    }

    /**
     * Instantiates the Squares for the Piece based on the coordinates passed in
     * for the configuration. Adds the Squares to the component array and to the
     * game pane.
     */
    private void makeSquares(int[][] coords, Color color) {
        for (int i = 0; i < _pieceSquares.length; i++) {
            int row = coords[i][1] + Constants.PIECE_ORIGIN_Y;
            int col = coords[i][0] + Constants.PIECE_ORIGIN_X;
            _pieceSquares[i] = new Square(row, col, color);
            _gamePane.getChildren().add(_pieceSquares[i].getSquare());
        }
    }

    /**
     * Shifts the Piece down by one row.
     * Calculates the new location of the Piece's squares and checks if the move
     * is valid. If it is, moves all the squares down, if not locks the piece into
     * place on the board. Returns whether or not the piece was successfully moved.
     */
    public boolean moveY() {
        int[][] newCoords = new int[_pieceSquares.length][2];

        for (int i = 0; i < _pieceSquares.length; i++) {
            newCoords[i][0] = _pieceSquares[i].getCol();
            newCoords[i][1] = _pieceSquares[i].getRow() + 1;
        }

        if (moveIsValid(newCoords)) {
            this.setNewCoords(newCoords);
            return true;
        }
        else {
            this.lockPiece();
            return false;
        }
    }

    /**
     * Shifts the Piece left or right by one column.
     * Calculates the new location, checks if the move is valid, and then moves
     * accordingly. Takes the direction as a parameter (-1 for left and 1 for right).
     */
    public void moveX(int direction) {
        int[][] newCoords = new int[_pieceSquares.length][2];

        for (int i = 0; i < _pieceSquares.length; i++) {
            newCoords[i][0] = _pieceSquares[i].getCol() + direction;
            newCoords[i][1] = _pieceSquares[i].getRow();
        }

        if (moveIsValid(newCoords)) {
            this.setNewCoords(newCoords);
        }
     }

    /**
     * Rotates the piece by 90 degrees counter clockwise.
     * Calculates the new location, checks if the move is valid, and then moves
     * accordingly. Does nothing if the piece is a square.
     */
    public void rotate() {
        if (!isSquare) {
            int rotateCenterX = _pieceSquares[1].getCol();
            int rotateCenterY = _pieceSquares[1].getRow();

            int[][] newCoords = new int[_pieceSquares.length][2];

            for (int i = 0; i < _pieceSquares.length; i++) {
                int currCol = _pieceSquares[i].getCol();
                int currRow = _pieceSquares[i].getRow();

                newCoords[i][0] = rotateCenterX - rotateCenterY + currRow;
                newCoords[i][1] = rotateCenterY + rotateCenterX - currCol;
            }

            if (moveIsValid(newCoords)) {
                this.setNewCoords(newCoords);
            }
        }
    }

    /**
     * Returns whether a move is valid. Takes an array of the new coordinates
     * of the Piece's Squares as a parameter and checks that the board does not
     * already have any Squares stored in any of the new locations.
     */
    private boolean moveIsValid(int[][] newCoords) {
        for (int i = 0; i < newCoords.length; i++) {
            if (_board[ newCoords[i][1] ][ newCoords[i][0] ] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Called when a move is checked and found to be successful. Takes the new
     * Square coordinates as a parameter and sets each Square in the square components
     * of the Piece to its new location.
     */
    private void setNewCoords(int[][] newCoords) {
        for (int i = 0; i < _pieceSquares.length; i++) {
            _pieceSquares[i].setCol(newCoords[i][0]);
            _pieceSquares[i].setRow(newCoords[i][1]);
        }
    }

    /**
     * Called when the Piece has moved as far down as it possibly can. Adds the
     * Piece's Squares logically to the board so that future pieces can detect it
     * and collide with it.
     */
    private void lockPiece() {
        for (Square square: _pieceSquares) {
            if (square != null) {
                _board[square.getRow()][square.getCol()] = square;
            }
        }
    }
}
