import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by oj on 1/23/15.
 */
public class Connect4 extends AnchorPane implements Connect4able   {
    public static final int ROWS = 6, COLS = 7, TOKEN_RADIUS = 40, TOKEN_SPACING = 10;

    private Circle[][] gameBoard;
    private Color currentPlayer;
    private Connect4AI redUI, yellowUI;
    private Rectangle highlightedColumn;

    public Connect4(Connect4AI ui, Color first,  Color user) {
        setup();
    }

    public Connect4(Connect4AI redUI, Connect4AI yellowUI, Color first) {
        setup();
    }

    public Connect4(Color first) {
        setup();
    }

    public Connect4() {
        setup();
    }

    private void setup() {
        gameBoard = new Circle[ROWS][COLS];
        currentPlayer = Color.RED;
        highlightedColumn = new Rectangle(2 * (TOKEN_RADIUS + TOKEN_SPACING),ROWS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));

        highlightedColumn.setFill(currentPlayer);
        highlightedColumn.setOpacity(0.3);

        setMaxHeight(ROWS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setMinHeight(ROWS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setMaxWidth(COLS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setMinWidth(COLS * 2 * (TOKEN_RADIUS + TOKEN_SPACING));
        setBackground(new Background(new BackgroundFill(Color.BLUE, new CornerRadii(TOKEN_RADIUS), new Insets(0))));
        setCursor(Cursor.CROSSHAIR);

        setOnMouseClicked(event -> {
            int col = (int) (event.getX() / (2 * (TOKEN_RADIUS + TOKEN_SPACING)));
            System.out.println(col);
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

        setOnMouseMoved(event-> {
            int col = (int) (event.getX() / (2 * (TOKEN_RADIUS + TOKEN_SPACING)));
            highlightedColumn.setLayoutX(col * 2 * (TOKEN_RADIUS + TOKEN_SPACING));

        });

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Circle c = new Circle((TOKEN_RADIUS + TOKEN_SPACING) + j * 2 * (TOKEN_RADIUS + TOKEN_SPACING), (TOKEN_RADIUS + TOKEN_SPACING) + i * 2 * (TOKEN_RADIUS + TOKEN_SPACING), TOKEN_RADIUS, Color.WHITE);
                gameBoard[i][j] = c;
                getChildren().add(c);
            }
        }
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
