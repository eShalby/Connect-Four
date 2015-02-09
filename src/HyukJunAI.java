import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Created by 35116 on 2/8/15.
 */
public class HyukJunAI implements Connect4AI {




    private boolean firstMove;
    private int[][] boardInts = new int[ROWS][COLS];
    private int[]   totalInts = new int[ROWS];
    private int     finalCol  = -1;

    private int[]   moves = new int[100];    public static final int ROWS = 6, COLS = 7, TOKEN_RADIUS = 40, TOKEN_SPACING = 10, AI_TURN_MS = 500;

    private Circle[][] gameBoard;
    private Color currentPlayer;
    private Connect4AI redUI, yellowUI;
    private Rectangle highlightedColumn;
    private MediaPlayer dropSound;






    public boolean isnotfull(int c, Color board[][]) {


        for (int i = 0; i < COLS; i++) {
            if(board[0][COLS].equals(Color.WHITE))

                    return true;
        }
        return false;
    }


    private void setup() {
        dropSound = new MediaPlayer(new Media(Connect4.class.getResource("drop.mp3").toString()));
        gameBoard = new Circle[ROWS][COLS];
        currentPlayer = Color.RED;

    }


    private Color[][] duplicate() {
        Color[][] a = new Color[gameBoard.length][gameBoard[0].length];

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                a[i][j] = (Color) gameBoard[i][j].getFill();
            }
        }

        return a;
    }

    private ArrayList<Integer> getPosition(Color[][] board, Color color) {
        ArrayList<Integer> positions = new ArrayList<Integer>();

        for (int run = 3; run > 0; run--) {

            for (int rows = 0; rows < ROWS; rows++) {
                for (int cols = 0; cols < COLS; cols++) {
                    if (board[rows][cols].equals(color)) {

                        Integer right       = getOpenDirection(2, color, board, rows, cols + 1);
                        Integer left        = getOpenDirection(6, color, board, rows, cols - 1);

                        Integer up          = getOpenDirection(0, color, board, rows - 1, cols);
                        Integer down        = getOpenDirection(4, color, board, rows + 1, cols);

                        Integer leftUp      = getOpenDirection(7, color, board, rows - 1, cols - 1);
                        Integer leftDown    = getOpenDirection(5, color, board, rows - 1, cols + 1);

                        Integer rightUp     = getOpenDirection(1, color, board, rows + 1, cols - 1);
                        Integer rightDown   = getOpenDirection(3, color, board, rows + 1, cols + 1);

                        if (right != null || left != null) {
                            int horizontal = 1 + getTokenRun(2, color, board, rows, cols + 1) + getTokenRun(6, color, board, rows, cols - 1);
                        }

                        if (up != null || down != null) {
                            int vertical = 1 + getTokenRun(2, color, board, rows, cols + 1) + getTokenRun(6, color, board, rows, cols - 1);
                        }

                        if (leftUp != null || leftDown != null) {
                            int diagonalLeft = 1 + getTokenRun(2, color, board, rows, cols + 1) + getTokenRun(6, color, board, rows, cols - 1);
                        }

                        if (rightUp != null || rightDown != null) {
                            int diagonalRight = 1 + getTokenRun(2, color, board, rows, cols + 1) + getTokenRun(6, color, board, rows, cols - 1);
                        }

                    }
                }
            }

            if (positions.size() > 1)
                break;
        }

        return positions;
    }

    private Integer getOpenDirection(int direction, Color tokenColor, Color[][] board, int r, int c){

        if (r >= ROWS || c >= COLS || r < 0 || c < 0)
            return null;

        int newR = 0 , newC = 0;

        if(direction == 0) { newR = r - 1; newC = c    ; }
        if(direction == 1) { newR = r - 1; newC = c + 1; }
        if(direction == 2) { newR = r + 1; newC = c    ; }
        if(direction == 3) { newR = r    ; newC = c + 1; }
        if(direction == 4) { newR = r    ; newC = c + 1; }
        if(direction == 5) { newR = r - 1; newC = c + 1; }
        if(direction == 6) { newR = r - 1; newC = c    ; }
        if(direction == 7) { newR = r - 1; newC = c - 1; }

        if(board[r][c].equals(tokenColor))
            return getOpenDirection(direction, tokenColor, board, newR, newC);
        else if(board[r][c].equals(Color.WHITE) && !((r + 1) < ROWS && (board[r + 1][c].equals(Color.WHITE))))
            return r * ROWS + c;
        else
            return null;

    }

    private int getTokenRun(int direction, Color tokenColor, Color[][] board, int r, int c){

        if (r >= ROWS || c >= COLS || r < 0 || c < 0)
            return 0;

        int newR = 0 , newC = 0;

        if(direction == 0) { newR = r - 1; newC = c    ; }
        if(direction == 1) { newR = r - 1; newC = c + 1; }
        if(direction == 2) { newR = r + 1; newC = c    ; }
        if(direction == 3) { newR = r    ; newC = c + 1; }
        if(direction == 4) { newR = r    ; newC = c + 1; }
        if(direction == 5) { newR = r - 1; newC = c + 1; }
        if(direction == 6) { newR = r - 1; newC = c    ; }
        if(direction == 7) { newR = r - 1; newC = c - 1; }

        if(board[r][c].equals(tokenColor))
            return 1 + getTokenRun(direction, tokenColor, board, newR, newC);
        else
            return 0;

    }








    public int getMove(Color[][] board , Color me) {

        ArrayList<Integer> playableCols = new ArrayList<Integer>(7);
        for (int col = 0; col < 7; col++) {
            if(board[0][col].equals(Color.WHITE))
            playableCols.add(col);
        }
         checkWinning(board,me);

        int colMove = (int) (Math.random() * playableCols.size());
        return playableCols.get(colMove);

    }


    public int checkWinning(Color[][] board ,Color me) {
        //check horizontal
        for (int r = 0; r < ROWS; r++) {
            int run = 0;
            for (int c = 0; c < COLS; c++) {
                if ((board[r][c].equals(me))) {
                    run++;

                    if (run == 2)
                        return c +1;
                } else {
                    run = 0;
                }
            }
        }

        //check vertical
        for (int c = 0; c < COLS; c++) {
            int run = 0;
            for (int r = 0; r < ROWS; r++) {
                if (((board[r][c].equals(me)))) {
                    run++;

                    if (run == 2)
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
                if ((board[r - i][c + i]).equals(me)) {
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
                if ((board[r - i][c - i]).equals(me)) {
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
        return "!";
    }
}