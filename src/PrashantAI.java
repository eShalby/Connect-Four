import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by 18148 on 1/30/15.
 */
public class PrashantAI implements Connect4AI{

    int col;
    int row;
    int column;

    public PrashantAI(Color yellow) {


    }


    //will return a value 0-6



    @Override
    public int getMove(Color[][] b, Color m) {

        ArrayList<Integer> collum = new ArrayList<Integer>(7);
        for (int i = 0; i < 7; i++) {
            if(b[0][i].equals(Color.WHITE)){
                collum.add(i);
            }

        }
        int randomoves = (int)(Math.random()*collum.size());

        if(checkcolumn(b,m) == 1){
            return row;

        } else if(checkrow(b,m) == 1){
            return column;

        }

        else {
            return collum.get(randomoves);
        }

    }



    private int checkcolumn(Color[][] board, Color me) {
        int k = 0;
        int count = 0;
        int p = 0;
        int rows;

        if(count == 3){

            return 1;


        }
        else{

            for (int j = 0; j < 7; j++) {
                row = j;
                if(board[p][j] == me){
                    count++;
                    p++;



                } else {
                    count = count;


                }


            }


        }
        return 0;
    }

    private int checkrow(Color[][] board, Color me) {
        int k = 0;
        int count = 0;
        int p = 0;
        int rows;

        if(count == 3){

            return 1;


        }
        else{

            for (int j = 0; j < 6; j++) {
                column = j;
                if(board[j][p] == me){
                    count++;
                    p++;



                } else {
                    count = count;


                }


            }


        }
        return 0;
    }

    @Override
    public Image getImage() {

        return null;
    }

    @Override
    public String getName() {

        return "Prashant Krishnan (AmazingWizard18148)";
    }
}