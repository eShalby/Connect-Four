import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by 39470 on 2/4/15.
 */
public class ZhenAI implements Connect4AItest {

    private boolean firstMove = true;
    private int count,posX;





    @Override
    public int getMove(Color[][] board, Color me) {
        if (firstMove){
            if (board[5][3].equals(Color.WHITE)){
                firstMove = false;
                return 3; }
            else if((int)(Math.random()*3)>1){
                return 0;
            }
            else return 6;
        }
        else if(opRow(board)){

            return posX;
        }
        else if (opColumn(board)){

            return posX;
        }

        else if (opdiagonal(board)) {

            return posX;



        }


            ArrayList<String> temp = new ArrayList<String >(7);
        for (int i = 0; i < 7; i++) {
            if (board[5][i].equals(Color.WHITE)){
                temp.add(i+"");
            }

        }
        int a =   (int)(Math.random() * temp.size());
        return Integer.parseInt(temp.get(a));


    }






    private boolean opdiagonal(Color[][] board) {
        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(Color.YELLOW)) {
                    count+=1;
                    return false;
                }

    } }
    return false;}

    private boolean opRow(Color[][] board) {
        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].equals(Color.YELLOW)) {
                    count+=1;
                    for (int k = 1; k < 3; k++) {
                        if (board[i][j+k].equals(Color.YELLOW)){
                            count = count+1;
                        }
                        else {
                            j=j+k;
                            break;
                        }


                    }

                    if (count==3){
                        if (i==0){

                            if (board[i][j+3].equals(Color.WHITE)){
                                posX = j+3;

                                return true;

                            }
                            else if (j!=0) {
                                if(board[i][j-1].equals(Color.WHITE)) {
                                    posX = j-1;

                                    return true;
                                }
                            }
                            else count = 0;

                        }
                        else {
                            if (board[i][j+3].equals(Color.WHITE)&&!board[i+1][j+3].equals(Color.WHITE)){
                                posX = j+3;
                                return true;
                            }
                            else if(j!=0){
                                if (board[i][j-1].equals(Color.WHITE)&&!board[i+1][j-1].equals(Color.WHITE)){
                                    posX = j-1;
                                    return true;
                                }

                            }
                            else count = 0;

                        }

                    }
                    else{ count = 0;}

                } }

            if (board[i][3].equals(Color.WHITE)) {
                for (int k = 0; k < 3; k++) {
                    if (board[i][4+k].equals(Color.YELLOW)){
                        count = count+1;
                    }
                    else {
                        i=i+k;
                        break;
                    }


                }

                if (count==3){
                    if(i==0){
                        posX = 3;
                        return true;
                    }
                    else if (!board[i+1][4].equals(Color.WHITE)&&board[i][4].equals(Color.WHITE)) {
                        posX = 3;

                        return true;  }
                    else count = 0;
                }
                else count = 0;


            }






        }
        return false;
    }

    private boolean opColumn(Color[][] board) {
        for (int j = 0; j < 7; j++) {
            for (int i = 5; i > 3; i--) {
                if (board[i][j].equals(Color.YELLOW)) {
                    count=1;
                    for (int k = 1; k < 3; k++) {
                        if (board[i-k][j].equals(Color.YELLOW)){
                            count = count+1;
                        }
                        else {
                            i=i-k;
                            break;
                        }


                    }

                    if (count==3){


                        if (board[i-3][j].equals(Color.WHITE)){
                            posX = j;

                            return true;

                        }
                        else count = 0;

                    }

                }
                else{ count = 0;}

            } }







        return false;}


    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
