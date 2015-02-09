import javafx.scene.paint.Color;

public class Group {
    int startCol, startRow, dRow, dCol;
    Color[] values = new Color[4];
    Color[][] board;

    public Group(Color[][] board, int startRow, int startCol, int dCol, int dRow) {
        this.board = board;
        this.dRow = dRow;
        this.dCol = dCol;
        this.startCol = startCol;
        this.startRow = startRow;
//        System.out.println("ΔX = " + dRow + " ΔY = " + dCol);
//        System.out.println(getLocation());

        fillValues();

    }

    private void fillValues() {
        for (int i = 0; i < values.length; i++) {
            int col = startCol + i * dCol;
            int row = startRow + i * dRow;
            values[i] = board[row][col];
        }
    }

    public int dangerousRow() {
        if (emptyTripleIndex() == -1)
            return -1;
        else
            return emptyTripleIndex() * dRow + startRow;
    }

    int rowOf(int index) {
        return startRow + dRow * index;
    }

    boolean isTrickyThreat() {
        fillValues();
        return values[0].equals(Color.WHITE) && values[1].equals(values[2]) && !values[1].equals(Color.WHITE) && values[3].equals(Color.WHITE);
    }

    int colOf(int index) {
        return startCol + dCol * index;
    }

    public int dangerousCol() {
        if (emptyTripleIndex() == -1)
            return -1;
        else
            return emptyTripleIndex() * dCol + startCol;
    }

    public boolean contains(Color c) {
        fillValues();
        boolean b = false;
        for (Color value : values) {
            b = b || value.equals(c);
        }
        return b;
    }

    public boolean doesNotContain(Color c) {
        fillValues();
        boolean b = !values[0].equals(c);
        for (Color value : values) {
            b = b && !value.equals(c);

        }
        return b;
    }

    public boolean isFull() {
        fillValues();
        return values[0].equals(values[1]) && values[1].equals(values[2]) && values[2].equals(values[3]);
    }

    public Color getFullColor() {
        if (isFull())
            return values[0];
        return null;
    }


    public String toString() {
        return "[" + startRow + "][" + startCol + "] to [" + (3 * dRow + startRow) + "][" + (3 * dCol + startCol) + "]";
    }

    public String getValString() {
        String vals = "";
        for (int i = 0; i < values.length; i++) {
            vals = vals + values[i] + ", ";
        }
        return vals;

    }

    /**
     * checks if the group has three of the same kind
     *
     * @return the index of the empty tile or -1 if the group does not have 3 of a kind / the 4th is full
     */
    public int emptyTripleIndex() {
        fillValues();
        //1 = 2 = 3, 0 = white return 0
        if (values[0].equals(Color.WHITE) && !values[1].equals(values[0]) && values[1].equals(values[2]) && values[2].equals(values[3]))
            return 0;
        //0 = 2 = 3, 1 = white return 1
        if (values[1].equals(Color.WHITE) && !values[0].equals(values[1]) && values[0].equals(values[2]) && values[2].equals(values[3]))
            return 1;
        //0 = 1 = 3, 2 = white return 2
        if (values[2].equals(Color.WHITE) && !values[0].equals(values[2]) && values[0].equals(values[1]) && values[1].equals(values[3]))
            return 2;
        //0 = 1 = 2, 3 = white return 3
        if (values[3].equals(Color.WHITE) && !values[0].equals(values[3]) && values[0].equals(values[1]) && values[1].equals(values[2]))
            return 3;
        return -1;
    }

    /**
     * @return the color of the player that will win if they play in the "dangerous" column to fill the group
     */

    public Color getCriticalColor() {

        if (emptyTripleIndex() == 0)
            return values[1];
        else if (emptyTripleIndex() == -1)
            return Color.WHITE;
        else
            return values[emptyTripleIndex() - 1];
    }
}
