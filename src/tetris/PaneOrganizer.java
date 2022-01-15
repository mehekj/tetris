package tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Creates all of the panes involved in the program: the root pane which contains
 * the pane that has the actual Tetris game and the pane on the bottom for the
 * quit button.
 */
public class PaneOrganizer {
    BorderPane _root;
    Pane _gamePane;
    HBox _bottomPane;

    /**
     * Instantiates all the panes and adds the child panes to the root. Styles
     * the game pane and instantiates the Tetris game.
     */
    public PaneOrganizer() {
        _root = new BorderPane();
        _gamePane = new Pane();
        _bottomPane = new HBox();

        BackgroundFill fill = new BackgroundFill(Color.FLORALWHITE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        _gamePane.setBackground(background);

        this.setUpButtonPane();

        _root.setTop(_gamePane);
        _root.setBottom(_bottomPane);

        new Tetris(_gamePane);
    }

    /**
     * Styles the bottom pane and creates the quit button.
     */
    private void setUpButtonPane() {
        BackgroundFill fill = new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        _bottomPane.setBackground(background);

        _bottomPane.setAlignment(Pos.CENTER);

        Button quitButton = new Button("Quit");

        _bottomPane.getChildren().add(quitButton);

        _bottomPane.setFocusTraversable(true);
        quitButton.setOnAction(new QuitHandler());
    }

    /**
     * Returns the root pane.
     */
    public BorderPane getRoot() {
        return _root;
    }

    /**
     * Called when the quit button is pressed. Ends the program.
     */
    private class QuitHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
    }
}
