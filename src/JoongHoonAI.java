import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by oj on 1/30/15.
 */
public class JoongHoonAI implements Connect4AI {

    //will return a value 0-6
    @Override
    public int getMove(Color[][] board, Color me) {
        ArrayList<Integer> playableCols = new ArrayList<Integer>(7);
        for (int col = 0; col < 7; col++) {
            if(board[0][col].equals(Color.WHITE))
                playableCols.add(col);
        }
        int randomMove = (int)(Math.random() * playableCols.size());
        return playableCols.get(randomMove);

    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return "J-J-J-Joongji...?";
    }
}
