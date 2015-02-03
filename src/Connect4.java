import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by oj on 1/23/15.
 */
public class Connect4 extends AnchorPane implements Connect4able {
    public static final int ROWS = 6, COLS = 7, TOKEN_RADIUS = 40, TOKEN_SPACING = 10;

    private Circle[][] gameBoard;
    private Color currentPlayer;
    private Connect4AI redUI, yellowUI;
    private Rectangle highlightedColumn;

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

    public Connect4() {
        setup();
    }

    private void setup() {
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
        if (currentPlayer.equals(Color.RED) && redUI == null || currentPlayer.equals(Color.YELLOW) && yellowUI == null) {
            highlightedColumn.setFill(currentPlayer);

            setOnMouseClicked(event -> {
                int col = (int) (event.getX() / (2 * (TOKEN_RADIUS + TOKEN_SPACING)));
                addToken(col);
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
                highlightedColumn.setLayoutX(col * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
            });


        } else if (currentPlayer.equals(Color.RED)) {
            setOnMouseClicked(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
            setOnMouseMoved(null);
            Timeline timeline = new Timeline(new KeyFrame(new Duration(1000),event -> {
                Color[][] board = duplicate();
                addToken(redUI.getMove(board, Color.RED));
                currentPlayer = Color.YELLOW;
            }));
            timeline.play();
        } else {
            setOnMouseClicked(null);
            setOnMouseEntered(null);
            setOnMouseExited(null);
            setOnMouseMoved(null);
            Timeline timeline = new Timeline(new KeyFrame(new Duration(1000),event -> {
                Color[][] board = duplicate();
                addToken(yellowUI.getMove(board, Color.YELLOW));
                currentPlayer = Color.RED;
            }));
            timeline.play();
        }

    }

    private void addToken(int col) {
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


    @Override
    public void dropToken(int col) {

    }

    @Override
    public void clear() {

    }

    @Override
    public boolean hasWinner() {
        return false;
    }

    @Override
    public Color getWinner() {
        return null;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public boolean isDraw() {
        return false;
    }

    @Override
    public Color getCurrentPlayer() {
        return null;
    }
}
