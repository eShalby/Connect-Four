import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by oj on 1/30/15.
 */
public interface Connect4AI {
    int getMove(Color[][] board);

    Image getImage();

    String getName();
}
