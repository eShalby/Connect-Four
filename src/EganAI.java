import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by 40465 on 2/9/15.
 */
public class EganAI implements Connect4AI {
        //will return a value 0-6
        @Override
        public int getMove(Color[][] board, Color me) {


            int move = (int) (Math.random() * 7);
            int currentRun = 0;
            int enemyCurrentRun = 0 ;


            if (move > 7) {
                for (int i = 0; i <= board.length - 1; i++) {
                    for (int j = board[0].length - 1; j >= 0; j--) {
                        if (board[i][j].equals(me)) {
                            currentRun++;
                            if (currentRun > 2) {
                                move = i + 1;
                                break;
                            }
                        } else {
                            if (!(board[i][j].equals(me)) && !(board[i][j].equals(Color.WHITE))) {
                                enemyCurrentRun++;
                                if (enemyCurrentRun > 2) {
                                    move = i + 1;
                                    break;
                                }
                            }
                        }
                    }

                }
            }

                return move;
            }


        @Override
        public Image getImage() {
            return null;
        }

        @Override
        public String getName() {
            return "Egan aka 'I 'tried'''";
        }
    }
