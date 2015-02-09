import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;


/**
 * Created by 35606 on 1/30/15.
 */
public class RuchitAI implements Connect4AI {
    Color me;
    Color ai;


    public RuchitAI(Color me){
        this.me=me;
    }

    private static final int ROWS = 6, COLS = 7;
       ArrayList<Integer>myTokens;
       ArrayList<Integer>aITokens;

    int a;
    int b;
    int c=a +1;

    @Override
    public int getMove(Color[][] board, Color me) {
        for (int i = 0; i < COLS; i++) {
            if(myTokens.get(i)>aITokens.get(i)) {
                a = i;
            }
            for (int j = 0; j < ROWS; i++) {
                if(myTokens.get(i)>aITokens.get(i)) {
                     b = j;


            }

        }


    }
        return board[b][a+1];
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return "I AM YOUR DOOM";
    }
}
