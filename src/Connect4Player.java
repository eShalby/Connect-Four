import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Connect4Player implements Connect4AI {
    //    Connect4Moderator mod;
    Color[][] board;
    ArrayList<Group> groups = new ArrayList<>(69);
    Color token;
    Color opp;

    public Connect4Player() {
    }

    public abstract int makePlay();

    public boolean isValid(int col) {
        return board[0][col].equals(Color.WHITE);
    }

    public int getRandomPlay() {
        int col = (int) (Math.random() * 6);
        boolean isValid = isValid(col);
        while (!isValid) {
            col = (int) (Math.random() * 6);
            isValid = isValid(col);
        }
        return col;
    }

    public int getMove(Color[][] board, Color me) {
        this.token = me;
        this.board = board;
        if (me.equals(Color.YELLOW))
            opp = Color.RED;
        else
            opp = Color.YELLOW;
        getGroups();
        refreshGroups();
        return makePlay();
    }

    private void getGroups() {

//                    Initialize and place the groups
        int[] dRows = {-1, 0, 1, 1};
        int[] dCols = {1, 1, 1, 0};
        int fRow, fCol;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

//                start @ i,j
//
//                if 0 < i + 3 * dCol <= board[0].length, 0 < j + 3 * dRow <= board.length
//                    create new group from i,j with specified increment pair
//
//                possible directions are: diagonal up, right, diagonal down, down
//                / -1 row +1 col
//                > 0 row +1 col
//                \ +1 row +1 col
//                | +1 row 0 col


                for (int k = 0; k < dRows.length; k++) {
                    fRow = i + 3 * dRows[k];
                    fCol = j + 3 * dCols[k];

//                    switch (k) {
//                        case 0:
//                            System.out.print("/ ");
//                            break;
//                        case 1:
//                            System.out.print("> ");
//                            break;
//                        case 2:
//                            System.out.print("\\ ");
//                            break;
//                        case 3:
//                            System.out.print("| ");
//                            break;
//                    }
                    if (fRow >= 0 && fRow <= board.length - 1 && fCol >= 0 && fCol <= board[0].length - 1) {
//                        System.out.println("Group: [" + j + "][" + i + "] to [" + fRow + "][" + fCol + "] is possible");
                        groups.add(new Group(board, i, j, dCols[k], dRows[k]));

                    }
                }


            }
        }
    }

    abstract void refreshGroups();

    public Color getToken() {
        return token;
    }

    public Image getImage() {
        return null;
    }

    public String getName() {
        return toString();
    }

    public String toString() {
        return "" + getClass();
    }

}
