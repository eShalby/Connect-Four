import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AngusAI implements Connect4AI {
    private static final int ROWS = 6, COLS = 7;

    public int getMove(Color[][] board , Color me) {
        Color enemyColor = getEnemyColor(me);

        ArrayList<Integer> myMoves;
        ArrayList<Integer> badColorMoves;

        //3's
        myMoves = getPositions(board, me, 3);
        badColorMoves = getPositions(board, enemyColor, 3);

        if (myMoves.size() > 0)   {
            System.out.println("3 - me, I will place a token on row: " + myMoves.get(0));
            return myMoves.get(0);
        }


        if (badColorMoves.size() > 0) {
            System.out.println("3 - HIM, I will place a token on row: " + badColorMoves.get(0));
            return badColorMoves.get(0);
        }


        badColorMoves.clear();
        myMoves.clear();

        //2's
        myMoves = getPositions(board, me, 2);
        badColorMoves = getPositions(board, enemyColor, 2);

        if (myMoves.size() > 0){
            System.out.println("2 - me, I will place a token on row: " + myMoves.get(0));
            return myMoves.get(0);
        }

        if (badColorMoves.size() > 0){
            System.out.println("2 - HIM, I will place a token on row: " + badColorMoves.get(0));
            return badColorMoves.get(0);
        }

        badColorMoves.clear();
        myMoves.clear();

        //1's
        myMoves = getPositions(board, me, 1);
        badColorMoves = getPositions(board, enemyColor, 1);

        if (myMoves.size() > 0){
            System.out.println("1 - me, I will place a token on row: " + myMoves.get(0));
            return myMoves.get(0);
        }

        if (badColorMoves.size() > 0){
            System.out.println("1 - HIM, I will place a token on row: " + badColorMoves.get(0));
            return badColorMoves.get(0);
        }

        badColorMoves.clear();
        myMoves.clear();

        System.out.println("Returning 3");

        return 3;

    }

    private ArrayList<Integer> getPositions(Color[][] board, Color color, int run) {
        ArrayList<Integer> positions = new ArrayList<Integer>();

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

                        if (right != null   || left != null)      {
                            int horizontal    = getTokenRun(2, color, board, rows, cols + 1) + getTokenRun(6, color, board, rows, cols - 1);

                            if (right != null && horizontal == run)
                                positions.add(right % COLS);
                            if (left != null && horizontal == run)
                                positions.add(left % COLS);
                        }

                        if (up != null      || down != null)      {
                            int vertical      = getTokenRun(0, color, board, rows + 1, cols) + getTokenRun(4, color, board, rows - 1, cols);

                            if (up != null && vertical == run)
                                positions.add(up % COLS );
                            if (down != null && vertical == run)
                                positions.add(down % COLS);
                        }

                        if (leftUp != null  || leftDown != null)  {
                            int diagonalLeft  = getTokenRun(5, color, board, rows - 1, cols + 1) + getTokenRun(1, color, board, rows + 1, cols - 1);

                            if (leftUp != null && diagonalLeft == run)
                                positions.add(leftUp % COLS);
                            if (leftDown != null && diagonalLeft == run)
                                positions.add(leftDown % COLS);
                        }

                        if (rightUp != null || rightDown != null) {
                            int diagonalRight = getTokenRun(7, color, board, rows - 1, cols + 1) + getTokenRun(3, color, board, rows + 1, cols - 1);

                            if (rightUp != null && diagonalRight == run)
                                positions.add(rightUp % COLS);
                            if (rightDown != null && diagonalRight == run)
                                positions.add(rightDown % COLS);
                        }

                    }
                }
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
            return r * COLS + c;
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

    public Image getImage() {
        return new Image("AI-Image.jpg");
    }

    public String getName() {
        return "Angus' bitchin' AI";
    }

    private Color getEnemyColor(Color me) {
        if(me.equals(Color.RED))
            return Color.YELLOW;
        else
            return Color.RED;
    }

    public static ArrayList merge(ArrayList<Integer> arrayList1 , ArrayList<Integer> arrayList2){
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for (int i = 0; i < arrayList1.size(); i++) {
            temp.add(arrayList1.get(i));
        }
        for (int i = 0; i < arrayList2.size(); i++) {
            temp.add(arrayList2.get(i));
        }
        return temp;
    }
}
