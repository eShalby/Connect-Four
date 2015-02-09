import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by 29917 on 2/8/15.
 */
public class WilsonAI implements Connect4AI {
    private int[][] boardInts = new int[ROWS][COLS];
    private int[]   totalInts = new int[ROWS];
    private int     finalCol  = -1;
    public static final int ROWS = 6, COLS = 7;


    private Circle[][] gameBoard;
    private Color currentPlayer;
    private Connect4AI redUI, yellowUI;
    private Rectangle highlightedColumn;

    private int [][] bestMove = {
            {-1,-1}
    };

    private void setup() {
        gameBoard = new Circle[ROWS][COLS];
        currentPlayer = Color.RED;

    }

    public boolean makeMove(int c) {
        if(c < 0 || c > 6) {
            System.out.println("This is an invalid move.");
            return false;
        } else {
            for(int i = 5;i >= 0;i--) {
                if(boardInts[i][c] == 0) {
                    boardInts[i][c] = c + 1;
                    return true;
                }
            }
            return false;
        }
    }

    public boolean validMove(int c) {
        if(c < 0 || c > 6) {
            System.out.println("This is an invalid move");
            return false;
        } else {
            for(int i = 5;i >= 0;i--) {
                if(boardInts[i][c] == 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public int findRowInMove(int c) {
        if(c < 0 || c > 6) {
            System.out.println("This is an invalid move.");
            return -1;
        } else {
            for(int i = 5;i >= 0;i--) {
                if(boardInts[i][c] == 0) {
                    return i;
                }
            }
            return -1;
        }
    }

  /*  public boolean validMove(int c) {
        for (int i = 0; i < COLS; i++) {
            if (gameBoard[c][i].getFill().equals(Color.WHITE))
                return true;
        }
        return false;
    }*/

    public int [][] findAllLegalMoves() {
        int [][] legalMove = {
                {-1,-1},
                {-1,-1},
                {-1,-1},
                {-1,-1},
                {-1,-1},
                {-1,-1},
                {-1,-1}
        };

        for(int c = 0;c < 7;c++) {
            for(int r = 5;r >= 0;r--) {
                if(boardInts[r][c] == 0) {
                    legalMove[c][0] = r;
                    legalMove[c][1] = c;
                    break;
                }
            }
        }
        return legalMove;
    }


    public int getMove(Color[][] board , Color me) {
        /*int[] move = new int[] {19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
        for (int i = 20; i > 0; i++) {
            if (move[i] == 19) {return 1;}
            if (move[i] == 18) {return 3;}
            if (move[i] == 17) {return 5;}
            if (move[i] == 16) {return 0;}
            if (move[i] == 15) {return 2;}
            if (move[i] == 14) {return 4;}
            if (move[i] == 13) {return 6;}
            if (move[i] == 12) {return 1;}
            if (move[i] == 11) {return 3;}
            if (move[i] == 10) {return 5;}
            return 0;
        }*/
        ArrayList<Integer> playableCols = new ArrayList<Integer>(7);
        for (int cols = 0; cols < 7; cols++) {
            if (board[0][cols].equals(Color.WHITE))
                playableCols.add(cols);

        }

        int colMove = (int)(Math.random() * playableCols.size());
        return playableCols.get(colMove);
    }

    public int checkWinner(Color me) {
        //check horizontal
        for (int r = 0; r < ROWS; r++) {
            int run = 0;
            for (int c = 0; c < COLS-1; c++) {
                if ((gameBoard[r][c].getFill()).equals(me)) {
                    run++;

                    if (run == 3)
                        return c +1;
                } else {
                    run = 0;
                }
            }
        }

        //check vertical
        for (int c = 0; c < COLS-1; c++) {
            int run = 0;
            for (int r = 0; r < ROWS; r++) {
                if ((gameBoard[r][c].getFill()).equals(me)) {
                    run++;

                    if (run == 3)
                        return c+1;
                } else {
                    run = 0;
                }
            }
        }

        //check diagonal1
        int r = 3, c = 0;

        while (c < COLS - 3) {
            int i = 0;
            int run = 0;

            while (r - i >= 0 && c + i < COLS) {
                if ((gameBoard[r - i][c + i].getFill()).equals(me)) {
                    run++;

                    if (run == 3)
                        return c;
                } else {
                    run = 0;
                }
                i++;
            }

            if (r < ROWS - 1) {
                r++;
            } else {
                c++;
            }

        }

        //check diagonal2
        r = 3;
        c = COLS - 1;

        while (c > 2) {
            int i = 0;
            int run = 0;

            while (r - i >= 0 && c - i >= 0) {
                if ((gameBoard[r - i][c - i].getFill()).equals(me)) {
                    run++;

                    if (run == 3)
                        return c-1;
                } else {
                    run = 0;
                }
                i++;
            }

            if (r < ROWS - 1) {
                r++;
            } else {
                c--;
            }
        }
        return (int)(Math.random() * 7);
    }

    public Image getImage() {
        return null;
    }

    public String getName() {
        return "WinAllDay";
    }
}
