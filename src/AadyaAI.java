import javafx.scene.image.Image;
import javafx.scene.paint.Color;
/**
 * Created by 40218 on 1/30/15.
 */
public class AadyaAI implements Connect4AI { //six rows coloums seven
    Color me;
    private static final int R =6 , C= 7;
    private boolean first=true;
    public AadyaAI(Color me)
    {
        this.me = me;
    }
    @Override
     public int getMove(Color[][] board, Color me) {
        {
            if (first) {
                this.first = false;
                return 3;
            }


            int h = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < R; j++) {
                    for (int k = 0; k < C; k++) {
                        if (board[j][k].equals(me)) {
                            int right = getD(me, 2, board, j, k + 1);
                            int left = getD(me, 6, board, j, k - 1);
                            int up = getD(me, 0, board, j - 1, k);
                            int down = getD(me, 4, board, j + 1, k);
                            int lup = getD(me, 7, board, j - 1, k - 1);
                            int ld = getD(me, 5, board, j - 1, k + 1);
                            int rup = getD(me, 1, board, j + 1, k - 1);
                            int rd = getD(me, 3, board, j + 1, k + 1);
                            if ((right != 0 || left != 0) || (up != 0 || down != 0) || (lup != 0 || ld != 0) || (rup != 0 || rd != 0)) {
                                h = 1 + possibilities(j, k + 1, me, board, 2) + possibilities(j, k - 1, me, board, 6);
                            }
                        }
                    }
                }
            }
            return h;
        }
    }

    private int getD( Color colour,int dir, Color[][] board, int r, int c){

        if (r >= R || c >= C || r < 0 || c < 0)
            return Integer.parseInt(null);
        if(dir == 0)
        {r = r - 1; }
        if(dir == 1)
        { r = r - 1; c = c + 1; }
        if(dir == 2)
        { r = r + 1; }
        if(dir == 3)
        { c = c + 1; }
        if(dir == 4)
        { c = c + 1; }
        if(dir == 5)
        { r = r - 1; c = c + 1; }
        if(dir == 6)
        { r = r - 1;  }
        if(dir == 7)
        { r = r - 1; c = c - 1; }
        if(board[r][c].equals(colour))
            return getD(colour, dir, board, r,c);
        else if(board[r][c].equals(Color.WHITE) && !((r + 1) < R && (board[r + 1][c].equals(Color.WHITE))))
            return r * R + c;
        else
            return Integer.parseInt(null);

    }

    private int possibilities(int c, int r , Color colour , Color[][] board , int dir){
        if (r >= R || c >= C || r < 0 || c < 0)
            return Integer.parseInt(null);
        if(dir == 0)
        {r = r - 1; }
        if(dir == 1)
        { r = r - 1; c = c + 1; }
        if(dir == 2)
        { r = r + 1; }
        if(dir == 3)
        { c = c + 1; }
        if(dir == 4)
        { c = c + 1; }
        if(dir == 5)
        { r = r - 1; c = c + 1; }
        if(dir == 6)
        { r = r - 1;  }
        if(dir == 7)
        { r = r - 1; c = c - 1; }
        if(board[r][c].equals(colour))
            return  possibilities(c, r,me, board, dir)+1;
        else
            return Integer.parseInt(null);

    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return "Aadya ";
    }
}
