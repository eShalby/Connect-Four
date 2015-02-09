

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by 40095 on 1/24/15.
 */
public class JonsAiIsBetterThanLinus extends Connect4Player {
    boolean isBetterThanLinus = true;
    ArrayList<Group> winningGroups, losingGroups, zugzwangGroups, winnableGroups, groups, tripleGroups, trickyThreats;
    String mode;
    boolean verbose;
    //Winning groups is all groups that can be won on the next play
    //Losing groups is all the groups that will lead to defeat in the next play
    //zugzwangGroups are all groups with three of a kind, but the 4th tile is not playable (Zugzwang)
    //winnableGroups is all groups that do not contain the opponents tile
    //tripleGroups is groups with three of a kind
    //groups is all groups

    //description assumes playing as Y


    /*
    Order of precedence for plays to make:
    1) plays that make you win immediately
        Pick first group from left
    2) plays that keep you from losing immediately
        Pick first group from left
    3) play where vals are _, R, R, _  - tricky threats
    3) plays that are in a winnable group AND not zugzwang
        Pick winnable group with most of your own tokens
    4) zugzwang plays
        Pick first group from left

     */

    public JonsAiIsBetterThanLinus() {
        this("");
    }

    public JonsAiIsBetterThanLinus(String mode) {
        groups = new ArrayList<Group>(69);
        tripleGroups = new ArrayList<Group>();
        winningGroups = new ArrayList<Group>();
        losingGroups = new ArrayList<Group>();
        zugzwangGroups = new ArrayList<Group>();
        winnableGroups = new ArrayList<>();
        trickyThreats = new ArrayList<>();
        this.mode = mode;
        verbose = mode.equals("v");

        refreshGroups();

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

                    if (verbose) {
                        switch (k) {
                            case 0:
                                System.out.print("/ ");
                                break;
                            case 1:
                                System.out.print("> ");
                                break;
                            case 2:
                                System.out.print("\\ ");
                                break;
                            case 3:
                                System.out.print("| ");
                                break;
                        }
                    }
                    if (fRow >= 0 && fRow <= board.length - 1 && fCol >= 0 && fCol <= board[0].length - 1) {
                        if (verbose)
                            System.out.println("Group: [" + j + "][" + i + "] to [" + fRow + "][" + fCol + "] is possible");
                        groups.add(new Group(board, i, j, dCols[k], dRows[k]));

                    }
                }


            }
        }
    }

    private boolean isPlayable(int row, int col) {
        if (row == 5)
            return board[row][col].equals(Color.WHITE);
        if (row + 1 < board.length)
            return board[row][col].equals(Color.WHITE) && !board[row + 1][col].equals(Color.WHITE);
        return false;
    }

    private int tilesBelowSpace(int row, int col) {
        int count = 0;
        for (int i = row; i < 5; i++) {
            if (board[i][col].equals(Color.WHITE)) {
                count++;
            }
        }
        return count;
    }

    private int winningCol() {
        refreshGroups();
        if (winningGroups.size() > 0) {
            if (verbose)
                System.out.println("winning group");
            return winningGroups.get(0).dangerousCol();
        }
        if (losingGroups.size() > 0) {
            if (verbose)
                System.out.println("Not losing");
            return losingGroups.get(0).dangerousCol();
        }
        if (trickyThreats.size() > 0) {
            if (verbose)
                System.out.println("Tricky Threat");
            return trickyThreats.get(0).colOf(0);
        }
        ArrayList<Group> viableGroups = new ArrayList<>();
        for (Group g : winnableGroups) {
            if (!zugzwangGroups.contains(g)) {
                viableGroups.add(g);
            }
        }
        if (verbose)
            System.out.println("playing random");
        return viableGroups.get(0).startCol + (int) (Math.random() * 4 * viableGroups.get(0).dCol);
    }

    private void setWinningGroups() {

        winnableGroups.clear();
        for (Group group : tripleGroups) {
            if (group.getCriticalColor().equals(token)) {
                if (isPlayable(group.rowOf(group.emptyTripleIndex()), group.colOf(group.emptyTripleIndex()))) {
                    winningGroups.add(group);
                }
            }
        }
        if (verbose)
            System.out.println("Filled Winning Groups ");

    }

    private void setLosingGroups() {
        losingGroups.clear();
        for (Group group : tripleGroups) {

            if (!group.getCriticalColor().equals(token) && !group.getCriticalColor().equals(Color.WHITE)) {
//                System.out.println("group " + group + " critical color = " + group.getCriticalColor());
                if (isPlayable(group.rowOf(group.emptyTripleIndex()), group.colOf(group.emptyTripleIndex()))) {
                    losingGroups.add(group);
                }
            }
        }

        if (verbose)
            System.out.println("filled losing groups ");
    }

    private void setWinnableGroups() {
        for (Group g : groups) {
            if (g.doesNotContain(opp)) {
                winnableGroups.add(g);
            }
        }
        if (verbose)
            System.out.println("filled winnable groups");
    }

    private void setZugzwangGroups() {
        for (Group g : groups) {
            if (g.dangerousCol() >= 0 && g.dangerousRow() >= 0) {
                if (!isPlayable(g.dangerousRow(), g.dangerousCol()) && tilesBelowSpace(g.dangerousRow(), g.dangerousCol()) % 2 == 1) //if the group is not playable and the tiles below the group is odd, play it
                    zugzwangGroups.add(g);
            }
        }
        if (verbose)
            System.out.println("filled zugzwang groups");
    }

    public void refreshGroups() {
        setTripleGroups();
        setWinningGroups();
        setTrickyGroups();
        setLosingGroups();
        setWinnableGroups();
        setZugzwangGroups();
    }

    private void setTripleGroups() {
        for (Group g : groups) {
            if (g.emptyTripleIndex() != 1) {
                tripleGroups.add(g);
            }
        }
    }

    private void setTrickyGroups() {
        for (Group g : groups) {
            if (g.isTrickyThreat()) {
                trickyThreats.add(g);
            }
        }
    }

    public int makePlay() {
        int winningCol = winningCol();
        if (winningCol != -1) {
            return winningCol;
        } else {
            return getRandomPlay();
        }
    }

    @Override
    public int getMove(Color[][] board, Color me) {
        this.token = me;
        this.board = board;
        getGroups();
        refreshGroups();
        return makePlay();
    }

    /**
     * Created by 40095 on 1/25/15.
     */

    /**
     * Created by 40095 on 1/27/15.
     */

}