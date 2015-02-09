import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by oj on 1/30/15.
 */
public class MisterAI implements Connect4AI {

    //will return a value 0-6
    @Override
    public int getMove(Color[][] board, Color me) {
        return (int)Math.random() * 7;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return "Mister Jones aka 'Your Leader'";
    }
}
