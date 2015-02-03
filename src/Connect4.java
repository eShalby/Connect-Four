import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by oj on 1/23/15.
 */
public class Connect4 extends AnchorPane implements Connect4able {
    public static final int ROWS = 6, COLS = 7, TOKEN_RADIUS = 40, TOKEN_SPACING = 10, AI_TURN_MS = 500;

    private Circle[][] gameBoard;
    private Color currentPlayer;
    private Connect4AI redUI, yellowUI;
    private Rectangle highlightedColumn;
    private MediaPlayer dropSound;

    public Connect4(Color first, Color user, Connect4AI ui) throws InvalidGameStateException {
        if (first.equals(Color.RED) || first.equals(Color.YELLOW) || user.equals(Color.RED) || user.equals(Color.YELLOW)) {
            currentPlayer = first;
            if (user.equals(Color.RED))
                yellowUI = ui;
            else
                redUI = ui;
        } else
            throw new InvalidGameStateException("Only Color.RED and Color.YELLOW are allowed Tokens");

        setup();
    }

    public Connect4(Color first, Connect4AI redUI, Connect4AI yellowUI) throws InvalidGameStateException {
        if (first.equals(Color.RED) || first.equals(Color.YELLOW)) {
            currentPlayer = first;
            this.yellowUI = yellowUI;
            this.redUI = redUI;
        } else
            throw new InvalidGameStateException("Only Color.RED and Color.YELLOW are allowed Tokens");

        setup();
    }

    public Connect4(Color first) throws InvalidGameStateException {
        if (first.equals(Color.RED) || first.equals(Color.YELLOW)) {
            currentPlayer = first;
        } else
            throw new InvalidGameStateException("Only Color.RED and Color.YELLOW are allowed Tokens");

        setup();
    }

    private void setup() {
        dropSound = new MediaPlayer(new Media(Connect4.class.getResource("drop.mp3").toString()));
        gameBoard = new Circle[ROWS][COLS];
        currentPlayer = Color.RED;
        highlightedColumn = new Rectangle(2 * (TOKEN_RADIUS + TOKEN_SPACING), ROWS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));

        highlightedColumn.setOpacity(0.3);

