import javafx.scene.paint.Color;

/**
 * Created by oj on 1/23/15.
 */
public interface Connect4able {
    void dropToken(int col);

    void clear();

    boolean hasWinner();

    /* returns null if no winner */
    Color getWinner();

    boolean isGameOver();

    boolean isDraw();

    Color getCurrentPlayer();
}
