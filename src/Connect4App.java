/**
 * Created by oj on 1/23/15.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Connect4App extends Application {
    private BorderPane root;
    private Stage stage;
    private Scene scene;
    private Connect4 connect4;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        root = new BorderPane();
        scene = new Scene(root, 800, 700); //width and height of application
        stage.setScene(scene);
        stage.setTitle("Connect4");  //text for the title bar of the window

        connect4 = new Connect4();
        root.setCenter(connect4);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}