        setMaxHeight(ROWS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setMinHeight(ROWS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setMaxWidth(COLS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setMinWidth(COLS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(TOKEN_RADIUS), new Insets(0))));
        setCursor(Cursor.CROSSHAIR);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Circle c = new Circle((TOKEN_RADIUS + TOKEN_SPACING) + j * 2 * (TOKEN_RADIUS + TOKEN_SPACING), (TOKEN_RADIUS + TOKEN_SPACING) + i * 2 * (TOKEN_RADIUS + TOKEN_SPACING), TOKEN_RADIUS, Color.WHITE);
                gameBoard[i][j] = c;
                getChildren().add(c);
            }
        }

        processTurn();
    }

    private void processTurn() {
        if (!isGameOver()) {
            if (currentPlayer.equals(Color.RED) && redUI == null || currentPlayer.equals(Color.YELLOW) && yellowUI == null) {
                highlightedColumn.setFill(currentPlayer);

                setOnMouseClicked(event -> {
                    int col = (int) (event.getX() / (2 * (TOKEN_RADIUS + TOKEN_SPACING)));
                    addToken(col);
                    updateHighlighter(col);

                    if (currentPlayer.equals(Color.RED))
                        currentPlayer = Color.YELLOW;
                    else
                        currentPlayer = Color.RED;
                    processTurn();
                });

                setOnMouseEntered(event -> {
                    if (!getChildren().contains(highlightedColumn)) {
                        getChildren().add(highlightedColumn);
                    }
                });

                setOnMouseExited(event -> {
                    if (getChildren().contains(highlightedColumn)) {
                        getChildren().remove(highlightedColumn);
                    }
                });

                setOnMouseMoved(event -> {
                    int col = (int) (event.getX() / (2 * (TOKEN_RADIUS + TOKEN_SPACING)));

                    updateHighlighter(col);
                });


            } else if (currentPlayer.equals(Color.RED)) {
                setOnMouseClicked(null);
                setOnMouseEntered(null);
                setOnMouseExited(null);
                setOnMouseMoved(null);
                Timeline timeline = new Timeline(new KeyFrame(new Duration(AI_TURN_MS), event -> {
                    Color[][] board = duplicate();
                    addToken(redUI.getMove(board, Color.RED));
                    currentPlayer = Color.YELLOW;
                    processTurn();
                }));
                timeline.play();
            } else {
                setOnMouseClicked(null);
                setOnMouseEntered(null);
                setOnMouseExited(null);
                setOnMouseMoved(null);
                Timeline timeline = new Timeline(new KeyFrame(new Duration(AI_TURN_MS), event -> {
                    Color[][] board = duplicate();
                    addToken(yellowUI.getMove(board, Color.YELLOW));
                    currentPlayer = Color.RED;
                    processTurn();
                }));
                timeline.play();
            }
        }
    }

    private void addToken(int col) {
        dropSound.stop();
        dropSound.seek(new Duration(0));
        dropSound.play();

        int r = 0;

        while (r < ROWS - 1 && (gameBoard[r][col].getFill()).equals(Color.WHITE)) {
            r++;
        }

        if (r == 0 && (!(gameBoard[0][col].getFill()).equals(Color.WHITE))) {

        } else {
            if (!(gameBoard[r][col].getFill()).equals(Color.WHITE))
               r--;

            gameBoard[r][col].setFill(currentPlayer);
        }

        if (getWinner() != null) {
            Text gameOver = new Text("Game Over");
            gameOver.setFont(Font.font("Helvetica", FontWeight.BOLD, 112));
            gameOver.setFill(getWinner());
            gameOver.setStroke(Color.BLACK);
            gameOver.setStrokeWidth(3);
            gameOver.setTextOrigin(VPos.BASELINE);
            gameOver.setEffect(new DropShadow(4,2,2,Color.DARKGREY));
            getChildren().add(gameOver);
            gameOver.setX(this.getWidth() / 2 - gameOver.getBoundsInLocal().getWidth() / 2);
            gameOver.setY(this.getHeight() / 2 - gameOver.getBoundsInLocal().getHeight() / 2);
            setOnMouseClicked(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
            setOnMouseMoved(null);

        }
    }

    private Color[][] duplicate() {
        Color[][] a = new Color[gameBoard.length][gameBoard[0].length];

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                a[i][j] = (Color)gameBoard[i][j].getFill();
            }
        }

        return a;
    }

    private void updateHighlighter(int col) {
        int row = 0;
        while (row < ROWS && ((Color)gameBoard[row][col].getFill()).equals(Color.WHITE))
            row++;
        highlightedColumn.setHeight(row *  2 * (TOKEN_RADIUS + TOKEN_SPACING));

        highlightedColumn.setLayoutX(col * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
    }

    @Override
    public void dropToken(int col) {

    }

    @Override
    public void clear() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                gameBoard[r][c].setFill(Color.WHITE);
            }
        }
    }

    @Override
    public boolean hasWinner() {
        return getWinner() != null;
    }

    @Override
    public Color getWinner() {
        if (checkWinner(Color.RED))
            return Color.RED;
        else if (checkWinner(Color.YELLOW))
            return Color.YELLOW;
        else
            return null;
    }

    private boolean checkWinner(Color color) {
        //check horizontal
        for (int r = 0; r < ROWS; r++) {
            int run = 0;
            for (int c = 0; c < COLS; c++) {
                if ((gameBoard[r][c].getFill()).equals(color)) {
                    run++;

                    if (run == 4)
                        return true;
                } else {
                    run = 0;
                }
            }
        }

        //check vertical
        for (int c = 0; c < COLS; c++) {
            int run = 0;
            for (int r = 0; r < ROWS; r++) {
                if ((gameBoard[r][c].getFill()).equals(color)) {
                    run++;

                    if (run == 4)
                        return true;
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
                if ((gameBoard[r - i][c + i].getFill()).equals(color)) {
                    run++;

                    if (run == 4)
                        return true;
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
                if ((gameBoard[r - i][c - i].getFill()).equals(color)) {
                    run++;

                    if (run == 4)
                        return true;
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

        return false;
    }

    @Override
    public boolean isGameOver() {
        return hasWinner() || isDraw();
    }

    @Override
    public boolean isDraw() {
        if (getWinner() != null)
            return false;

        for (int i = 0; i < COLS; i++) {
            if (gameBoard[0][i].getFill().equals(Color.WHITE))
                return false;
        }
        return true;
    }

    @Override
    public Color getCurrentPlayer() {
        return null;
    }
}
