import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by 28808 on 1/30/15.
 */
public class DanielAI implements  Connect4AI {
    @java.lang.Override
    public int getMove(Color[][] board, Color me) {
        Color colorOfAi = me;
        Color notColorOfAi;
        if(colorOfAi == Color.RED){
            notColorOfAi = Color.YELLOW;
        }
        if(colorOfAi == Color.YELLOW){
            notColorOfAi = Color.RED;
        } else{
            notColorOfAi = Color.YELLOW;
        }

        ArrayList<Integer> playableColumn = new ArrayList<Integer>(7);
        for (int i = 0; i < 7; i++) {
            if(board[0][i].equals(Color.WHITE))
                playableColumn.add(i);
        }
        //THIS PART IS FOR THE AI
        for (int k = 5; k>= 0; k--) {  //Horizontal Search
            for (int i = 0; i < 4; i++) {
                if (board[k][0] == colorOfAi && board[k][1] == colorOfAi && board[k][2] == colorOfAi) {
                    return 3;
                }
                if (board[k][5] == colorOfAi && board[k][4] == colorOfAi && board[k][3] == colorOfAi && board[k][6] != Color.WHITE) {

                    return 2;
                }

                if (board[k][5] == colorOfAi && board[k][4] == colorOfAi && board[k][3] == colorOfAi && board[k][6] == Color.WHITE) {

                    return 6;
                }

                if (board[k][i] == colorOfAi && board[k][i + 1] == colorOfAi && board[k][i + 2] == colorOfAi) {
                    if(i > 0){
                        if(k < 5 ){
                            if(board[k-1][i-1] == Color.WHITE){
                                return (int)(playableColumn.size() * Math.random());
                            }
                        }
                        if(board[k][i-1] == Color.WHITE){
                            return i-1;
                        }
                    }
                    if(i <4){
                        if(board[k][i+3] == Color.WHITE && board[k][i] == colorOfAi && board[k][i+2] == colorOfAi && board[k][i+1] == colorOfAi){
                            if(k < 5 )
                                if(board[k-1][i+3] == Color.WHITE){
                                    return (int)(playableColumn.size() * Math.random());
                                }

                            return i+3;
                        }
                    }
                }
                if (board[k][i] == colorOfAi && board[k][i + 1] == colorOfAi && board[k][i + 3] == colorOfAi) {
                    if(k < 5 ){
                        if(board[k-1][i+2] == Color.WHITE){
                            return (int)(playableColumn.size() * Math.random());
                        }
                    }
                    return i + 2;
                }
                if (board[k][i] == colorOfAi && board[k][i + 2] == colorOfAi && board[k][i + 3] == colorOfAi) {
                    if(k < 5 ){
                        if(board[k-1][i+1] == Color.WHITE){
                            return (int)(playableColumn.size() * Math.random());
                        }
                    }
                    return i + 1;
                }
            }
        }
        for (int i = 0; i < 7; i++) {    // vertical check
            for (int j = 5; j > 2; j--) {
                if(board[j][i] == colorOfAi && board[j-1][i] == colorOfAi && board[j-2][i] == colorOfAi){
                    if(board[0][i] != Color.WHITE){
                        if(i == 0){
                            return (int)(playableColumn.size() * Math.random());
                        }
                        return (int)(playableColumn.size() * Math.random());
                    }
                    return i;
                }
            }
        }
        //diagonal check left to right(placing token on the "end" of diagonal
        for(int i = 0; i < 4; i++){
            for(int j = 5; j > 2; j--){
                if(board[j][i] == colorOfAi && board[j-1][i+1] == colorOfAi && board[j-2][i+2] == colorOfAi && board[j-3][i+3] == Color.WHITE && board[j-2][i+3] != Color.WHITE){

                    return i+3;
                }
            }
        }
        for(int i = 6; i > 2; i--){    //diagonal check right to left(placing token at "end")
            for(int j = 5; j > 2; j--){
                if(board[j][i] == colorOfAi && board[j-1][i-1] == colorOfAi && board[j-2][i-2] == colorOfAi && board[j-3][i-3] == Color.WHITE && board[j-2][i-3] != Color.WHITE){

                    return i-3;
                }
            }
        }
        for(int i = 1; i < 5; i++){    //left to right
            if(board[4][i] == colorOfAi && board[4-1][i+1] == colorOfAi && board[4-2][i+2] == colorOfAi && board[4+1][i-1] == Color.WHITE ){

                return i-1;
            }
        }
        for (int i = 5; i > 2 ; i--) {    //left to right
            if(board[4][i] == colorOfAi && board[3][i-1] == colorOfAi && board[2][i-2] == colorOfAi && board[5][i+1] == Color.WHITE){
                return i+1;
            }
        }
        for(int i = 1; i < 4; i++){      //left to right ignore bottom row
            for(int j = 3; j > 1; j--){
                if(board[j][i] == colorOfAi && board[j-1][i+1] == colorOfAi && board[j-2][i+2] == colorOfAi && board[j+1][i-1] == Color.WHITE && board[j-2][i-1] != Color.WHITE){
                    return i-1;
                }
            }
        }
        for(int i = 5; i > 1; i--){    //diagonal check right to left(placing token at "end")
            for(int j = 3; j > 1; j--){
                if(board[j][i] == colorOfAi && board[j-1][i-1] == colorOfAi && board[j-2][i-2] == colorOfAi && board[j+1][i+1] == Color.WHITE && board[j+2][i+1] != Color.WHITE){

                    return i+3;
                }
            }
        }
        // THIS PART IS FOR CHECKING THREATS

        for (int k = 5; k>= 0; k--) {  //Horizontal Search
            for (int i = 0; i < 4; i++) {
                if (board[k][0] == notColorOfAi && board[k][1] == notColorOfAi && board[k][2] == notColorOfAi) {
                    return 3;
                }
                if (board[k][5] == notColorOfAi && board[k][4] == notColorOfAi && board[k][3] == notColorOfAi && board[k][6] != Color.WHITE) {

                    return 2;
                }

                if (board[k][5] == notColorOfAi && board[k][4] == notColorOfAi && board[k][3] == notColorOfAi && board[k][6] == Color.WHITE) {

                    return 6;
                }

                if(i > 0){
                    if(board[k][i-1] == Color.WHITE && board[k][i] == notColorOfAi && board[k][i+1] == notColorOfAi && board[k][i+2] == notColorOfAi){
                        if(k < 5 ){
                            if(board[k-1][i-1] == Color.WHITE){
                                return (int)(playableColumn.size() * Math.random());
                            }
                        }
                        return i-1;
                    }
                }
                if(i <4){
                    if(board[k][i+3] == Color.WHITE && board[k][i] == notColorOfAi && board[k][i+1] == notColorOfAi && board[k][i+2] == notColorOfAi){

                        if(board[k-1][i+3] == Color.WHITE){
                            return (int)(playableColumn.size() * Math.random());
                        }

                        return i+3;
                    }
                }

                if (board[k][i] == notColorOfAi && board[k][i + 1] == notColorOfAi && board[k][i + 3] == notColorOfAi) {
                    if(k< 5 ){
                        if(board[k-1][i+2] == Color.WHITE){
                            return (int)(playableColumn.size() * Math.random());
                        }
                    }
                    return i + 2;

                }

                if (board[k][i] == notColorOfAi && board[k][i + 2] == notColorOfAi && board[k][i + 3] == notColorOfAi) {
                    if(k < 5 ){
                        if(board[k-1][i+1] == Color.WHITE){
                            return (int)(playableColumn.size() * Math.random());
                        }
                    }
                    return i + 1;

                }
            }
        }
        for (int i = 0; i < 7; i++) {    // vertical check
            for (int j = 5; j > 2; j--) {
                if(board[j][i] == notColorOfAi && board[j-1][i] == notColorOfAi && board[j-2][i] == notColorOfAi){
                    if(board[0][i] != Color.WHITE){
                        if(i == 0){
                            return (int)(playableColumn.size() * Math.random());
                        }
                        return (int)(playableColumn.size() * Math.random());
                    }
                    return i;
                }
            }
        }
        //diagonal check left to right(placing token on the "end" of diagonal
        for(int i = 0; i < 4; i++){
            for(int j = 5; j > 2; j--){
                if(board[j][i] == notColorOfAi && board[j-1][i+1] == notColorOfAi && board[j-2][i+2] == notColorOfAi && board[j-3][i+3] == Color.WHITE && board[j-2][i+3] != Color.WHITE){

                    return i+3;
                }
            }
        }
        for(int i = 6; i > 2; i--){    //diagonal check right to left(placing token at "end")
            for(int j = 5; j > 2; j--){
                if(board[j][i] == notColorOfAi && board[j-1][i-1] == notColorOfAi && board[j-2][i-2] == notColorOfAi && board[j-3][i-3] == Color.WHITE && board[j-2][i-3] != Color.WHITE){

                    return i-3;
                }
            }
        }
        for(int i = 1; i < 5; i++){    //left to right
            if(board[4][i] == notColorOfAi && board[4-1][i+1] == notColorOfAi && board[4-2][i+2] == notColorOfAi && board[4+1][i-1] == Color.WHITE ){

                return i-1;
            }
        }
        for (int i = 5; i > 2 ; i--) {    //left to right
            if(board[4][i] == notColorOfAi && board[3][i-1] == notColorOfAi && board[2][i-2] == notColorOfAi && board[5][i+1] == Color.WHITE){

                return i+1;
            }
        }
        for(int i = 1; i < 4; i++){      //left to right ignore bottom row
            for(int j = 3; j > 1; j--){
                if(board[j][i] == notColorOfAi && board[j-1][i+1] == notColorOfAi && board[j-2][i+2] == notColorOfAi && board[j+1][i-1] == Color.WHITE && board[j-2][i-1] != Color.WHITE){

                    return i-1;
                }
            }
        }
        for(int i = 5; i > 1; i--){    //diagonal check right to left(placing token at "end")
            for(int j = 3; j > 1; j--){
                if(board[j][i] == notColorOfAi && board[j-1][i-1] == notColorOfAi && board[j-2][i-2] == notColorOfAi && board[j+1][i+1] == Color.WHITE && board[j+2][i+1] != Color.WHITE){

                    return i+3;
                }
            }
        }


        return (int)(playableColumn.size() * Math.random());
    }





    @java.lang.Override
    public Image getImage() {
        return null;
    }

    @java.lang.Override
    public String getName() {
        return null;
    }
}
