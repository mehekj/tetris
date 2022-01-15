package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Rectangle wrapper class for main graphic element of the game. Makes up the
 * border and all of the Pieces. Stores its location in the form of the int row
 * and column, the values of which can be retrieved and set. The class handles all
 * translation of the logical int values of location into actual graphic values
 * based on the constant square width of the grid.
 */
public class Square {
    private Rectangle _square;
    private int _row;
    private int _col;

    /**
     * Creates a new Rectangle at the row and column on the board and of the color
     * passed into the constructor.
     */
    public Square(int row, int col, Color color) {
        _row = row;
        _col = col;

        _square = new Rectangle(_col * Constants.SQUARE_WIDTH,
                _row * Constants.SQUARE_WIDTH,
                Constants.SQUARE_WIDTH,
                Constants.SQUARE_WIDTH);
        this.setColor(color);
    }

    /**
     * Returns the actual Rectangle.
     */
    public Rectangle getSquare() {
        return _square;
    }

    /**
     * Sets the integer row value. Sets the graphic location based on the row and
     * the square width from Constants.
     */
    public void setRow(int row) {
        _row = row;
        _square.setY(_row * Constants.SQUARE_WIDTH);
    }

    /**
     * Returns the row.
     */
    public int getRow() {
        return _row;
    }

    /** Sets the integer column value. Sets the graphic location based on col
     * and the square width from Constants.
     */
    public void setCol(int col) {
        _col = col;
        _square.setX(_col * Constants.SQUARE_WIDTH);
    }

    /**
     * Returns the column.
     */
    public int getCol() {
        return _col;
    }

    /**
     * Helper method to set the color of the Square. Takes the fill as a parameter
     * but sets the same stroke for all squares.
     */
    private void setColor(Color fill) {
        _square.setFill(fill);
        _square.setStroke(Color.FLORALWHITE);
        _square.setStrokeType(StrokeType.INSIDE);
    }
}
