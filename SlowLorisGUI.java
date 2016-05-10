import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SlowLorisGUI extends Application {

    Stage window;
    Scene scene;
    BorderPane borderPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        borderPane = new BorderPane();
        scene = new Scene(borderPane, 700, 500);

        window.setScene(scene);
        window.show();
    }
}
