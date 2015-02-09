import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by 37181 on 1/30/15.
 */
public class ErfannAI implements Connect4AI {
    Color me;

    public ErfannAI(Color me) {

        this.me = me;
    }


    //will return a value 0 - 6
    @Override
    public int getMove(Color[][] board, Color me) {
        int run = 0;


        //check vertical
        for (int c = 0; c < board[0].length; c++) {
            run = 0;
            for (int r = 0; r < board.length; r++) {
                if ((board[r][c].equals(me))) {
                    run++;

                    if (run == 3) {
                        return c;
                    }


                }
            }
        }

        //check horizontal
        for (int r = 0; r < board.length; r++) {
            run = 0;
            for (int c = 0; c < board[0].length; c++) {
                if ((board[r][c].equals(me))) {
                    run++;

                    if (run == 3) {
                        //all 4 corners (top right, bottom left and etc)
                        if (r == 5 && c == 2 && board[r][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 5 && c == 6 && board[r][c - 3].equals(0)) {
                            return c - 3;
                        } else if (r == 0 && c == 2 && board[r][c + 1].equals(0) && !board[r + 1][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 0 && c == 6 && board[r][c - 3].equals(0) && !board[r + 1][c - 3].equals(0)) {
                            return c - 3;
                        }

                    }

                    //check bottom,top,left,right and normal
                    if (run == 3){
                        if (r == 0 && board[r][c + 1].equals(0) && !board[r + 1][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 0 && board[r][c - 3].equals(0) && !board[r + 1][c - 3].equals(0)) {
                            return c - 3;
                        } else if ((r < 5 && r != 0) && !board[r + 1][c + 1].equals(0)) {
                            return c + 1;
                        } else if ((r < 5 && r != 0) && !board[r + 1][c - 3].equals(0)) {
                            return c - 3;
                        } else if (r == 5 && board[r][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 5 && board[r][c - 3].equals(0)) {
                            return c + 1;
                        }
                    }

                    if (run == 0) {
                        return (int) (Math.random() * 7);
                    }
                }
            }

        }

        //return 2;
        if (run == 0)
            return (int) (Math.random() * 7);

        return 0;

    }

    public int getDangerous(Color board[][], Color me){
        int run = 0;

        //dangerous vertical
        for (int c = 0; c < board[0].length; c++) {
            run = 0;
            for (int r = 0; r < board.length; r++) {
                if ((!board[r][c].equals(me))) {
                    run++;

                    if (run == 3) {
                        return c;
                    }


                }
            }
        }

        //dangerous horizontal
        for (int r = 0; r < board.length; r++) {
            run = 0;
            for (int c = 0; c < board[0].length; c++) {
                if ((!board[r][c].equals(me))) {
                    run++;

                    if (run == 3) {
                        //all 4 corners (top right, bottom left and etc)
                        if (r == 5 && c == 2 && board[r][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 5 && c == 6 && board[r][c - 3].equals(0)) {
                            return c - 3;
                        } else if (r == 0 && c == 2 && board[r][c + 1].equals(0) && !board[r + 1][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 0 && c == 6 && board[r][c - 3].equals(0) && !board[r + 1][c - 3].equals(0)) {
                            return c - 3;
                        }

                    }

                    //check bottom,top,left,right and normal
                    if (run == 3){
                        if (r == 0 && board[r][c + 1].equals(0) && !board[r + 1][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 0 && board[r][c - 3].equals(0) && !board[r + 1][c - 3].equals(0)) {
                            return c - 3;
                        } else if ((r < 5 && r != 0) && !board[r + 1][c + 1].equals(0)) {
                            return c + 1;
                        } else if ((r < 5 && r != 0) && !board[r + 1][c - 3].equals(0)) {
                            return c - 3;
                        } else if (r == 5 && board[r][c + 1].equals(0)) {
                            return c + 1;
                        } else if (r == 5 && board[r][c - 3].equals(0)) {
                            return c + 1;
                        }
                    }

                    if (run == 0) {
                        return (int) (Math.random() * 7);
                    }
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
        return "Erfann Daniel";
    }
}
